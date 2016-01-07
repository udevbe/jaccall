package com.github.zubnix.jaccall.compiletime.struct;

import com.github.zubnix.jaccall.CLong;
import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Size;
import com.github.zubnix.jaccall.Struct;
import com.github.zubnix.jaccall.StructType;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.common.collect.SetMultimap;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StructWriter implements BasicAnnotationProcessor.ProcessingStep {

    private static final String STRUCT               = Struct.class.getSimpleName();
    private static final String firstFieldOffsetCode = "private static final int OFFSET_0 = 0";

    private final StructGenerator structGenerator;

    public StructWriter(final StructGenerator structGenerator) {
        this.structGenerator = structGenerator;
    }

    @Override
    public Set<? extends Class<? extends Annotation>> annotations() {
        return Collections.singleton(Struct.class);
    }

    @Override
    public void process(final SetMultimap<Class<? extends Annotation>, Element> elementsByAnnotation) {
        for (final TypeElement typeElement : ElementFilter.typesIn(elementsByAnnotation.values())) {
            parseStructFields(typeElement);
        }
    }

    private void parseStructFields(final TypeElement element) {

        final LinkedList<FieldDefinition> fieldDefinitions = new LinkedList<>();

        for (final AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString()
                                .equals(STRUCT)) {
                Boolean union = false;
                List<? extends AnnotationValue> fieldAnnotations = new LinkedList<>();

                for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> structAttribute : annotationMirror.getElementValues()
                                                                                                                               .entrySet()) {
                    if (structAttribute.getKey()
                                       .getSimpleName()
                                       .toString()
                                       .equals("union")) {
                        union = (Boolean) structAttribute.getValue()
                                                         .getValue();
                    }
                    else if (structAttribute.getKey()
                                            .getSimpleName()
                                            .toString()
                                            .equals("value")) {
                        fieldAnnotations = (List<? extends AnnotationValue>) structAttribute.getValue()
                                                                                            .getValue();
                    }
                }

                if (fieldAnnotations != null && union != null) {
                    parseFieldAnnotations(union,
                                          fieldDefinitions,
                                          fieldAnnotations);
                }
            }
        }

        final List<FieldSpec> offsetFields    = new LinkedList<>();
        final StringBuilder   ffiTypeCode     = new StringBuilder("$T.ffi_type_struct(");
        final List<Object>    ffiTypeCodeArgs = new LinkedList<>();
        ffiTypeCodeArgs.add(JNI.class);
        for (int i = 0; i < fieldDefinitions.size(); i++) {
            final FieldDefinition fieldDefinition = fieldDefinitions.get(i);
            final String offsetCode = fieldDefinition.getOffsetCode();
            final Object[] offsetCodeArgs = fieldDefinition.getOffsetCodeArgs();
            final FieldSpec fieldSpec = FieldSpec.builder(TypeName.INT,
                                                          "OFFSET_" + i,
                                                          Modifier.PRIVATE,
                                                          Modifier.STATIC,
                                                          Modifier.FINAL)
                                                 .initializer(offsetCode,
                                                              offsetCodeArgs)
                                                 .build();
            offsetFields.add(fieldSpec);

            if (i != 0) {
                ffiTypeCode.append(", ");
            }
            ffiTypeCode.append(fieldDefinition.getFfiTypeCode());
            ffiTypeCodeArgs.addAll(Arrays.asList(fieldDefinition.getFfiTypeCodeArgs()));
        }
        ffiTypeCode.append(')');

        final FieldSpec ffiTypeField = FieldSpec.builder(TypeName.INT,
                                                         "FFI_TYPE",
                                                         Modifier.PUBLIC,
                                                         Modifier.STATIC,
                                                         Modifier.FINAL)
                                                .initializer(ffiTypeCode.toString(),
                                                             ffiTypeCodeArgs.toArray())
                                                .build();

        final FieldSpec sizeField = FieldSpec.builder(TypeName.INT,
                                                      "SIZE",
                                                      Modifier.PUBLIC,
                                                      Modifier.STATIC,
                                                      Modifier.FINAL)
                                             .initializer("$T.ffi_type_struct_size(FFI_TYPE)",
                                                          JNI.class)
                                             .build();

        final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                 .addStatement("super(SIZE)")
                                                 .build();

        //TODO add field accessor methods
        final TypeSpec typeSpec = TypeSpec.classBuilder(element.getSimpleName() + "_Jaccall_StructType")
                                          .addModifiers(Modifier.ABSTRACT)
                                          .superclass(StructType.class)
                                          .addField(ffiTypeField)
                                          .addField(sizeField)
                                          .addFields(offsetFields)
                                          .addMethod(constructor)
                                          .build();

        // 0 if we have a non top level type, or 1 if we do.
        for (final PackageElement packageElement : ElementFilter.packagesIn(Collections.singletonList(element.getEnclosingElement()))) {
            final JavaFile javaFile = JavaFile.builder(packageElement.getQualifiedName()
                                                                     .toString(),
                                                       typeSpec)
                                              .build();
            try {
                javaFile.writeTo(this.structGenerator.getProcessingEnvironment()
                                                     .getFiler());
            }
            catch (final IOException e) {
                this.structGenerator.getProcessingEnvironment()
                                    .getMessager()
                                    .printMessage(Diagnostic.Kind.ERROR,
                                                  "Could not write struct type source file: \n" + javaFile.toString(),
                                                  element);
                e.printStackTrace();
            }
        }
    }

    private void parseFieldAnnotations(final Boolean union,
                                       final LinkedList<FieldDefinition> fieldDefinitions,
                                       final List<? extends AnnotationValue> fieldAnnotations) {
        for (int i = 0; i < fieldAnnotations.size(); i++) {
            final AnnotationValue fieldAnnotation = fieldAnnotations.get(i);
            final AnnotationMirror fieldAnnotationMirror = (AnnotationMirror) fieldAnnotation.getValue();

            VariableElement cType = null;
            Integer cardinality = 1;
            TypeMirror dataType = null;

            for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> fieldAttribute : fieldAnnotationMirror.getElementValues()
                                                                                                                               .entrySet()) {
                if (fieldAttribute.getKey()
                                  .getSimpleName()
                                  .toString()
                                  .equals("type")) {

                    cType = (VariableElement) fieldAttribute.getValue()
                                                            .getValue();
                }
                else if (fieldAttribute.getKey()
                                       .getSimpleName()
                                       .toString()
                                       .equals("cardinality")) {
                    cardinality = (Integer) fieldAttribute.getValue()
                                                          .getValue();
                    if (cardinality < 1) {
                        this.structGenerator.getProcessingEnvironment()
                                            .getMessager()
                                            .printMessage(Diagnostic.Kind.ERROR,
                                                          "Cardinality of struct field must be at least 1.",
                                                          fieldAttribute.getKey());
                    }
                }
                else if (fieldAttribute.getKey()
                                       .getSimpleName()
                                       .toString()
                                       .equals("dataType")) {
                    dataType = (TypeMirror) fieldAttribute.getValue()
                                                          .getValue();
                }
            }

            parseFieldAnnotation(i,
                                 fieldDefinitions,
                                 cType,
                                 cardinality,
                                 dataType,
                                 union);
        }
    }

    private void parseFieldAnnotation(final int i,
                                      final LinkedList<FieldDefinition> fieldDefinitions,
                                      final VariableElement cType,
                                      final Integer cardinality,
                                      final TypeMirror dataType,
                                      final Boolean union) {

        final CType cTypeInstance = CType.valueOf(cType.getSimpleName()
                                                       .toString());
        final boolean array = cardinality > 1;

        switch (cTypeInstance) {
            case CHAR: {
                addChar(i,
                        fieldDefinitions,
                        array,
                        cardinality,
                        union);
                break;
            }
            case UNSIGNED_CHAR: {
                addUnsignedChar(i,
                                fieldDefinitions,
                                array,
                                cardinality,
                                union);
                break;
            }
            case SHORT: {
                addShort(i,
                         fieldDefinitions,
                         array,
                         cardinality,
                         union);
                break;
            }
            case UNSIGNED_SHORT: {
                addUnsignedShort(i,
                                 fieldDefinitions,
                                 array,
                                 cardinality,
                                 union);
                break;
            }
            case INT: {
                addInt(i,
                       fieldDefinitions,
                       array,
                       cardinality,
                       union);
                break;
            }
            case UNSIGNED_INT: {
                addUnsignedInt(i,
                               fieldDefinitions,
                               array,
                               cardinality,
                               union);
                break;
            }
            case LONG: {
                addLong(i,
                        fieldDefinitions,
                        array,
                        cardinality,
                        union);
                break;
            }
            case UNSIGNED_LONG: {
                addUnsignedLong(i,
                                fieldDefinitions,
                                array,
                                cardinality,
                                union);
                break;
            }
            case LONG_LONG: {
                addLongLong(i,
                            fieldDefinitions,
                            array,
                            cardinality,
                            union);
                break;
            }
            case UNSIGNED_LONG_LONG: {
                addUnsignedLongLong(i,
                                    fieldDefinitions,
                                    array,
                                    cardinality,
                                    union);
                break;
            }
            case FLOAT: {
                addFloat(i,
                         fieldDefinitions,
                         array,
                         cardinality,
                         union);
                break;
            }
            case DOUBLE: {
                addDouble(i,
                          fieldDefinitions,
                          array,
                          cardinality,
                          union);
                break;
            }
            case POINTER: {
                addPointer(i,
                           fieldDefinitions,
                           array,
                           cardinality,
                           dataType,
                           union);
                break;
            }
            case STRUCT: {
                if (dataType == null) {
                    this.structGenerator.getProcessingEnvironment()
                                        .getMessager()
                                        .printMessage(Diagnostic.Kind.ERROR,
                                                      "Data type of struct must be specified.",
                                                      cType);
                    return;
                }

                final DeclaredType structTypeType = (DeclaredType) dataType;
                if (structTypeType.asElement()
                                  .getSimpleName()
                                  .toString()
                                  .equals("StructType")) {
                    this.structGenerator.getProcessingEnvironment()
                                        .getMessager()
                                        .printMessage(Diagnostic.Kind.ERROR,
                                                      "Declared struct type must be a subclass of 'com.github.zubnix.jaccall.StructType'.",
                                                      cType);
                    return;
                }

                addStruct(i,
                          fieldDefinitions,
                          array,
                          cardinality,
                          dataType,
                          union);
                break;
            }
        }
    }

    private void addStruct(final int i,
                           final LinkedList<FieldDefinition> fieldDefinitions,
                           final boolean array,
                           final Integer cardinality,
                           final TypeMirror dataType,
                           final Boolean union) {

        //TODO get type of first field of struct.
        final String sizeOfCode  = "$T.sizeof(($T) null)";//Size, first field size
        final String ffiTypeCode = "$T.FFI_TYPE_FLOAT";//JNI

        //TODO implement special handling for nested struct types

//        fieldDefinitions.add(createFieldDefinition(i,
//                                                   fieldDefinitions,
//                                                   sizeOfCode,
//                                                   new Object[]{Size.class,},
//                                                   ffiTypeCode,
//                                                   new Object[]{JNI.class}));
    }

    private void addPointer(final int i,
                            final LinkedList<FieldDefinition> fieldDefinitions,
                            final boolean array,
                            final Integer cardinality,
                            final TypeMirror dataType,
                            final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Pointer) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_POINTER";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addDouble(final int i,
                           final LinkedList<FieldDefinition> fieldDefinitions,
                           final boolean array,
                           final Integer cardinality,
                           final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Double) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_DOUBLE";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addFloat(final int i,
                          final LinkedList<FieldDefinition> fieldDefinitions,
                          final boolean array,
                          final Integer cardinality,
                          final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Float) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_FLOAT";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addUnsignedLongLong(final int i,
                                     final LinkedList<FieldDefinition> fieldDefinitions,
                                     final boolean array,
                                     final Integer cardinality,
                                     final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Long) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_UINT64";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addLongLong(final int i,
                             final LinkedList<FieldDefinition> fieldDefinitions,
                             final boolean array,
                             final Integer cardinality,
                             final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Long) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_SINT64";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addUnsignedLong(final int i,
                                 final LinkedList<FieldDefinition> fieldDefinitions,
                                 final boolean array,
                                 final Integer cardinality,
                                 final Boolean union) {
        final String sizeOfCode  = "($T.sizeof(($T) null)) * " + cardinality;//Size, CLong
        final String ffiTypeCode = "$T.FFI_TYPE_ULONG";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class, CLong.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addLong(final int i,
                         final LinkedList<FieldDefinition> fieldDefinitions,
                         final boolean array,
                         final Integer cardinality,
                         final Boolean union) {
        final String sizeOfCode  = "($T.sizeof(($T) null)) * " + cardinality;//Size, CLong
        final String ffiTypeCode = "$T.FFI_TYPE_SLONG";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class, CLong.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addUnsignedInt(final int i,
                                final LinkedList<FieldDefinition> fieldDefinitions,
                                final boolean array,
                                final Integer cardinality,
                                final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Integer) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_UINT32";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addInt(final int i,
                        final LinkedList<FieldDefinition> fieldDefinitions,
                        final boolean array,
                        final Integer cardinality,
                        final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Integer) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_SINT32";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addUnsignedShort(final int i,
                                  final LinkedList<FieldDefinition> fieldDefinitions,
                                  final boolean array,
                                  final Integer cardinality,
                                  final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Short) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_UINT16";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addShort(final int i,
                          final LinkedList<FieldDefinition> fieldDefinitions,
                          final boolean array,
                          final Integer cardinality,
                          final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Short) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_SINT16";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addUnsignedChar(final int i,
                                 final LinkedList<FieldDefinition> fieldDefinitions,
                                 final boolean array,
                                 final Integer cardinality,
                                 final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Byte) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_UINT8";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private void addChar(final int i,
                         final LinkedList<FieldDefinition> fieldDefinitions,
                         final boolean array,
                         final Integer cardinality,
                         final Boolean union) {
        final String sizeOfCode  = "($T.sizeof((Byte) null)) * " + cardinality;//Size
        final String ffiTypeCode = "$T.FFI_TYPE_SINT8";//JNI

        fieldDefinitions.add(createFieldDefinition(i,
                                                   fieldDefinitions,
                                                   sizeOfCode,
                                                   new Object[]{Size.class},
                                                   ffiTypeCode,
                                                   new Object[]{JNI.class},
                                                   union));
    }

    private FieldDefinition createFieldDefinition(final int i,
                                                  final LinkedList<FieldDefinition> fieldDefinitions,
                                                  final String sizeOfCode,
                                                  final Object[] sizeofCodeArgs,
                                                  final String ffiTypeCode,
                                                  final Object[] ffiTypeCodeArgs,
                                                  final Boolean union) {
        final String   offsetCode;
        final Object[] offsetCodeArgs;

        if (i == 0 || union) {
            //we're the first field
            offsetCode = "0";
            offsetCodeArgs = new Object[]{};
        }
        else {
            final FieldDefinition previous = fieldDefinitions.get(i - 1);
            offsetCode = "newOffset(" + sizeOfCode + ", OFFSET_" + (i - 1) + "+" + previous.getSizeOfCode() + ")";
            final Object[] previousSizeOfCodeArgs = previous.getSizeOfCodeArgs();
            offsetCodeArgs = new Object[sizeofCodeArgs.length + previousSizeOfCodeArgs.length];
            System.arraycopy(sizeofCodeArgs,
                             0,
                             offsetCodeArgs,
                             0,
                             sizeofCodeArgs.length);
            System.arraycopy(previousSizeOfCodeArgs,
                             0,
                             offsetCodeArgs,
                             sizeofCodeArgs.length,
                             previousSizeOfCodeArgs.length);
        }

        return new FieldDefinition(ffiTypeCode,
                                   ffiTypeCodeArgs,
                                   offsetCode,
                                   offsetCodeArgs,
                                   sizeOfCode,
                                   sizeofCodeArgs);
    }
}
