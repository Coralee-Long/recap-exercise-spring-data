package spring.recapexercisespringmongo.controller;

import spring.recapexercisespringmongo.model.AsterixCharacter;
import spring.recapexercisespringmongo.repo.CharacterRepo;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Reset DB state before each test
class AsterixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Inject repository for testing with Flapdoodle
    @Autowired
    private CharacterRepo characterRepo;



    @Test
    void getCharacterById() {
    }

    @Test
    void getAll_shouldReturnEmptyList_whenCalledInitially() throws Exception {
        // GIVEN
        // No characters are added to the database, so it is empty

        // WHEN & THEN
        mockMvc.perform(get("/asterix/characters")) // Simulate HTTP GET request
                .andExpect(status().isOk()) // Verify HTTP 200 response
                .andExpect(content().json("[]")); // Expect an empty JSON array
    }

    @Test
    void characters_shouldReturnListOfCharacters_whenRepositoryContainsData() throws Exception {
        // GIVEN
        characterRepo.save(new AsterixCharacter("1", "Asterix", 35, "Krieger"));
        characterRepo.save(new AsterixCharacter("2", "Obelix", 35, "Lieferant"));

        // WHEN & THEN
        mockMvc.perform(get("/asterix/characters")) // Perform GET request
                .andExpect(status().isOk()) // Verify HTTP status 200 OK
                .andExpect(content().json("""
                [
                    {
                        "id": "1",
                        "name": "Asterix",
                        "age": 35,
                        "profession": "Krieger"
                    },
                    {
                        "id": "2",
                        "name": "Obelix",
                        "age": 35,
                        "profession": "Lieferant"
                    }
                ]
            """)); // Verify the response matches the list of characters
    }


    @Test
    void addCharacter_shouldReturnCharacter_whenCalledWithValidId() throws Exception {
        // GIVEN
        // Prepare a Character to save to the DB
        AsterixCharacter character = new AsterixCharacter(
                "1", // ID
                "Asterix", // Name
                35, // Age
                "Krieger" // Profession
        );
        characterRepo.save(character); // Save it into the embedded database

        // WHEN & THEN
        mockMvc.perform(get("/asterix/character/1")) // Perform GET request with ID "1"
                .andExpect(status().isOk()) // Verify HTTP status 200
                // Check that the response matches the expected character
                .andExpect(content().json("""  
                    {
                    "id": "1",
                    "name": "Asterix",
                    "age": 35,
                    "profession": "Krieger"
                    }
                    """));
    }

    @Test
    void updateCharacter_shouldReturnUpdatedCharacter_whenCalledWithValidId() throws Exception {
        // GIVEN
        // Prepare a Character to save to the DB
        AsterixCharacter character = new AsterixCharacter(
                "1", // ID
                "Asterix", // Name
                35, // Age
                "Krieger" // Profession
        );
        characterRepo.save(character); // Save it into the embedded database

        // Prepare the updated Character
        String updatedCharacterInJson = """
                 {
                   "id": "1",
                   "name": "Asterix Updated",
                   "age": 36,
                   "profession": "Updated Krieger"
                 }
                """;

        // WHEN & THEN
        mockMvc.perform(put("/asterix/characters/1")  // Perform PUT request
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedCharacterInJson))
                .andExpect(status().isOk())
                // Verify the response body matches the updated character
                .andExpect(content().json("""
                  {
                     "id": "1",
                     "name": "Asterix Updated",
                     "age": 36,
                     "profession": "Updated Krieger"
                  }
                  """));

        // Verify Character was added
        Optional<AsterixCharacter> updatedCharacter = characterRepo.findById("1");
        assertTrue(updatedCharacter.isPresent());
        assertEquals("Asterix Updated", updatedCharacter.get().name());
        assertEquals(36, updatedCharacter.get().age());
        assertEquals("Updated Krieger", updatedCharacter.get().profession());


    }


    @Test
    void deleteCharacter_shouldRemoveCharacter_whenCalledWithValidId() throws Exception {
        // GIVEN
        // Prepare a Character to save to the DB
        AsterixCharacter character = new AsterixCharacter(
                "1", // ID
                "Asterix", // Name
                35, // Age
                "Krieger" // Profession
        );
        characterRepo.save(character); // Save it into the embedded database

        // Verify the Character exists in DB before deleting
        Optional<AsterixCharacter> updatedCharacter = characterRepo.findById("1");
        assertTrue(updatedCharacter.isPresent());

        // WHEN & THEN
        mockMvc.perform(delete("/asterix/characters/1")) // Perform DELETE request
                .andExpect(status().isOk()); // Verify HTTP status is 200 OK

        // Verify the character no longer exists in the database
        Optional<AsterixCharacter> deletedCharacter = characterRepo.findById("1");
        assertTrue(deletedCharacter.isEmpty());
    }
}