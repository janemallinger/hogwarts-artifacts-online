package artifact;

import artifact.dto.ArtifactDto;
import org.junit.Test;
import system.StatusCode;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

import static java.nio.file.Files.delete;
import static javax.management.Query.eq;
import static javax.swing.UIManager.put;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import system.exception.ObjectNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ArtifactService artifactService;


    List<artifact> artifacts;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @BeforeEach
    void setUp() {
        this.artifacts = new ArrayList<>();

        artifact a1 = new artifact();
        a1.setId("1250808601744904191");
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
        this.mockMvc.perform(MockMvcResquestBuilders.get(this.baseUrl + "/artifacts/1250808601744904191").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find one success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data.name").value("Deluminator"));
    }

    @Test
    void testFindArtifactByIdNotFound() {
        //Given
        given(this.artifactService.findById("1250808601744904191")).willThrow(new ObjectNotFoundException("1250808601744904191"));

        //When and Then
        this.mockMvc.perform(MockMvcResquestBuilders.get(this.baseUrl + "/artifacts/1250808601744904191").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find artifact"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFineAllArtifactsSuccess() {
        //Given
        given(this.artifactService.findAll()).willReturn(this.artifacts);


        //When and Then
        this.mockMvc.perform(get(this.baseUrl + "/artifacts").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find all success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.artifacts.size())))
                .andExpect(jsonPath("$.data[0].id").value("1250808601744904191"))
                .andExpect(jsonPath("$.data[0].name").value("Deluminator"))
                .andExpect(jsonPath("$.data[1].id").value("1250808601744904192"))
                .andExpect(jsonPath("$.data[1].name").value("Cloak"));
    }

    @Test
    void testAddArtifactSuccess() {
        //Given
        ArtifactDto artifactDto = new ArtifactDto(null,
                "Remembrall",
                "glows when forgotten",
                "ImageUrl",
                null);
        String json = this.objectMapper.writeValueAsString(artifactDto);

        artifact savedArtifact = new artifact();
        savedArtifact.setId("1250808601744904191");
        savedArtifact.setName("Remembrall");
        savedArtifact.setDescription("glows when forgotten");
        savedArtifact.setImageUrl("ImageUrl");

        given(this.artifactService.save(Mockito.any(artifact.class))).willReturn(savedArtifact);



        //When and Then
        this.mockMvc.perform(post(this.baseUrl + "/artifacts").contentType(PageAttributes.MediaType.APPLICATION_JSON).content(json).accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(savedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedArtifact.getImageUrl()));

    }

    @Test
    void testUpdateArtifactSuccess() throws Exception {
        ArtifactDto artifactDto = new ArtifactDto("1250808601744904191",
                "cloak",
                "go invisible",
                "ImageUrl",
                null);
        String json = this.objectMapper.writeValueAsString(artifactDto);

        artifact updatedArtifact = new artifact();
        updatedArtifact.setId("1250808601744904192");
        updatedArtifact.setName("cloak");
        updatedArtifact.setDescription("go invisible");
        updatedArtifact.setImageUrl("ImageUrl");

        given(this.artifactService.update(eq("1250808601744904192"), Mockito.any(artifact.class))).willReturn(savedArtifact);



        //When and Then
        this.mockMvc.perform(put(this.baseUrl + "/artifacts/1250808601744904192").contentType(PageAttributes.MediaType.APPLICATION_JSON).content(json).accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("update success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904192"))
                .andExpect(jsonPath("$.data.name").value(updatedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(updatedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(updatedArtifact.getImageUrl()));

    }

    @Test
    void testUpdateArtifactErrorWithNonExistentId() throws Exception {
        ArtifactDto artifactDto = new ArtifactDto("1250808601744904191",
                "cloak",
                "go invisible",
                "ImageUrl",
                null);
        String json = this.objectMapper.writeValueAsString(artifactDto);


        given(this.artifactService.update(eq("1250808601744904192"), Mockito.any(artifact.class))).willThrow(new ObjectNotFoundException());



        //When and Then
        this.mockMvc.perform(put(this.baseUrl + "/artifacts/1250808601744904192").contentType(PageAttributes.MediaType.APPLICATION_JSON).content(json).accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find artifact with Id 1250808601744904192"))
                .andExpect(jsonPath("$.data").isEmpty());


    }

    @Test
    void testDeleteArtifactSuccess() throws Exception {
        //Given
        doNothing().when(this.artifactService.delete("1250808601744904192"));

        //When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/artifacts/1250808601744904192").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("delete success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactErrorWithNonExistentId() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("1250808601744904191")).when(this.artifactService.delete("1250808601744904192"));

        //When and Then
        this.mockMvc.perform(delete(this.baseUrl + "/artifacts/1250808601744904192").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("could not find artifact with Id 1250808601744904191"))
                .andExpect(jsonPath("$.data").isEmpty());
    }



}
