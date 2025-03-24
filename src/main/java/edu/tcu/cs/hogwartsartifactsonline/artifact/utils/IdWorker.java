package artifact.dto;

import wizard.WizardDto;

public record ArtifactDto(String id,
                          @NotEmpty(message = "name is required")
                          String name,

                          @NotEmpty(message = "description is required")
                          String description,

                          @NotEmpty(message = "imageUrl is required")
                          String ImageUrl,

                          WizardDto owner) {

}
