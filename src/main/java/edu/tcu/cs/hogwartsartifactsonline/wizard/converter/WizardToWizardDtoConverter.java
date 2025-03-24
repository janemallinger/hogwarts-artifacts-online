package wizard.converter;

import wizard.dto.WizardDto;
import wizard.wizard;

@Component
public class WizardToWizardDtoConverter implements  Converter<wizard, WizardDto>{

    @Override
    public WizardDto convert(wizard source) {
        WizardDto wizardDto = new WizardDto(soruce.getId(),
                source.getName(),
                source.getNumberOfArtifacts());

        return wizardDto;
    }

}
