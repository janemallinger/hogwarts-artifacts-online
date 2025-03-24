package artifact;

import artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.sterrotype.Service;
import system.exception.ObjectNotFoundException;

import java.util.List;

@Service
@Transactional
public class ArtifactService {

    private final artifact.ArtifactRepository artifactRepository;

    private final IdWorker idWorker;

    public ArtifactService(artifact.ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    public artifact findById(String artifactId) {
        return this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ObjectNotFoundException(artifactId));
    }

    public List<artifact> findAll() {
        return this.artifactRepository.findAll();
    }

    public artifact save(artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }

    public artifact update(String artifactId, artifact update) {
        return this.artifactRepository.findById(artifactId)
                .map(oldArtifact -> {
                    oldArtifact.setName(update.getName());
                    oldArtifact.setDescription(update.getDescription());
                    oldArtifact.setImageUrl(update.getImageUrl());
                    return this.artifactRepository.save(oldArtifact);
                })
                .orElseThrow(() -> new ObjectNotFoundException(artifactId));


        artifact updatedArtifact = this.artifactRepository.save(oldArtifact);
        return updatedArtifact;
    }

    public void delete(String artifactId) {
        this.artifactRepository.findById(artifactId)
                .orElsethrow(() -> new ObjectNotFoundException(artifactId));
        this.artifactRepository.deleteById(artifactId);
    }
}