package artifact;

import artifact.converter.ArtifactToArtifactDtoConverter;
import artifact.dto.ArtifactDto;
import org.springframework.web.bind.annotation.RestController;
import system.StatusCode;

import javax.xml.transform.Result;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtifactController {

    private final artifact.ArtifactService artifactService;

    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;

    public ArtifactController(artifact.ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
    }

    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        artifact foundArtifact = this.artifactService.findById(artifactId);
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(foundArtifact);
        return new Result(true, StatusCode.SUCCESS, "find one success", artifactDto);
    }

    @GetMapping("/api/v1/artifacts")
    public Result findArtifacts() {
        List<artifact> foundArtifacts = this.artifactService.findAll();
        List<ArtifactDto> artifactDtos = foundArtifacts.stream()
                .map(this.artifactToArtifactDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find all success", artifactDtos);
    }

    @PostMapping("/api/v1/artifacts")
    public Result addArtifact(@RequestBody ArtifactDto artifactDto) {
        return null;
    }
}
