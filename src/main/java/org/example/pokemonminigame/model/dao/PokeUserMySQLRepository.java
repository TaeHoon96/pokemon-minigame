package org.example.pokemonminigame.model.dao;

import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
public class PokeUserMySQLRepository extends MySQLRepository {
    Logger logger = Logger.getLogger(JDBCRepository.class.getName());
    public PokeUserMySQLRepository() throws Exception {
        super();
        logger.info("정상적으로 생성되었습니다");
    }
}
