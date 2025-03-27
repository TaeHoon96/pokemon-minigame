package org.example.pokemonminigame.model.dao;

import org.example.pokemonminigame.util.DotenvMixin;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

abstract public class MySQLRepository implements JDBCRepository, DotenvMixin
{
    final Logger logger = Logger.getLogger(MySQLRepository.class.getName());
    protected final Connection connection;

    public MySQLRepository() throws Exception {
        logger.info("생성 시도");
        prepareClass("com.mysql.cj.jdbc.Driver");
        String url = dotenv.get("MYSQL_URL");
        Properties properties = new Properties();
        properties.put("user", dotenv.get("MYSQL_USER"));
        properties.put("password", dotenv.get("MYSQL_PASSWORD"));
        properties.put("database", dotenv.get("MYSQL_DATABASE"));
        properties.put("host", dotenv.get("MYSQL_HOST"));
        properties.put("port", dotenv.get("MYSQL_PORT"));
        properties.put("databaseName", dotenv.get("MYSQL_DATABASE"));
        connection = getConnection(url, properties);
        testConnection();
        logger.info("정상적으로 생성");
    }

    public void testConnection() throws Exception {
        Statement statement = connection.createStatement();
        statement.execute("SELECT 1 + 1;");
    }
}
