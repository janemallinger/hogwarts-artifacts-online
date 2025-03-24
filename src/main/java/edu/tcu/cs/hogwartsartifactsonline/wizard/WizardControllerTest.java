package wizard;

import artifact.artifact;
import org.junit.Test;
import system.StatusCode;
import wizard.dto.WizardDto;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WizardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowires
    ObjectMapper objectMapper;

    @MockBean
    WizardService wizardService;

    List<wizard> wizards;

    @BeforeEach
    void setUp() throws Exception {
        artifact a1 = new artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("It's a Deluminator");
        a1.setImageUrl("ImageUrl");


        artifact a2 = new artifact();
        a2.setId("1250808601744904192");
        a2.setName("Cloak");
        a2.setDescription("It's a Cloak");
        a2.setImageUrl("ImageUrl");


        artifact a3 = new artifact();
        a3.setId("1250808601744904193");
        a3.setName("Wand");
        a3.setDescription("It's a wand");
        a3.setImageUrl("ImageUrl");


        artifact a4 = new artifact();
        a4.setId("1250808601744904194");
        a4.setName("Map");
        a4.setDescription("It's a Map");
        a4.setImageUrl("ImageUrl");


        artifact a5 = new artifact();
        a5.setId("1250808601744904195");
        a5.setName("Sword");
        a5.setDescription("It's a Sword");
        a5.setImageUrl("ImageUrl");


        artifact a6 = new artifact();
        a6.setId("1250808601744904196");
        a6.setName("Stone");
        a6.setDescription("It's a Stone");
        a6.setImageUrl("ImageUrl");

        this.wizards = new ArrayList<>();

        wizard w1 = new wizard();
        w1.setId(1);
        w1.setName("Albus");
        w1.addArtifact(a1);
        w1.addArtifact(a3);
        this.wizards.add(w1);

        wizard w2 = new wizard();
        w2.setId(2);
        w2.setName("Harry");
        w2.addArtifact(a2);
        w2.addArtifact(a4);
        this.wizards.add(w2);

        wizard w3 = new wizard();
        w3.setId(3);
        w3.setName("Neville");
        w3.addArtifact(a5);
        this.wizards.add(w3);

    }

    @Test
    void testFindAllWizardsSuccess() throws Exception {
        //Given
        given(this.wizardService.findAll()).willReturn(this.wizards);

        //When and Then
        this.mockMvc.perform(get("/api/v1/wizards").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .addExpect(jsonPath("$.flag").value(true))
                .addExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .addExpect(jsonPath("$.message").value("find all success"))
                .addExpect(jsonPath("$.data", Matchers.hasSize(this.wizards.size())))
                .addExpect(jsonPath("$.data[0].id").value(1))
                .addExpect(jsonPath("$.data[0].name").value("Albus"))
                .addExpect(jsonPath("$.data[1].id").value(2))
                .addExpect(jsonPath("$.data[1].name").value("Harry"));

    }

    @Test
    void testFindWizardByIdSuccess()  throws Exception {
        //Given
        given(this.wizardService.findById(1)).willReturn(this.wizards.get(0));

        //When and Then
        this.mockMvc.perform(get("/api/v1/wizards/1").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .addExpect(jsonPath("$.flag").value(true))
                .addExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .addExpect(jsonPath("$.message").value("find one success"))
                .addExpect(jsonPath("$.data.id").value(1))
                .addExpect(jsonPath("$.data.name").value("Albus"));
    }

    @Test
    void testFindWizardByIdNotFound()  throws Exception {
        //Given
        given(this.wizardService.findById(5)).willThrow(new WizardNotFoundException(1));

        //When and Then
        this.mockMvc.perform(get("/api/v1/wizards/5").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .addExpect(jsonPath("$.flag").value(false))
                .addExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .addExpect(jsonPath("$.message").value("couldn't find"))
                .addExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto(null, "Hermione", null);

        String json = this.objectMapper.writeValueAsString(wizardDto);

        wizard savedWizard = new wizard();
        savedWizard.setId(4);
        savedWizard.setName("Hermione");

        //Given
        given(this.wizardService.save(Mockito.any(wizard.class))).willReturn(savedWizard);

        //When and Then
        this.mockMvc.perform(get("/api/v1/wizards/5").accept(PageAttributes.MediaType.APPLICATION_JSON))
                .addExpect(jsonPath("$.flag").value(true))
                .addExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .addExpect(jsonPath("$.message").value("Added wizard"))
                .addExpect(jsonPath("$.data.id").isNotEmpty())
                .addExpect(jsonPath("$.data.name").value(savedWizard.getName()));
    }
    @Test
    void testUpdateWizardSuccess() throws Exception {
        WizardDto wizardDto = new WizardDto(null, "updated wizard name", null);


    }

}
