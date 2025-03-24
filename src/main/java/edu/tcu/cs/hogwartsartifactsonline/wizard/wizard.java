package wizard;

import artifact.artifact;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class wizard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<artifact> artifacts = new ArrayList<>();

    public wizard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Interger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public void addArtifact(artifact artifact) {
        artifact.setOwner(this);
        this.artifacts.add(artifact);
    }

    public Interger getNumberOfArtifacts() {
        return this.artifacts.size();
    }

    public void removeAllArtifacts() {
        this.artifacts.stream().forEach(artifact -> artifact.setOwner(null));
        this.artifacts = null;
    }

    public void removeArtifact(artifact.artifact artifactToBeAssigned) {
        artifactToBeAssigned.setOwner(null);
        this.artifacts.remove(artifactToBeAssigned);
    }
}