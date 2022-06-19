package marco.cortes.ChallengeBackend.controller;

import marco.cortes.ChallengeBackend.entity.Personage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TestEPersonageController extends AbstractTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    private final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hdXRoL2xvZ2luIiwiZXhwIjoxNjU1Njc5MjUxfQ.WNXdJqKHnQSlqaOVFpcocE8wuCcTMhzij4uWPKOsAjQ";

    @Test
    void updateCharacter() throws Exception {
        String uri = "/characters/2/update";
        Personage personage = new Personage();
        personage.setAge(25);
        personage.setImage("Image aasdasd");
        personage.setName("Rhaast");
        personage.setHistory("History");
        personage.setWeight(88.2f);
        personage.setId(1L);
        mvc.perform(MockMvcRequestBuilders.put(uri)
                        .content(mapToJson(personage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.character.id").exists());
    }

    @Test
    void updateCharacterNotFound() throws Exception {
        String uri = "/characters/214121/update";
        Personage personage = new Personage();
        personage.setAge(25);
        personage.setImage("Image aasdasd");
        personage.setName("Rhaast");
        personage.setHistory("History");
        personage.setWeight(88.2f);
        personage.setId(214121L);
        mvc.perform(MockMvcRequestBuilders.put(uri)
                        .content(mapToJson(personage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }

    @Test
    void deleteCharacter() throws Exception {
        String uri = "/characters/1/delete";
        mvc.perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.character.id").exists());
    }

    @Test
    void deleteCharacterNotFound() throws Exception {
        String uri = "/characters/214121/delete";
        mvc.perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists());
    }
}
