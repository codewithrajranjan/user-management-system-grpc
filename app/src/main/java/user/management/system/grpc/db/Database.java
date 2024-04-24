package user.management.system.grpc.db;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Database {
    private String connectionURL = "jdbc:mysql://localhost:3306/user_management_system?useSSL=false";

    public Connection getConnection() {
      
        try {
            Connection connection = DriverManager.getConnection(connectionURL, "root", "password");
            return connection;
      
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
