package code;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO {

    private Connection connection;

    public UserDAO(Connection conn) {
        connection = conn;
    }

    public void save(User user) throws SQLException {
        if (user.getId() == null) {
            var sql = "INSERT INTO users (username, phone) VALUES (?, ?)";

            try (var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPhone());
                preparedStatement.executeUpdate();

                var generateKeys = preparedStatement.getGeneratedKeys();
                if (generateKeys.next()) {
                    user.setId(generateKeys.getLong(1));
                } else {
                    throw new SQLException("DB have not returned an id after saving an entity");
                }
            }
        } else {
            var sql = "UPDATE users SET name = ? WHERE id = ?;\n"
                    + "UPDATE users SET phone = ? WHERE id = ?;\n";

            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setLong(2, user.getId());
                preparedStatement.setString(3, user.getPhone());
                preparedStatement.setLong(4, user.getId());

                preparedStatement.executeUpdate();
            }
        }
    }

    public Optional<User> find(Long id)  throws SQLException {
        var sql = "SELECT * FROM users WHERE id = ?";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var name = resultSet.getString("username");
                var phone = resultSet.getString("phone");
                var user =  new User(name, phone);
                user.setId(id);
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    public void dataOutput() throws SQLException {
        var sql = "SELECT * FROM users";

        try (var statement3 = connection.createStatement()) {
            var resultSet = statement3.executeQuery(sql);
            System.out.println("Data:");

            while (resultSet.next()) {
                System.out.printf("%s %s\n",
                        resultSet.getString("username"),
                        resultSet.getString("phone"));
            }
        }
    }

    public void delete(Long id) throws SQLException {
        var sql = "DELETE FROM users WHERE id = ?;\n";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
