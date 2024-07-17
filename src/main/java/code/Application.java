package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class Application {
    public static void main(String[] args) throws SQLException {

        try (var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test")) {

            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try (var statement = conn.createStatement()) {
                statement.execute(sql);
            }

            var sql2 = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (var preparedStatement = conn.prepareStatement(sql2)) {
                preparedStatement.setString(1, "Tommy");
                preparedStatement.setString(2, "123456789");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Rob");
                preparedStatement.setString(2, "987654321");
                preparedStatement.executeUpdate();
            }

            dataOutput(conn);

            var sql3 = "DELETE FROM users WHERE username = ?;";
            try (var preparedStatement2 = conn.prepareStatement(sql3))
            {
                preparedStatement2.setString(1, "Rob");
                preparedStatement2.executeUpdate();
            }

            dataOutput(conn);
        }
    }

    public static void dataOutput(Connection conn) throws SQLException {
        var sql = "SELECT * FROM users";

        try (var statement3 = conn.createStatement()) {
            var resultSet = statement3.executeQuery(sql);
            System.out.println("Data:");

            while (resultSet.next()) {
                System.out.printf("%s %s\n",
                        resultSet.getString("username"),
                        resultSet.getString("phone"));
            }
        }
    }
}