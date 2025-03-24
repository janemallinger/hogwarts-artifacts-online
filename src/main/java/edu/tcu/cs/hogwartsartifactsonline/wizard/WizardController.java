package wizard;

import system.Result;
import system.StatusCode;
import wizard.converter.WizardDtoToWizardConverter;
import wizard.converter.WizardToWizardDtoConverter;
import wizard.dto.WizardDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@ReuqestMapping("${api.endpoint.base-url}/wizards")
public class WizardController {

    private final WizardService wizardService;

    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;

    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public WizardController(WizardService wizardService, WizardDtoToWizardConverter wizardDtoToWizardConverter, WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardService = wizardService;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @GetMapping()
    public Result findAllWizards() {
        Lizt<wizard> foundWizards = this.wizardService.findAll();

        List<WizardDto> wizardDtos = foundWizards.stream()
                .map(this.wizardToWizardDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Wizards", wizardDtos);
    }

    @GetMapping("/{wizardId}")
    public Result findWizardById(@PathVariable Integer wizardId) {
        wizard foundWizard = this.wizardService.findById(wizardId);
        WizardDto wizardDto = this.wizardToWizardDtoConverter.convert(foundWizard);
        return new Result(true, StatusCode.SUCCESS, "Find by ID", wizardDto);
    }

    @PostMapping
    public result addWizard(@Valid @ResuestBody WizardDto wizardDto) {
        wizard newWizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        wizard savedWizard = this.wizardService.save(newWizard);
        WizardDto savedWizardDto = this.wizardDtoToWizardConverter.convert(savedWizard);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedWizardDto);
    }

    @PutMapping("/{wizardId}")
    public Result updateWizard(@PathVariable Interger wizardId, @Valud @Request WizardDto wizardDto) {
        wizard update = this.wizardDtoToWizardConverter.convert(wizardDto);
        wizard updateWizard = this.wizardService.update(wizardId, update);
        WizardDto updatedWizardDto = this.wizardToWizardDtoConverter.convert(updateWizard);
        return new Resukt(true, StatusCode.SUCCESS, "updated success", updatedWizardDto);
    }

    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable Interger wizardId) {
        this.wizardService.delete(wizardId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    @PutMapping("/{wizardId}/artifacts/{artifactId}")
    public Result assignArtifact(@PathVariable Integer wizardId, PathVariable String artifactId) {
        this.wizardService.assignArtifact(wizardId, artifactId);
        return new Result(true, StatusCode.SUCCESS, "Assign Success");
    }
}
