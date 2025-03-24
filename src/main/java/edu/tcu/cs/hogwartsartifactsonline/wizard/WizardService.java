package wizard;

import artifact.ArtifactRepository;
import artifact.ArtifactService;
import artifact.artifact;
import system.exception.ObjectNotFoundException;

@Service
@Transactional
public class WizardService {
    private final wizard.WizardRepository wizardRepository;

    private final ArtifactRepository artifactRepository;

    public WizardService(wizard.WizardRepository wizardRepository, ArtifactRepository artifactRepository) {
        this.wizardRepository = wizardRepository;
        this.artifactRepository = artifactRepository;
    }

    public List<wizard> findAll() {
        return this.wizardRepository.findAll();
    }

    public wizard findById(Integer wizardId) {
        return this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException(wizardId));
    }

    public wizard save(wizard newWizard) {
        return this.wizardRepository.save(newWizard);
    }

    public wizard update(Integer wizardId, wizard update) {
        return this.wizardRepository.findById(wizardId).map(oldWizard -> {
            oldWizard.setName(update.getName());
            return this.wizardRepository.save(oldWizard);
        });
    }

    public void delete(Integer wizardId) {
        wizard wizardToBeDelete = this.wizardRepository.findById(wizardId)
                .orElsethrow(() -> new ObjectNotFoundException(wizardId));

        wizardToBeDelete.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }

    public void assignArtifact(Integer wizardId, String artifactId) {
        artifact artifactToBeAssigned = this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));

        wizard wizard = this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        if(artifactToBeAssigned.getOwner() != null) {
            artifactToBeAssigned.getOwner().removeArtifact(artifactToBeAssigned);
        }

        wizard.addArtifact(artifactToBeAssigned);
    }

}
