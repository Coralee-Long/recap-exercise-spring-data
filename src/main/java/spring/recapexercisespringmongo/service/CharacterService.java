package spring.recapexercisespringmongo.service;

import org.springframework.stereotype.Service;
import spring.recapexercisespringmongo.model.AsterixCharacter;
import spring.recapexercisespringmongo.repo.CharacterRepo;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepo characterRepo;

    // Constructor injection for the repository
    public CharacterService(CharacterRepo characterRepo) {
        this.characterRepo = characterRepo;
    }

    // Fetch character by id from MongoDB
    public AsterixCharacter getCharacterById(String id) {
        return characterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));
    }

    // Fetch all characters from MongoDB
    public List<AsterixCharacter> getAllCharacters() {
        return characterRepo.findAll();
    }

    // Fetch characters by name
    public List<AsterixCharacter> getCharactersByName(String name) {
        return characterRepo.findAsterixCharacterByName(name);
    }

    // Fetch characters by profession
    public List<AsterixCharacter> getCharactersByProfession(String profession) {
        return characterRepo.findAsterixCharacterByProfession(profession);
    }

    public AsterixCharacter addCharacter(AsterixCharacter asterixCharacter) {
        return characterRepo.save(asterixCharacter);
    }

    public AsterixCharacter updateCharacter(AsterixCharacter asterixCharacter) {
        return characterRepo.save(asterixCharacter);
    }

    public void deleteCharacter(String id) {
        if (!characterRepo.existsById(id)) {
            throw new RuntimeException("Character not found with id: " + id);
        }
        characterRepo.deleteById(id);
    }



}
