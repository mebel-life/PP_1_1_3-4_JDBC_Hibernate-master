package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    // методф CRUD

    public void createUsersTable() {

        try(Statement statement = Util.getConnection().createStatement()) {

            statement.executeUpdate("CREATE TABLE `mydbtest`.`users` (`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`name` VARCHAR(45) NOT NULL,`lastname` VARCHAR(45) NOT NULL," +
                    "`age` INT NOT NULL, PRIMARY KEY (`id`)) " +
                    "ENGINE = InnoDB DEFAULT CHARACTER SET = utf8");



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void dropUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try(PreparedStatement preparedStatement =
                    Util.getConnection().prepareStatement("INSERT INTO users(name,lastname,age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement =
                    Util.getConnection().prepareStatement("DELETE FROM users WHERE id = ?");) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Statement statement = Util.getConnection().createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(users.toString());
        return users;
    }

    public void cleanUsersTable() {

        try(Statement statement = Util.getConnection().createStatement()) {

            statement.executeUpdate("DELETE FROM users");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
