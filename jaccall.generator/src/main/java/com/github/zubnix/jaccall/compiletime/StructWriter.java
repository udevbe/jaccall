package com.github.zubnix.jaccall.compiletime;

import com.github.zubnix.jaccall.CLong;
import com.github.zubnix.jaccall.CType;
import com.github.zubnix.jaccall.JNI;
import com.github.zubnix.jaccall.Pointer;
import com.github.zubnix.jaccall.Size;
import com.github.zubnix.jaccall.Struct;
import com.github.zubnix.jaccall.StructType;
import com.github.zubnix.jaccall.Types;
import com.google.common.primitives.Primitives;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

final class StructWriter {

    private static final class GET_TYPE_MIRROR extends SimpleAnnotationValueVisitor7<TypeMirror, Void> {
        @Override
        public TypeMirror visitType(final TypeMirror typeMirror,
                                    final Void aVoid) {
            return typeMirror;
        }
    }

    private static final class GET_STRING extends SimpleAnnotationValueVisitor7<String, Void> {
        @Override
        public String visitString(final String s,
                                  final Void aVoid) {
            return s;
        }
    }

    private static final class GET_INT extends SimpleAnnotationValueVisitor7<Integer, Void> {
        @Override
        public Integer visitInt(final int i,
                                final Void aVoid) {
            return i;
        }
    }

    private static final class GET_VAR_ELEMENT extends SimpleAnnotationValueVisitor7<VariableElement, Void> {
        @Override
        public VariableElement visitEnumConstant(final VariableElement variableElement,
                                                 final Void aVoid) {
            return variableElement;
        }
    }

    private static final GET_TYPE_MIRROR GET_TYPE_MIRROR = new GET_TYPE_MIRROR();
    private static final GET_STRING      GET_STRING      = new GET_STRING();
    private static final GET_INT         GET_INT         = new GET_INT();
    private static final GET_VAR_ELEMENT GET_VAR_ELEMENT = new GET_VAR_ELEMENT();

    private static final String STRUCT = Struct.class.getSimpleName();
    private final Messager messager;
    private final Filer    filer;

    StructWriter(final Messager messager,
                 final Filer filer) {
        this.messager = messager;
        this.filer = filer;
    }

    public void process(final TypeElement typeElement) {
        parseStructFields(typeElement);
    }

