package spring.recapexercisespringmongo.service;

import org.springframework.stereotype.Service;
import spring.recapexercisespringmongo.model.AsterixCharacter;
import spring.recapexercisespringmongo.repo.CharacterRepo;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepo characterRepo;
    private final IdService idService; // inject IdService Dependency

    // Constructor injection for the repository
    public CharacterService(CharacterRepo characterRepo, IdService idService) {
        this.characterRepo = characterRepo;
        this.idService = idService;
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

    // Fetch all characters from MongoDB with same age
    public List<AsterixCharacter> getAllCharactersWithAge(int age) {
        return characterRepo.findAsterixCharacterByAge(age);
    }

    // Fetch characters by name
    public List<AsterixCharacter> getCharactersByName(String name) {
        return characterRepo.findAsterixCharacterByName(name);
    }

    // Fetch characters by age
    public List<AsterixCharacter> getCharactersByAge(int age) {
        return characterRepo.findAsterixCharacterByAge(age);
    }

    // Fetch characters by profession
    public List<AsterixCharacter> getCharactersByProfession(String profession) {
        return characterRepo.findAsterixCharacterByProfession(profession);
    }

    // Fetch characters by maxAge (Java Filter Method)
//    public List<AsterixCharacter> getCharactersByMaxAge(int maxAge) {
//        // Get all characters
//        List<AsterixCharacter> allCharacters = characterRepo.findAll();
//        return allCharacters.stream()
//                .filter(character -> character.age() <= maxAge)
//                .toList(); // Convert back to List
//    }

    // Fetch characters by maxAge (Mongo DB Filter Method)
    public List<AsterixCharacter> getCharactersByMaxAge(int maxAge) {
    // Let MongoDB handle the filtering
    return characterRepo.findAsterixCharacterByAgeLessThanEqual(maxAge);
    // Basically the same as the MongoDB query: { "age": { "$lte": age } }
    }

    // Add a new character with a randomly generated ID
    public AsterixCharacter addCharacter(AsterixCharacter asterixCharacter) {
        // Create a new instance with a generated ID
        AsterixCharacter characterWithId = new AsterixCharacter(
                idService.generateId(), // Generate a random ID
                asterixCharacter.name(),
                asterixCharacter.age(),
                asterixCharacter.profession()
        );
        return characterRepo.save(characterWithId);
    }

    public AsterixCharacter updateCharacter(String id, AsterixCharacter asterixCharacter) {
        if (characterRepo.existsById(id)) {
             characterRepo.save(asterixCharacter);
            return characterRepo.findById(id).orElseThrow();
        } else {
            throw new RuntimeException("Character not found with id: " + id);
        }
    }

    public void deleteCharacter(String id) {
        if (characterRepo.existsById(id)) {
            characterRepo.deleteById(id);
        } else {
            throw new RuntimeException("Character not found with id: " + id);
        }
    }

}
