package org.example.pokemonminigame.model.dao;

import org.example.pokemonminigame.model.dto.PokeUser;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Repository
public class PokeImageMySQLRepository extends MySQLRepository {
    Logger logger = Logger.getLogger(JDBCRepository.class.getName());
    public PokeImageMySQLRepository() throws Exception {
        super();
        logger.info("정상적으로 생성되었습니다");
    }

    public List<String> readImages(String userId) throws Exception {
        List<String> images = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT * FROM poke_image WHERE poke_user_id = '%s' ORDER BY created_at DESC".formatted(userId);
            try (ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    images.add(rs.getString("image"));
                }
            }
            return images;
        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void createPokeImage(String image, String userId) {
        try (Statement stmt = connection.createStatement()) {
            // Statement.executeQuery() cannot issue statements that do not produce result sets.
            // 왜? : INSERT는 executeUpdate()를 써야한다
            // statement.executeQuery(
            String query = "INSERT INTO poke_image (poke_image_id, image, poke_user_id) VALUES ('%s', '%s', '%s')" // 작은 따옴표 주의!!!
                    .formatted(UUID.randomUUID(), image, userId);
            int rowCount = stmt.executeUpdate(query);
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
