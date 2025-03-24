package wizard.converter;

import wizard.dto.WizardDto;
import wizard.wizard;

@Component
public class WizardDtoToWizardConverter implements  Converter<WizardDto, wizard> {

    @Override
    public wizard convert(WizardDto source) {
        wizard wizard = new wizard();
        wizard.setId(source.id());
        wizard.setName(source.name());
        return wizard;
    }
}
