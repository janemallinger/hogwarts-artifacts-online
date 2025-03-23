package artifact;

import system.StatusCode;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ArtifactService artifactService;
    List<artifact> artifacts;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();

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

        artifact a3 = new artifact();
        a3.setId("1250808601744904193");
        a3.setName("Wand");
        a3.setDescription("It's a wand");
        a3.setImageUrl("ImageUrl");
        this.artifacts.add(a3);

        artifact a4 = new artifact();
        a4.setId("1250808601744904194");
        a4.setName("Map");
        a4.setDescription("It's a Map");
        a4.setImageUrl("ImageUrl");
        this.artifacts.add(a4);

        artifact a5 = new artifact();
        a5.setId("1250808601744904195");
        a5.setName("Sword");
        a5.setDescription("It's a Sword");
        a5.setImageUrl("ImageUrl");
        this.artifacts.add(a5);

        artifact a6 = new artifact();
        a6.setId("1250808601744904196");
        a6.setName("Stone");
        a6.setDescription("It's a Stone");
        a6.setImageUrl("ImageUrl");
        this.artifacts.add(a6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindArtifactByIdSuccess() {
        //Given
        given(this.artifactService.findById("1250808601744904191")).willReturn(this.artifacts.get(0));

        //When and Then
        this.mockMvc.perform(MockMvcResquestBuilders.get("/api/v1/artifacts/1250808601744904191").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find one success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));
    }

    @Test
    void testFindArtifactByIdNotFound() {
        //Given
        given(this.artifactService.findById("1250808601744904191")).willThrow(new ArtifactNotFoundException("1250808601744904191"));

        //When and Then
        this.mockMvc.perform(MockMvcResquestBuilders.get("/api/v1/artifacts/1250808601744904191").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find artifact"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}