    private void parseStructFields(final TypeElement element) {

        final LinkedList<FieldDefinition> fieldDefinitions = new LinkedList<>();

        Boolean union = Boolean.FALSE;
        for (final AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString()
                                .equals(STRUCT)) {

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

        final List<FieldSpec> offsetFields = new LinkedList<>();
        final CodeBlock.Builder ffiTypeCodeBuilder = CodeBlock.builder()
                                                              .add(union ? "$[$T.ffi_type_union(" : "$[$T.ffi_type_struct(",
                                                                   JNI.class);
        final List<MethodSpec> accessors = new LinkedList<>();
        for (int i = 0; i < fieldDefinitions.size(); i++) {
            final FieldDefinition fieldDefinition = fieldDefinitions.get(i);
            final CodeBlock       offsetCode      = fieldDefinition.getOffsetCode();
            final FieldSpec fieldSpec = FieldSpec.builder(TypeName.INT,
                                                          "OFFSET_" + i,
                                                          Modifier.PRIVATE,
                                                          Modifier.STATIC,
                                                          Modifier.FINAL)
                                                 .initializer(offsetCode)
                                                 .build();
            offsetFields.add(fieldSpec);

            final int cardinality = fieldDefinition.getCardinality();
            if (cardinality == 1) {
                if (i != 0) {
                    ffiTypeCodeBuilder.add(", ");
                }
                ffiTypeCodeBuilder.add(fieldDefinition.getFfiTypeCode());
            }
            else {
                for (int j = 0; j < cardinality; j++) {
                    if (i != 0 || j != 0) {
                        ffiTypeCodeBuilder.add(", ");
                    }
                    ffiTypeCodeBuilder.add(fieldDefinition.getFfiTypeCode());
                }
            }

            accessors.addAll(fieldDefinition.getAccessorsCode());
        }
        ffiTypeCodeBuilder.add(")$]");

        final FieldSpec ffiTypeField = FieldSpec.builder(TypeName.LONG,
                                                         "FFI_TYPE",
                                                         Modifier.PUBLIC,
                                                         Modifier.STATIC,
                                                         Modifier.FINAL)
                                                .initializer(ffiTypeCodeBuilder.build())
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

        final AnnotationSpec annotationSpec = AnnotationSpec.builder(Generated.class)
                                                            .addMember("value",
                                                                       "$S",
                                                                       JaccallGenerator.class.getName())
                                                            .build();

        final TypeSpec typeSpec = TypeSpec.classBuilder(element.getSimpleName() + "_Jaccall_StructType")
                                          .addAnnotation(annotationSpec)
                                          .addModifiers(Modifier.ABSTRACT)
                                          .superclass(StructType.class)
                                          .addField(ffiTypeField)
                                          .addField(sizeField)
                                          .addFields(offsetFields)
                                          .addMethod(constructor)
                                          .addMethods(accessors)
                                          .build();

        // 0 if we have a non top level type, or 1 if we do.
        for (final PackageElement packageElement : ElementFilter.packagesIn(Collections.singletonList(element.getEnclosingElement()))) {
            final JavaFile javaFile = JavaFile.builder(packageElement.getQualifiedName()
                                                                     .toString(),
                                                       typeSpec)
                                              .build();
            try {
                javaFile.writeTo(this.filer);
            }
            catch (final IOException e) {
                this.messager.printMessage(Diagnostic.Kind.ERROR,
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
            final AnnotationValue  fieldAnnotation       = fieldAnnotations.get(i);
            final AnnotationMirror fieldAnnotationMirror = (AnnotationMirror) fieldAnnotation.getValue();

            VariableElement cType        = null;
            Integer         cardinality  = 1;
            Integer         pointerDepth = 0;
            TypeMirror      dataType     = null;
            String          name         = null;

            for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> fieldAttribute : fieldAnnotationMirror.getElementValues()


                                                                                                                               .entrySet()) {
                final AnnotationValue annotationValue = fieldAttribute.getValue();
                final String fieldAttrName = fieldAttribute.getKey()
                                                           .getSimpleName()
                                                           .toString();
                switch (fieldAttrName) {
                    case "type":
                        cType = annotationValue.accept(GET_VAR_ELEMENT,
                                                       null);
                        break;
                    case "cardinality":
                        cardinality = annotationValue.accept(GET_INT,
                                                             null);
                        if (cardinality < 1) {
                            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                                       "Cardinality of struct field must be at least 1.",
                                                       fieldAttribute.getKey());
                        }
                        break;
                    case "pointerDepth":
                        pointerDepth = annotationValue.accept(GET_INT,
                                                              null);
                        if (pointerDepth < 0) {
                            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                                       "Pointer depth can not be negative.",
                                                       fieldAttribute.getKey());
                        }
                        break;
                    case "dataType":
                        dataType = annotationValue.accept(GET_TYPE_MIRROR,
                                                          null);
                        break;
                    case "name":
                        name = annotationValue.accept(GET_STRING,
                                                      null);
                        break;
                }
            }

            parseFieldAnnotation(i,
                                 name,
                                 fieldDefinitions,
                                 cType,
                                 cardinality,
                                 pointerDepth,
                                 dataType,
                                 union);
        }
    }

