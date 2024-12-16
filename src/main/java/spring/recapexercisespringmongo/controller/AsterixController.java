package spring.recapexercisespringmongo.controller;

import org.springframework.web.bind.annotation.*;
import spring.recapexercisespringmongo.model.AsterixCharacter;
import spring.recapexercisespringmongo.service.CharacterService;

import java.util.List;

@RestController
@RequestMapping("/asterix")
public class AsterixController {

    private final CharacterService characterService;

    // Constructor injection for CharacterService
    public AsterixController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/character/{id}")
    public AsterixCharacter getCharacterById(@PathVariable String id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping("/characters")
    public List<AsterixCharacter> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/characters/name/{name}")
    public List<AsterixCharacter> getCharactersByName(@PathVariable String name) {
        return characterService.getCharactersByName(name);
    }

    @GetMapping("/characters/age/{age}")
    public List<AsterixCharacter> getCharactersByAge(@PathVariable int age) {
        return characterService.getCharactersByAge(age);
    }

    @GetMapping("/characters/profession/{profession}")
    public List<AsterixCharacter> getCharactersByProfession(@PathVariable String profession) {
        return characterService.getCharactersByProfession(profession);
    }

    @GetMapping("/characters/max-age/{maxAge}")
    public List<AsterixCharacter> getCharactersByMaxAge(@PathVariable int maxAge) {
        return characterService.getCharactersByMaxAge(maxAge);
    }

    @PostMapping("/characters")
    public AsterixCharacter addCharacter(@RequestBody AsterixCharacter asterixCharacter) {
        return characterService.addCharacter(asterixCharacter);
    }

    @PutMapping("/characters/{id}")
    public AsterixCharacter updateCharacter(@PathVariable String id, @RequestBody AsterixCharacter asterixCharacter) {
        return characterService.updateCharacter(id, asterixCharacter);
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
    }
}
