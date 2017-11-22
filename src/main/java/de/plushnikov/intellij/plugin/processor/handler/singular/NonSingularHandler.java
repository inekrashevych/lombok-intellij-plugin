package de.plushnikov.intellij.plugin.processor.handler.singular;

import java.text.MessageFormat;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiVariable;

import de.plushnikov.intellij.plugin.processor.field.AccessorsInfo;
import de.plushnikov.intellij.plugin.psi.LombokLightFieldBuilder;
import de.plushnikov.intellij.plugin.psi.LombokLightMethodBuilder;
import de.plushnikov.intellij.plugin.util.PsiAnnotationSearchUtil;
import de.plushnikov.intellij.plugin.util.PsiMethodUtil;
import lombok.Builder;

class NonSingularHandler implements BuilderElementHandler {
  private static final String SETTER_PREFIX = "set";

  private final boolean shouldGenerateFullBodyBlock;

  NonSingularHandler(boolean shouldGenerateFullBodyBlock) {
    this.shouldGenerateFullBodyBlock = shouldGenerateFullBodyBlock;
  }

  public void addBuilderField(@NotNull List<PsiField> fields, @NotNull PsiVariable psiVariable, @NotNull PsiClass innerClass, @NotNull AccessorsInfo accessorsInfo, @NotNull PsiSubstitutor builderSubstitutor) {
    final String fieldName = accessorsInfo.removePrefix(psiVariable.getName());
    if (PsiAnnotationSearchUtil.isAnnotatedWith(psiVariable, Builder.Default.class)) {
      final LombokLightFieldBuilder fieldSetBuilder =
        new LombokLightFieldBuilder(psiVariable.getManager(), fieldName + "$set", PsiType.BOOLEAN)
          .withModifier(PsiModifier.PRIVATE)
          .withNavigationElement(psiVariable)
          .withContainingClass(innerClass);
      fields.add(fieldSetBuilder);
    }
    final LombokLightFieldBuilder fieldBuilder =
      new LombokLightFieldBuilder(psiVariable.getManager(), fieldName, builderSubstitutor.substitute(psiVariable.getType()))
        .withModifier(PsiModifier.PRIVATE)
        .withNavigationElement(psiVariable)
        .withContainingClass(innerClass);
    fields.add(fieldBuilder);
  }

  public void addBuilderMethod(@NotNull List<PsiMethod> methods, @NotNull PsiVariable psiVariable, @NotNull String fieldName, @NotNull PsiClass innerClass, boolean fluentBuilder, PsiType returnType, String psiFieldName, PsiSubstitutor builderSubstitutor) {
    boolean hasBuilderDefault = PsiAnnotationSearchUtil.isAnnotatedWith(psiVariable, Builder.Default.class);
    methods.add(new LombokLightMethodBuilder(psiVariable.getManager(), createSetterName(psiFieldName, fluentBuilder))
      .withMethodReturnType(returnType)
      .withContainingClass(innerClass)
      .withParameter(psiFieldName, builderSubstitutor.substitute(psiVariable.getType()))
      .withNavigationElement(psiVariable)
      .withModifier(PsiModifier.PUBLIC)
      .withBody(createCodeBlock(innerClass, fluentBuilder, psiFieldName, hasBuilderDefault)));
  }

  @NotNull
  private PsiCodeBlock createCodeBlock(@NotNull PsiClass innerClass, boolean fluentBuilder, String psiFieldName,
                                       boolean useDefault) {
    final String blockText;
    if (shouldGenerateFullBodyBlock) {
      blockText = getAllMethodBody(psiFieldName, fluentBuilder, useDefault);
    } else {
      blockText = "return null;";
    }
    return PsiMethodUtil.createCodeBlockFromText(blockText, innerClass);
  }

  @Override
  public String createSingularName(PsiAnnotation singularAnnotation, String psiFieldName) {
    return psiFieldName;
  }

  @NotNull
  private String createSetterName(@NotNull String fieldName, boolean isFluent) {
    return isFluent ? fieldName : SETTER_PREFIX + StringUtil.capitalize(fieldName);
  }

  private String getAllMethodBody(@NotNull String psiFieldName, boolean fluentBuilder, boolean useDefault) {
    final String codeBlockTemplate = useDefault ? "this.{0} = {0};this.{0}$set = true;{1}" : "this.{0} = {0};{1}";
    return MessageFormat.format(codeBlockTemplate, psiFieldName, fluentBuilder ? "\nreturn this;" : "");
  }

  @Override
  public void appendBuildPrepare(@NotNull StringBuilder buildMethodParameters, @NotNull PsiVariable psiVariable, @NotNull String fieldName) {
  }

  @Override
  public void appendBuildCall(@NotNull StringBuilder buildMethodParameters, @NotNull String fieldName, String className, boolean builderDefault) {
    buildMethodParameters.append(
      builderDefault ? MessageFormat.format("this.{0}$set ? this.{0} : {1}.$default${0}()", fieldName, className) : fieldName);
  }
}
