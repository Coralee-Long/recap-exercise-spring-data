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

    @GetMapping("/characters/{id}")
    public AsterixCharacter getCharacterById(@PathVariable String id) {
        return characterService.getCharacterById(id);
    }

    @GetMapping("/characters")
    public List<AsterixCharacter> getCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/characters/name/{name}")
    public List<AsterixCharacter> getCharactersByName(@PathVariable String name) {
        return characterService.getCharactersByName(name);
    }

    @GetMapping("/characters/profession/{profession}")
    public List<AsterixCharacter> getCharactersByProfession(@PathVariable String profession) {
        return characterService.getCharactersByProfession(profession);
    }

    @PostMapping("/characters")
    public AsterixCharacter addCharacter(@RequestBody AsterixCharacter asterixCharacter) {
        return characterService.addCharacter(asterixCharacter);
    }

    @PutMapping("/characters")
    public AsterixCharacter updateCharacter(@RequestBody AsterixCharacter asterixCharacter) {
        return characterService.updateCharacter(asterixCharacter);
    }


    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable String id) {
        characterService.deleteCharacter(id);
    }







}