    private void parseFieldAnnotation(final int i,
                                      final String fieldName,
                                      final LinkedList<FieldDefinition> fieldDefinitions,
                                      final VariableElement cType,
                                      final Integer cardinality,
                                      final Integer pointerDepth,
                                      final TypeMirror dataType,
                                      final Boolean union) {

        final CType cTypeInstance = CType.valueOf(cType.getSimpleName()
                                                       .toString());

        switch (cTypeInstance) {
            case CHAR: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Byte.class,
                                                           null,
                                                           "FFI_TYPE_SINT8",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case UNSIGNED_CHAR: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Byte.class,
                                                           null,
                                                           "FFI_TYPE_UINT8",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case SHORT: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Short.class,
                                                           null,
                                                           "FFI_TYPE_SINT16",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case UNSIGNED_SHORT: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Short.class,
                                                           null,
                                                           "FFI_TYPE_UINT16",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case INT: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Integer.class,
                                                           null,
                                                           "FFI_TYPE_SINT32",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case UNSIGNED_INT: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Integer.class,
                                                           null,
                                                           "FFI_TYPE_UINT32",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case LONG: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           CLong.class,
                                                           null,
                                                           "FFI_TYPE_SLONG",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case UNSIGNED_LONG: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           CLong.class,
                                                           null,
                                                           "FFI_TYPE_ULONG",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case LONG_LONG: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Long.class,
                                                           null,
                                                           "FFI_TYPE_SINT64",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case UNSIGNED_LONG_LONG: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Long.class,
                                                           null,
                                                           "FFI_TYPE_UINT64",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case FLOAT: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Float.class,
                                                           null,
                                                           "FFI_TYPE_FLOAT",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case DOUBLE: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Double.class,
                                                           null,
                                                           "FFI_TYPE_DOUBLE",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case POINTER: {
                fieldDefinitions.add(createFieldDefinition(i,
                                                           fieldName,
                                                           fieldDefinitions,
                                                           Pointer.class,
                                                           dataType,
                                                           "FFI_TYPE_POINTER",
                                                           union,
                                                           cardinality,
                                                           pointerDepth));
                break;
            }
            case STRUCT: {
                if (dataType == null) {
                    this.messager.printMessage(Diagnostic.Kind.ERROR,
                                               "Data type of struct must be specified.",
                                               cType);
                    return;
                }

                final DeclaredType structTypeType = (DeclaredType) dataType;
                if (structTypeType.asElement()
                                  .getSimpleName()
                                  .toString()
                                  .equals("StructType")) {
                    this.messager.printMessage(Diagnostic.Kind.ERROR,
                                               "Declared struct type must be a subclass of 'com.github.zubnix.jaccall.StructType'.",
                                               cType);
                    return;
                }

                addStruct(i,
                          fieldName,
                          fieldDefinitions,
                          cardinality,
                          structTypeType,
                          union);
                break;
            }
        }
    }

    private void addStruct(final int i,
                           final String fieldName,
                           final LinkedList<FieldDefinition> fieldDefinitions,
                           final Integer cardinality,
                           final DeclaredType structTypeType,
                           final Boolean union) {

        final TypeName structTypeName = ClassName.get(structTypeType);

        final CodeBlock ffiTypeCode = CodeBlock.builder()
                                               .add("$T.FFI_TYPE",
                                                    structTypeName)
                                               .build();
        final CodeBlock offsetCode;
        if (i == 0 || union) {
            //we're the first field
            offsetCode = CodeBlock.builder()
                                  .addStatement("0")
                                  .build();
        }
        else {
            final FieldDefinition previous = fieldDefinitions.get(i - 1);

            offsetCode = CodeBlock.builder()
                                  .add("$[$T.newOffset($T.alignment(($T) null), OFFSET_$L + ($L * $L))$]",
                                       Types.class,
                                       Types.class,
                                       findFirstNonStructField(structTypeType),
                                       i - 1,
                                       previous.getSizeOfCode(),
                                       previous.getCardinality())
                                  .build();
        }

        final CodeBlock sizeOfCode = CodeBlock.builder()
                                              .add("$T.SIZE",
                                                   structTypeName)
                                              .build();

        final List<MethodSpec> accessors = new LinkedList<>();
        if (cardinality > 1) {
            accessors.add(MethodSpec.methodBuilder(fieldName)
                                    .addModifiers(Modifier.PUBLIC,
                                                  Modifier.FINAL)
                                    .returns(ParameterizedTypeName.get(ClassName.get(Pointer.class),
                                                                       structTypeName))
                                    .addStatement("return readArray(OFFSET_$L, $T.class)",
                                                  i,
                                                  structTypeName)
                                    .build());
        }
        else {
            final MethodSpec read = MethodSpec.methodBuilder(fieldName)
                                              .addModifiers(Modifier.PUBLIC,
                                                            Modifier.FINAL)
                                              .returns(structTypeName)
                                              .addStatement("return readStructType(OFFSET_$L, $T.class)",
                                                            i,
                                                            structTypeName)
                                              .build();

            final MethodSpec write = MethodSpec.methodBuilder(fieldName)
                                               .addModifiers(Modifier.PUBLIC,
                                                             Modifier.FINAL)
                                               .addParameter(structTypeName,
                                                             fieldName,
                                                             Modifier.FINAL)
                                               .addStatement("writeStructType(OFFSET_$L, $N)",
                                                             i,
                                                             fieldName)
                                               .build();
            accessors.add(read);
            accessors.add(write);
        }

        fieldDefinitions.add(new FieldDefinition(ffiTypeCode,
                                                 offsetCode,
                                                 sizeOfCode,
                                                 cardinality,
                                                 accessors));
    }

