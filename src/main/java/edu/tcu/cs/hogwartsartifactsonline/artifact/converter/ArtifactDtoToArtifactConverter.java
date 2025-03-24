package artifact.converter;

import artifact.artifact;
import artifact.dto.ArtifactDto;

import java.lang.reflect.ParameterizedType;

@Component
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, artifact> {

    @Override
    public artifact convert(ArtifactDto source) {
        artifact artifact = new artifact();
        artifact.setId(source.id());
        artifact.setName(source.name());
        artifact.setDescription(source.description());
        artifact.setImageUrl(source.ImageUrl());

        return artifact;
    }

}
