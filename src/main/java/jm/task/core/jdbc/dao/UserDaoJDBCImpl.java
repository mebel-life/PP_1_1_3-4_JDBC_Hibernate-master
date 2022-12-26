package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    // методф CRUD

    public void createUsersTable() {



        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE `mydbtest`.`users` (`id` INT NOT NULL AUTO_INCREMENT, " +
                    "`name` VARCHAR(45) NOT NULL,`lastname` VARCHAR(45) NOT NULL," +
                    "`age` INT NOT NULL, PRIMARY KEY (`id`)) " +
                    "ENGINE = InnoDB DEFAULT CHARACTER SET = utf8");

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }


    }

    public void dropUsersTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users(name,lastname,age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM users WHERE id = ?");

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }




    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);

                connection.commit();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println(users.toString());
        return users;
    }

    public void cleanUsersTable() {

        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM users");

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
