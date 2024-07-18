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

            var dao = new UserDAO(conn);
            var user = new User("Tommy", "123456789");

            System.out.println("ID is " + user.getId());
            dao.save(user);
            System.out.println("ID after save is " + user.getId());

            var findUser = dao.find(user.getId()).get();
            var result = user.getId() == findUser.getId();
            System.out.println("Equal after find is " + result);

            var user2 = new User ("Rob", "987654321");
            dao.save(user2);
            dao.dataOutput();

            dao.delete(user.getId());
            user.setId(null);
            dao.dataOutput();
        }
    }
}