    private Object findFirstNonStructField(final DeclaredType structTypeType) {
        final List<? extends AnnotationMirror> annotationMirrors = structTypeType.asElement()
                                                                                 .getAnnotationMirrors();
        for (final AnnotationMirror annotationMirror : annotationMirrors) {
            if (annotationMirror.getAnnotationType()
                                .asElement()
                                .getSimpleName()
                                .toString()
                                .equals(STRUCT)) {
                for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues()
                                                                                                                     .entrySet()) {
                    if (entry.getKey()
                             .getSimpleName()
                             .toString()
                             .equals("value")) {
                        final List<? extends AnnotationValue> fields = (List<? extends AnnotationValue>) entry.getValue()
                                                                                                              .getValue();
                        final AnnotationValue  firstField            = fields.get(0);
                        final AnnotationMirror fieldAnnotationMirror = (AnnotationMirror) firstField.getValue();

                        VariableElement cType    = null;
                        TypeMirror      dataType = null;
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
                                                   .equals("dataType")) {
                                dataType = (TypeMirror) fieldAttribute.getValue()
                                                                      .getValue();
                            }
                        }

                        if (cType != null) {
                            final CType cTypeInstance = CType.valueOf(cType.getSimpleName()
                                                                           .toString());
                            switch (cTypeInstance) {
                                case CHAR:
                                    return Byte.class;
                                case UNSIGNED_CHAR:
                                    return Byte.class;
                                case SHORT:
                                    return Short.class;
                                case UNSIGNED_SHORT:
                                    return Short.class;
                                case INT:
                                    return Integer.class;
                                case UNSIGNED_INT:
                                    return Integer.class;
                                case LONG:
                                    return CLong.class;
                                case UNSIGNED_LONG:
                                    return CLong.class;
                                case LONG_LONG:
                                    return Long.class;
                                case UNSIGNED_LONG_LONG:
                                    return Long.class;
                                case FLOAT:
                                    return Float.class;
                                case DOUBLE:
                                    return Double.class;
                                case POINTER:
                                    return Pointer.class;
                                case STRUCT:
                                    //nested struct in a nested struct
                                    return findFirstNonStructField((DeclaredType) dataType);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private FieldDefinition createFieldDefinition(final int i,
                                                  final String fieldName,
                                                  final LinkedList<FieldDefinition> fieldDefinitions,
                                                  final Class<?> javaType,
                                                  final TypeMirror dataType,
                                                  final String ffiType,
                                                  final Boolean union,
                                                  final Integer cardinality,
                                                  Integer pointerDepth) {
        final CodeBlock alignmentCode = CodeBlock.builder()
                                                 .add("$T.alignment(($T) null)",
                                                      Types.class,
                                                      javaType)
                                                 .build();
        final CodeBlock sizeOfCode = CodeBlock.builder()
                                              .add("$T.sizeof(($T) null)",
                                                   Size.class,
                                                   javaType)
                                              .build();
        final CodeBlock ffiTypeCode = CodeBlock.builder()
                                               .add("$T.$L",
                                                    JNI.class,
                                                    ffiType)
                                               .build();
        final CodeBlock offsetCode;

        if (i == 0 || union) {
            //we're the first field
            offsetCode = CodeBlock.builder()
                                  .add("0")
                                  .build();
        }
        else {
            final FieldDefinition previous = fieldDefinitions.get(i - 1);

            offsetCode = CodeBlock.builder()
                                  .add("$[$T.newOffset($L, OFFSET_$L + ($L * $L))$]",
                                       Types.class,
                                       alignmentCode,
                                       i - 1,
                                       previous.getSizeOfCode(),
                                       previous.getCardinality())
                                  .build();
        }


        final List<MethodSpec> accessors = new LinkedList<>();

        if (cardinality > 1) {
            if (Pointer.class.isAssignableFrom(javaType)) {
                //array defines an implicit extra pointer depth
                pointerDepth++;

                TypeName dataTypeName = dataType == null ? ClassName.get(Void.class) : ClassName.get(dataType);
                if (dataTypeName.isPrimitive()) {
                    dataTypeName = dataTypeName.box();
                }

                ParameterizedTypeName pointerType = ParameterizedTypeName.get(ClassName.get(Pointer.class),
                                                                              dataTypeName);

                String statement = "return readArray(OFFSET_$L, $T.class)";
                for (int j = 0; j < pointerDepth; j++) {
                    pointerType = ParameterizedTypeName.get(ClassName.get(Pointer.class),
                                                            pointerType);
                    statement += ".castpp()";
                }

                accessors.add(MethodSpec.methodBuilder(fieldName)
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .returns(pointerType)
                                        .addStatement(statement,
                                                      i,
                                                      dataType)
                                        .build());
            }
            else {
                accessors.add(MethodSpec.methodBuilder(fieldName)
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL)
                                        .returns(ParameterizedTypeName.get(Pointer.class,
                                                                           javaType))
                                        .addStatement("return readArray(OFFSET_$L, $T.class)",
                                                      i,
                                                      javaType)
                                        .build());
            }
        }
        else if (Pointer.class.isAssignableFrom(javaType)) {
            TypeName dataTypeName = dataType == null ? ClassName.get(Void.class) : ClassName.get(dataType);
            if (dataTypeName.isPrimitive()) {
                dataTypeName = dataTypeName.box();
            }

            ParameterizedTypeName pointerType = ParameterizedTypeName.get(ClassName.get(Pointer.class),
                                                                          dataTypeName);

            String statement = "return readPointer(OFFSET_$L, $T.class)";
            for (int j = 0; j < pointerDepth; j++) {
                pointerType = ParameterizedTypeName.get(ClassName.get(Pointer.class),
                                                        pointerType);
                statement += ".castpp()";
            }

            //read
            accessors.add(MethodSpec.methodBuilder(fieldName)
                                    .addModifiers(Modifier.PUBLIC,
                                                  Modifier.FINAL)
                                    .returns(pointerType)
                                    .addStatement(statement,
                                                  i,
                                                  dataTypeName)
                                    .build());
            //write
            accessors.add(MethodSpec.methodBuilder(fieldName)
                                    .addModifiers(Modifier.PUBLIC,
                                                  Modifier.FINAL)
                                    .addParameter(pointerType,
                                                  CodeBlock.builder()
                                                           .add("$N",
                                                                fieldName)
                                                           .build()
                                                           .toString(),
                                                  Modifier.FINAL)
                                    .addStatement("writePointer(OFFSET_$L, $N)",
                                                  i,
                                                  fieldName)
                                    .build());
        }
        else {
            //read
            accessors.add(MethodSpec.methodBuilder(fieldName)
                                    .addModifiers(Modifier.PUBLIC,
                                                  Modifier.FINAL)
                                    .returns(Primitives.unwrap(javaType))
                                    .addStatement("return read$L(OFFSET_$L)",
                                                  javaType.getSimpleName(),
                                                  i)
                                    .build());
            //write
            accessors.add(MethodSpec.methodBuilder(fieldName)
                                    .addModifiers(Modifier.PUBLIC,
                                                  Modifier.FINAL)
                                    .addParameter(Primitives.unwrap(javaType),
                                                  CodeBlock.builder()
                                                           .add("$N",
                                                                fieldName)
                                                           .build()
                                                           .toString(),
                                                  Modifier.FINAL)
                                    .addStatement("write$L(OFFSET_$L, $N)",
                                                  javaType.getSimpleName(),
                                                  i,
                                                  fieldName)
                                    .build());
        }

        return new FieldDefinition(ffiTypeCode,
                                   offsetCode,
                                   sizeOfCode,
                                   cardinality,
                                   accessors);
    }
}
