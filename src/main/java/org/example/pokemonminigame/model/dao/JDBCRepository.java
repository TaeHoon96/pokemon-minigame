package org.example.pokemonminigame.model.dao;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.pokemonminigame.model.dto.PokeUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;

// DTO : Data Transfer Object
// DAO : Data Access Object
public interface JDBCRepository {

    default void prepareClass(String className) throws Exception {
        Class.forName(className);
    }

    default Connection getConnection(String url, Properties properties) throws Exception {
        // Class.forName("com.mysql.cj.jdbc.Driver");
        url = "%s%s:%s/%s"
        .formatted(url, properties.getProperty("host"), properties.getProperty("port"), properties.getProperty("database"));
        // database 인식 못해... 으엉...
        return DriverManager.getConnection(url, properties);
    }


}
