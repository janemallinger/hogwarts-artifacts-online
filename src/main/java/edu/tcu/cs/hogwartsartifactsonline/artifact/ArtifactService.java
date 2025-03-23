package artifact;

import artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.sterrotype.Service;

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
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<artifact> findAll() {
        return this.artifactRepository.findAll();
    }

    public artifact save(artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }
}
