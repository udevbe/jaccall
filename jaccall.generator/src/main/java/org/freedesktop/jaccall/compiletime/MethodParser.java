package org.freedesktop.jaccall.compiletime;


import org.freedesktop.jaccall.ByVal;
import org.freedesktop.jaccall.JNI;
import org.freedesktop.jaccall.Lng;
import org.freedesktop.jaccall.Ptr;
import org.freedesktop.jaccall.Unsigned;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class MethodParser {

    private static final String UNSIGNED = Unsigned.class.getSimpleName();
    private static final String LNG      = Lng.class.getSimpleName();
    private static final String PTR      = Ptr.class.getSimpleName();
    private static final String BY_VAL   = ByVal.class.getSimpleName();

    private final Messager messager;

    public MethodParser(final Messager messager) {
        this.messager = messager;
    }

    public String parseMethodName(final ExecutableElement executableElement) {
        return executableElement.getSimpleName()
                                .toString();
    }

    public int parseArgSize(final ExecutableElement executableElement) {
        return executableElement.getParameters()
                                .size();
    }

    public String parseJniSignature(final ExecutableElement executableElement) {
        final StringBuilder jniSignature = new StringBuilder();
        jniSignature.append('(');
        for (final VariableElement variableElement : executableElement.getParameters()) {
            jniSignature.append(parseJNIChar(variableElement.asType(),
                                             variableElement));
        }
        jniSignature.append(')');
        jniSignature.append(parseJNIChar(executableElement.getReturnType(),
                                         executableElement));

        return jniSignature.toString();
    }

    private char parseJNIChar(final TypeMirror typeMirror,
                              final Element element) {
        final TypeKind kind = typeMirror.getKind();

        switch (kind) {
            case BYTE:
                return 'B';
            case SHORT:
                return 'S';
            case INT:
                return 'I';
            case LONG:
                return 'J';
            case FLOAT:
                return 'F';
            case DOUBLE:
                return 'D';
            case VOID:
                return 'V';
            default:
                this.messager.printMessage(Diagnostic.Kind.ERROR,
                                           "Unsupported type " + typeMirror,
                                           element);
                return 0;
        }
    }

    public CodeBlock parseFfiSignature(final ExecutableElement executableElement) {

        final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

        //return type
        codeBlockBuilder.add(parseFfiString(executableElement.getReturnType(),
                                            executableElement));

        //arguments
        for (final VariableElement variableElement : executableElement.getParameters()) {
            codeBlockBuilder.add(", ");
            codeBlockBuilder.add(parseFfiString(variableElement.asType(),
                                                variableElement));
        }

        return codeBlockBuilder.build();
    }

    private CodeBlock parseFfiString(final TypeMirror typeMirror,
                                     final Element element) {

        final CodeBlock.Builder builder = CodeBlock.builder();

        Map<? extends ExecutableElement, ? extends AnnotationValue> unsigned = null;
        Map<? extends ExecutableElement, ? extends AnnotationValue> lng      = null;
        Map<? extends ExecutableElement, ? extends AnnotationValue> ptr      = null;
        Map<? extends ExecutableElement, ? extends AnnotationValue> byVal    = null;

        for (final AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            final DeclaredType annotationType = annotationMirror.getAnnotationType();
            final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();

            final String simpleName = annotationType.asElement()
                                                    .getSimpleName()
                                                    .toString();

            if (simpleName.equals(PTR)) {
                ptr = elementValues;
            }
            else if (simpleName.equals(UNSIGNED)) {
                unsigned = elementValues;
            }
            else if (simpleName.equals(LNG)) {
                lng = elementValues;
            }
            else if (simpleName.equals(BY_VAL)) {
                byVal = elementValues;
            }
        }

        final TypeKind kind = typeMirror.getKind();
        switch (kind) {
            case BYTE:
                if (unsigned == null) {
                    builder.add("$T.FFI_TYPE_SINT8",
                                JNI.class);
                }
                else {
                    builder.add("$T.FFI_TYPE_UINT8",
                                JNI.class);
                }
                break;
            case SHORT:
                if (unsigned == null) {
                    builder.add("$T.FFI_TYPE_SINT16",
                                JNI.class);
                }
                else {
                    builder.add("$T.FFI_TYPE_UINT16",
                                JNI.class);
                }
                break;
            case INT:
                if (unsigned == null) {
                    builder.add("$T.FFI_TYPE_SINT32",
                                JNI.class);
                }
                else {
                    builder.add("$T.FFI_TYPE_UINT32",
                                JNI.class);
                }
                break;
            case LONG:
                if (ptr != null) {
                    //it's a pointer
                    builder.add("$T.FFI_TYPE_POINTER",
                                JNI.class);
                }
                else if (byVal != null) {
                    //it's a struct by value
                    builder.add(parseByVal(byVal));
                }
                else if (lng != null && unsigned != null) {
                    //it's an unsigned long long
                    builder.add("$T.FFI_TYPE_UINT64",
                                JNI.class);
                }
                else if (lng != null) {
                    //it's a signed long long
                    builder.add("$T.FFI_TYPE_SINT64",
                                JNI.class);
                }
                else if (unsigned != null) {
                    //it's an unsigned long
                    builder.add("$T.FFI_TYPE_ULONG",
                                JNI.class);
                }
                else {
                    //it's an signed long
                    builder.add("$T.FFI_TYPE_SLONG",
                                JNI.class);
                }
                break;
            case FLOAT:
                builder.add("$T.FFI_TYPE_FLOAT",
                            JNI.class);
                break;
            case DOUBLE:
                builder.add("$T.FFI_TYPE_DOUBLE",
                            JNI.class);
                break;
            case VOID:
                builder.add("$T.FFI_TYPE_VOID",
                            JNI.class);
                break;
            default:
                this.messager.printMessage(Diagnostic.Kind.ERROR,
                                           "Unsupported type " + typeMirror,
                                           element);
        }

        return builder.build();
    }

    private CodeBlock parseByVal(final Map<? extends ExecutableElement, ? extends AnnotationValue> byVal) {
        final CodeBlock.Builder builder = CodeBlock.builder();
        for (final Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> annotationEntry : byVal.entrySet()) {
            if (annotationEntry.getKey()
                               .getSimpleName()
                               .toString()
                               .equals("value")) {
                final AnnotationValue value = annotationEntry.getValue();
                final TypeMirror structClass = (TypeMirror) value.getValue();

                builder.add(parseStructFields(annotationEntry.getKey(),
                                              structClass));
                break;
            }
        }
        return builder.build();
    }

    private CodeBlock parseStructFields(final Element element,
                                        final TypeMirror structClass) {
        final DeclaredType structTypeType    = (DeclaredType) structClass;
        final Element      structTypeElement = structTypeType.asElement();

        final String structTypeName = structTypeElement.getSimpleName()
                                                       .toString();
        if (structTypeName.equals("StructType")) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Declared struct type must be a subclass of 'StructType'.",
                                       element);
        }

        //TODO check if struct type element has a struct annotation and fail if it does not
        //structTypeElement.getAnnotationMirrors()

        final Set<PackageElement> packageElements = ElementFilter.packagesIn(Collections.singleton(structTypeElement.getEnclosingElement()));
        if (packageElements.isEmpty()) {
            this.messager.printMessage(Diagnostic.Kind.ERROR,
                                       "Declared struct type must be a top level type.",
                                       element);
        }

        final CodeBlock.Builder builder = CodeBlock.builder();
        for (final PackageElement packageElement : packageElements) {
            final String packageName = packageElement.getQualifiedName()
                                                     .toString();
            builder.add("$T.FFI_TYPE",
                        ClassName.get(packageName,
                                      structTypeName));
        }
        return builder.build();
    }
}
