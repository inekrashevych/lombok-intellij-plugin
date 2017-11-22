public class BuilderDefault {
  private String variable;

  @java.beans.ConstructorProperties({"variable"})
  BuilderDefault(String variable) {
    this.variable = variable;
  }

  public static BuilderDefaultBuilder builder() {
    return new BuilderDefaultBuilder();
  }

  private static String $default$variable() {
    return "defaultValue";
  }

  public static class BuilderDefaultBuilder {
    private boolean variable$set;
    private String variable;

    BuilderDefaultBuilder() {
    }

    public BuilderDefaultBuilder variable(String variable) {
      this.variable = variable;
      this.variable$set = true;
      return this;
    }

    public BuilderDefault build() {
      return new BuilderDefault(this.variable$set ? this.variable : BuilderDefault.$default$variable());
    }

    public String toString() {
      return "BuilderDefault.BuilderDefaultBuilder(variable=" + this.variable + ")";
    }
  }
}
