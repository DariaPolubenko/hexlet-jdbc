package code;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {

        var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

        var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";

        var statement = conn.createStatement();
        statement.execute(sql);
        statement.close();

        var sql2 = "INSERT INTO users (username, phone) VALUES ('Tom', '123456789')";
        var statement2 = conn.createStatement();
        statement2.executeUpdate(sql2);
        statement2.close();

        var sql3 = "INSERT INTO users (username, phone) VALUES ('Rob', '987654321')";
        var statement3 = conn.createStatement();
        statement3.executeUpdate(sql3);
        statement3.close();

        var sql4 = "SELECT * FROM users";
        var statement4 = conn.createStatement();
        var resultSet = statement4.executeQuery(sql4);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getString("phone"));
        }
        statement4.close();

        conn.close();
    }
}