package artifact.dto;

import wizard.WizardDto;

public record ArtifactDto(String id,
                          String name,
                          String description,
                          String ImageUrl,
                          WizardDto owner) {

}
