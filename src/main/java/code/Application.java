package code;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {

        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var sql2 = "INSERT INTO users (username, phone) VALUES ('Tom', '123456789')";
            try (var statement2 = conn.createStatement()) {
                statement2.executeUpdate(sql2);
            }

            var sql3 = "INSERT INTO users (username, phone) VALUES ('Rob', '987654321')";
            try (var statement3 = conn.createStatement()) {
                statement3.executeUpdate(sql3);
            }

            var sql4 = "SELECT * FROM users";
            try (var statement4 = conn.createStatement()) {
                var resultSet = statement4.executeQuery(sql4);
                while (resultSet.next()) {
                    System.out.printf("%s %s\n",
                            resultSet.getString("username"),
                            resultSet.getString("phone"));
                }
            }
        }
    }
}