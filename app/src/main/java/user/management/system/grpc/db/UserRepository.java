package user.management.system.grpc.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user.management.system.grpc.model.User;

public class UserRepository {
    Database db;

    public UserRepository(){
        db = new Database();
    }
    public void CreateUser(User user) throws SQLException{
        String sqlString = "INSERT INTO users (first_name, last_name, email, password) "+
        " VALUES ('"+ user.getFirstName() +"', '"+ user.getLastName() +"', '"+ user.getEmail() +"', '"+ user.getPassword() +"')";
        Connection connection = db.getConnection();
        connection.createStatement().execute(sqlString);
    }

    public User findUserByEmail(String email) throws SQLException {
        System.out.println(email);
        String sqlString = "SELECT * FROM users WHERE email = '" + email  +"'";
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            ResultSet executeQuery = statement.executeQuery(sqlString);

            if(executeQuery.next()) {
                User user = new User();
                user.setId(executeQuery.getInt("id"));
                user.setFirstName(executeQuery.getString("first_name"));
                user.setLastName(executeQuery.getString("last_name"));
                user.setEmail(executeQuery.getString("email"));
                user.setPassword(executeQuery.getString("password"));
                return user;
            }
            return null;
    }
}
