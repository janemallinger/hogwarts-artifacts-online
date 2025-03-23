package artifact;

import artifact.utils.IdWorker;
import org.junit.Test;
import wizard.wizard;


import static javax.management.Query.times;
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import BDDAssumptions.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    ArtifactService artifactService;

    List<artifact> arifacts;
    private ArrayList<Object> artifacts;

    @BeforeEach
    void setUp() {
        artifact a1 = new artifact();
        a.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("It's a Deluminator");
        a1.setImageUrl("ImageUrl");
        this.artifacts.add(a1);

        artifact a2 = new artifact();
        a2.setId("1250808601744904192");
        a2.setName("Cloak");
        a2.setDescription("It's a Cloak");
        a2.setImageUrl("ImageUrl");
        this.artifacts.add(a2);

       this.artifacts = new ArrayList<>();
       this.artifacts.add(a1);
        this.artifacts.add(a2);
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

    @Test
    void testFindAllSuccess() {
        //Given
        given(artifactRepository.findAll()).willRetunr(this.artifacts);

        //When
        List<artifact> actualArtifacts = artifactService.findAll();

        //Then
        assertThat(actualArtifacts.size()).isEqualTo(this.artifacts.size());

        verify(artifactRepository, times(1)).findAll();
    }

    @Test
    void testSaveSuccess() {

        //Given
        artifact newArtifact = new artifact();
        newArtifact.setName("Artifact 3");
        newArtifact.setDescription("Description...");
        newArtifact.setImageUrl("ImageUrl...");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save()).willReturn(newArtifact);

        //When
        artifact savedArtifact = artifactService.save(newArtifact);

        //Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());

        verify(artifactRepository, times(1)).save(newArtifact);
    }

}