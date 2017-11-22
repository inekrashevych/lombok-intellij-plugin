import java.beans.ConstructorProperties;
import lombok.NonNull;

public final class BuilderDefault {
  @NonNull
  private final String nonNullVariable;
  private final String variable;

  private static String $default$variable() {
    return "value";
  }

  @ConstructorProperties({"nonNullVariable", "variable"})
  BuilderDefault(@NonNull String nonNullVariable, String variable) {
    if (nonNullVariable == null) {
      throw new NullPointerException("nonNullVariable");
    } else {
      this.nonNullVariable = nonNullVariable;
      this.variable = variable;
    }
  }

  public static BuilderDefault.BuilderDefaultBuilder builder() {
    return new BuilderDefault.BuilderDefaultBuilder();
  }

  @NonNull
  public String getNonNullVariable() {
    return this.nonNullVariable;
  }

  public String getVariable() {
    return this.variable;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof BuilderDefault)) {
      return false;
    } else {
      BuilderDefault other = (BuilderDefault)o;
      Object this$nonNullVariable = this.getNonNullVariable();
      Object other$nonNullVariable = other.getNonNullVariable();
      if (this$nonNullVariable == null) {
        if (other$nonNullVariable != null) {
          return false;
        }
      } else if (!this$nonNullVariable.equals(other$nonNullVariable)) {
        return false;
      }

      Object this$variable = this.getVariable();
      Object other$variable = other.getVariable();
      if (this$variable == null) {
        if (other$variable != null) {
          return false;
        }
      } else if (!this$variable.equals(other$variable)) {
        return false;
      }

      return true;
    }
  }

  public int hashCode() {
    int PRIME = true;
    int result = 1;
    Object $nonNullVariable = this.getNonNullVariable();
    int result = result * 59 + ($nonNullVariable == null ? 43 : $nonNullVariable.hashCode());
    Object $variable = this.getVariable();
    result = result * 59 + ($variable == null ? 43 : $variable.hashCode());
    return result;
  }

  public String toString() {
    return "BuilderDefault(nonNullVariable=" + this.getNonNullVariable() + ", variable=" + this.getVariable() + ")";
  }

  public static class BuilderDefaultBuilder {
    private String nonNullVariable;
    private boolean variable$set;
    private String variable;

    BuilderDefaultBuilder() {
    }

    public BuilderDefault.BuilderDefaultBuilder nonNullVariable(String nonNullVariable) {
      this.nonNullVariable = nonNullVariable;
      return this;
    }

    public BuilderDefault.BuilderDefaultBuilder variable(String variable) {
      this.variable = variable;
      this.variable$set = true;
      return this;
    }

    public BuilderDefault build() {
      return new BuilderDefault(this.nonNullVariable, this.variable$set ? this.variable : BuilderDefault.$default$variable());
    }

    public String toString() {
      return "BuilderDefault.BuilderDefaultBuilder(nonNullVariable=" + this.nonNullVariable + ", variable=" + this.variable + ")";
    }
  }
}
