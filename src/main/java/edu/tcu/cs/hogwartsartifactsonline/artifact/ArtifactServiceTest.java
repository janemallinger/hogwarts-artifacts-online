package artifact;

import org.junit.Test;
import wizard.wizard;


import static javax.management.Query.times;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import BDDAssumptions.given;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks
    ArtifactService artifactService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //Given. Arrange inputs and targets. Define the behavior of mock object artifactRepository
        /*
        "id": "1250808601744904192",
        "name": "Invisibility Cloak",
        "description": "An invisibility cloak is used to make the wearer invisible.",
        "imageUrl": "ImageUrl",
         */
        artifact a = new artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImageUrl("ImageUrl");

        wizard w = new wizard();
        w.setId(2);
        w.setName("Harry Potter");


        a.setOwner(w);

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a));

        //When. Act on the target behavior. When steps should cover the method to be tested.

        artifact returnedArtifact = artifactService.findById("1250808601744904192");

        //Then. Assert expected outcomes.
        assertThat(returnedArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnedArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(a.getImageUrl());
        vertify(artifactRepository, times(wantedNumberOfInvocations: 1)).findById("1250808601744904192");


    }

    @Test
    void testFindByIdNotFound() {
        //Given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty);

        //When
        Throwable thrown = catchThrowable(() -> {
            artifact returnedArtifact = artifactService.findById(artifactId: "1250808601744904192");
        });

        //Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could nto find arrifact with Id 1250808601744904192 :(");
        vertify(artifactRepository, times(wantedNumberOfInvocations: 1)).findById("1250808601744904192");
    }

}