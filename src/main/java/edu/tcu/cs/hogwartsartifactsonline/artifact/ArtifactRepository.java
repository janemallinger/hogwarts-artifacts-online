package artifact;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ArtifactRepository extends JpaRepository<artifact, String>{
}
