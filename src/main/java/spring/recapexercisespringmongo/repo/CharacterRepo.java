package spring.recapexercisespringmongo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import spring.recapexercisespringmongo.model.AsterixCharacter;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepo extends MongoRepository<AsterixCharacter, String> {
    List<AsterixCharacter> findAsterixCharacterByName(String name);
    List<AsterixCharacter> findAsterixCharacterByProfession(String profession);




}
