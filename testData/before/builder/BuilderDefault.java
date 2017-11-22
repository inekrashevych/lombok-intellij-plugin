import lombok.NonNull;

@lombok.Value
@lombok.Builder
public class BuilderDefault {
  @NonNull
  String nonNullVariable;

  @lombok.Builder.Default
  String variable = "value";
}
