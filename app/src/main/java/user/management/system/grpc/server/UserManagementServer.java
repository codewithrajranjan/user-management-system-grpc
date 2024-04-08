package user.management.system.grpc.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cwrr.user_management.CreateUserResponse;
import com.cwrr.user_management.Error;
import com.cwrr.user_management.UserManagementServiceGrpc.UserManagementServiceImplBase;

public class UserManagementServer extends UserManagementServiceImplBase{

    @Override
    public void createUser(com.cwrr.user_management.CreateUserRequest createUserRequest, io.grpc.stub.StreamObserver<com.cwrr.user_management.CreateUserResponse> responseObserver) {

        if(createUserRequest.getName().isEmpty()) {
            Error nameError = com.cwrr.user_management.Error.newBuilder()
            .setCode("400")
            .setEntity("user")
            .setMessage("Name can not be empty")
            .build();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(nameError)
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
            return;
        }
        if(createUserRequest.getEmail().isEmpty()) {
            Error emailError = com.cwrr.user_management.Error.newBuilder()
            .setCode("400")
            .setEntity("email")
            .setMessage("Email can not be empty")
            .build();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(emailError)
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
            return;
        }
        if(createUserRequest.getPassword().isEmpty()) {
            Error passwordError = com.cwrr.user_management.Error.newBuilder()
            .setCode("400")
            .setEntity("password")
            .setMessage("Password can not be empty")
            .build();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(passwordError)
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
            return;
        }

        


        String connectionURL = "jdbc:mysql://localhost:3306/user_management_system?useSSL=false";
        try {
            Connection connection = DriverManager.getConnection(connectionURL, "root", "password");
            connection.createStatement().execute("INSERT INTO users (name, email, password) values ('" + createUserRequest.getName() + "', '" + createUserRequest.getEmail() + "', '" + createUserRequest.getPassword() + "');");
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
                .setSuccess(true)
                .setUserId("123456")
                .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
        } catch (SQLException e) {
            e.printStackTrace();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(Error.newBuilder().setCode("500").setEntity("database").setMessage("Internal server error").build())
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
            
        }
        
    }

}
