package de.plushnikov.intellij.plugin.processor.handler.singular;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiVariable;

import de.plushnikov.intellij.plugin.processor.field.AccessorsInfo;

public interface BuilderElementHandler {
  void addBuilderField(@NotNull List<PsiField> fields, @NotNull PsiVariable psiVariable, @NotNull PsiClass innerClass, @NotNull AccessorsInfo accessorsInfo, @NotNull PsiSubstitutor substitutor);

  void addBuilderMethod(@NotNull List<PsiMethod> methods, @NotNull PsiVariable psiVariable, @NotNull String fieldName, @NotNull PsiClass innerClass, boolean fluentBuilder, PsiType returnType, String singularName, PsiSubstitutor builderSubstitutor);

  String createSingularName(PsiAnnotation singularAnnotation, String psiFieldName);

  void appendBuildPrepare(@NotNull StringBuilder buildMethodParameters, @NotNull PsiVariable psiVariable, @NotNull String fieldName);

  void appendBuildCall(@NotNull StringBuilder buildMethodParameters, @NotNull String fieldName, String className, boolean builderDefault);
}
