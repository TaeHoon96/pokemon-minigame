package org.example.pokemonminigame.model.dao;

import org.example.pokemonminigame.model.dto.PokeUser;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

@Repository
public class PokeUserMySQLRepository extends MySQLRepository {
    Logger logger = Logger.getLogger(JDBCRepository.class.getName());
    public PokeUserMySQLRepository() throws Exception {
        super();
        logger.info("정상적으로 생성되었습니다");
    }

    public void createPokeUser(PokeUser pokeUser) {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("INSERT INTO poke_user (poke_user_id, username, password) VALUES (%s, %s, %s)"
                    .formatted(pokeUser.id(), pokeUser.username(), pokeUser.password()));
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
