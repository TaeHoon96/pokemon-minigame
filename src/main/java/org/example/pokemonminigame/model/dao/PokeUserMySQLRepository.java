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
            // Statement.executeQuery() cannot issue statements that do not produce result sets.
            // 왜? : INSERT는 executeUpdate()를 써야한다
            // statement.executeQuery(
            String query = "INSERT INTO poke_user (poke_user_id, username, password) VALUES ('%s', '%s', '%s')" // 작은 따옴표 주의!!!
                    .formatted(pokeUser.id(), pokeUser.username(), pokeUser.password());
            int rowCount = statement.executeUpdate(query);
            if (rowCount == 0) { // 자동생성을 믿지 마세요...
                throw new RuntimeException("뭔가 잘못됨");
            }
            logger.info("정상 추가");
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
