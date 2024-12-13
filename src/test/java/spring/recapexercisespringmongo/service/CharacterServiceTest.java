package spring.recapexercisespringmongo.service;

import org.junit.jupiter.api.Test;
import spring.recapexercisespringmongo.model.AsterixCharacter;
import spring.recapexercisespringmongo.repo.CharacterRepo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CharacterServiceTest {

    // Make mocks of external dependencies
    // Mock the CharacterRepo
    private final CharacterRepo characterRepo = mock(CharacterRepo.class);
    // Mock the IdService
    private final IdService idService = mock(IdService.class);

    @Test
    void getAllCharacters_shouldReturnAllCharacters() {
        // GIVEN
        List<AsterixCharacter> characters = List.of(
                new AsterixCharacter("1", "Asterix", 35, "Krieger" ),
                new AsterixCharacter("2", "Obelix", 35, "Lieferant" )
        );
        when(characterRepo.findAll()).thenReturn(characters); // Mocked repo

        // Make a service
        CharacterService characterService = new CharacterService(characterRepo,idService);

        // WHEN
        List<AsterixCharacter> result = characterService.getAllCharacters();

        // THEN
        assertEquals(characters, result);
        verify(characterRepo, times(1)).findAll(); // Ensure findAll() was called once
        verifyNoMoreInteractions(characterRepo); // Ensure no other methods were called on the repo
    }

    @Test
    void getCharacterById_shouldReturnCharacter_ifValidId() {
        // GIVEN
        AsterixCharacter asterixCharacter = new AsterixCharacter("1", "Asterix", 35, "Krieger");
        AsterixCharacter expected = new AsterixCharacter(
                asterixCharacter.id(),
                asterixCharacter.name(),
                asterixCharacter.age(),
                asterixCharacter.profession());

        // Make a service
        CharacterService characterService = new CharacterService(characterRepo, idService);
        when(characterRepo.findById(asterixCharacter.id())).thenReturn(Optional.of(expected));

        // WHEN
        AsterixCharacter result = characterService.getCharacterById(asterixCharacter.id());

        // THEN
        assertEquals(expected, result);
        verify(characterRepo, times(1)).findById(asterixCharacter.id());
        verifyNoMoreInteractions(characterRepo); // Ensure no other methods were called on the repo

    }

    @Test
    void addCharacter_shouldReturnAddedCharacter() {
        // GIVEN
        // Make a service
        CharacterService characterService = new CharacterService(characterRepo,idService);
        // Make character
        AsterixCharacter asterixCharacter = new AsterixCharacter("1", "Asterix", 35, "Krieger");
        // Make expected Character
        AsterixCharacter expected = new AsterixCharacter(
                "1",
                asterixCharacter.name(),
                asterixCharacter.age(),
                asterixCharacter.profession());
        // mock the idService behaviour
        when(idService.generateId()).thenReturn("1");
        when(characterRepo.save(asterixCharacter)).thenReturn(expected);

        // WHEN
        AsterixCharacter actual = characterService.addCharacter(asterixCharacter);

        // THEN
        assertEquals(expected, actual);
        verify(characterRepo, times(1)).save(asterixCharacter);
        verifyNoMoreInteractions(characterRepo); // Ensure no other methods were called on the repo
    }


    @Test
    void updateCharacter_shouldReturnUpdatedCharacter_ifValidId() {
        // GIVEN
        AsterixCharacter asterixCharacter = new AsterixCharacter("1", "Asterix", 35, "Krieger");
        AsterixCharacter expected = new AsterixCharacter(
                asterixCharacter.id(),
                asterixCharacter.name(),
                asterixCharacter.age(),
                asterixCharacter.profession());

        // Make a service
        CharacterService characterService = new CharacterService(characterRepo,idService);
        when(characterRepo.existsById(asterixCharacter.id())).thenReturn(true);
        when(characterRepo.findById(asterixCharacter.id())).thenReturn(Optional.of(expected));

        // WHEN
        AsterixCharacter result = characterService.updateCharacter(asterixCharacter.id(), asterixCharacter);

        // THEN
        assertEquals(expected, result);
        verify(characterRepo, times(1)).existsById(asterixCharacter.id()); // Check existsById is called once
        verify(characterRepo, times(1)).save(asterixCharacter); // Check save is called once
        verify(characterRepo, times(1)).findById(asterixCharacter.id()); // Check findById is called once

        // Ensure no other methods were called
        verifyNoMoreInteractions(characterRepo, idService);
    }

    @Test
    void deleteCharacter_shouldDeleteCharacter_ifValidId() {
        // GIVEN
        AsterixCharacter asterixCharacter = new AsterixCharacter("1", "Asterix", 35, "Krieger");
        String characterId = "1";
        AsterixCharacter expected = new AsterixCharacter(
                characterId,
                asterixCharacter.name(),
                asterixCharacter.age(),
                asterixCharacter.profession());

        // Mock repository behavior
        when(characterRepo.existsById(characterId)).thenReturn(true); // Character exists before deletion
        doNothing().when(characterRepo).deleteById(characterId); // Mock deleteById behavior

        // Make a service
        CharacterService characterService = new CharacterService(characterRepo,idService);

        // WHEN
        characterService.deleteCharacter(asterixCharacter.id());

        // THEN
        verify(characterRepo, times(1)).existsById(characterId); // Check existence was called
        verify(characterRepo, times(1)).deleteById(characterId); // Check deleteById was called
        verifyNoMoreInteractions(characterRepo); // Ensure no other methods were called on the repo
    }
}
