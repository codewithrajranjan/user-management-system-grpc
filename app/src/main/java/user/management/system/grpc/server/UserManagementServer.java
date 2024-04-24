package user.management.system.grpc.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.cwrr.user_management.CreateUserResponse;
import com.cwrr.user_management.Error;
import com.cwrr.user_management.UserManagementServiceGrpc.UserManagementServiceImplBase;

import user.management.system.grpc.db.UserRepository;
import user.management.system.grpc.model.User;

public class UserManagementServer extends UserManagementServiceImplBase{

    @Override
    public void createUser(com.cwrr.user_management.CreateUserRequest createUserRequest, io.grpc.stub.StreamObserver<com.cwrr.user_management.CreateUserResponse> responseObserver) {
        
        try {
            if(createUserRequest.getFirstName().isEmpty()) {
                Error nameError = com.cwrr.user_management.Error.newBuilder()
                .setCode("400")
                .setEntity("user")
                .setMessage("First Name can not be empty")
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

            User user = new User();
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
            user.setEmail(createUserRequest.getEmail());
            user.setPassword(createUserRequest.getPassword());
            
            UserRepository userRepository = new UserRepository();

            if(userRepository.findUserByEmail(createUserRequest.getEmail()) != null) {
                Error emailError = com.cwrr.user_management.Error.newBuilder()
                .setCode("400")
                .setEntity("email")
                .setMessage("user already exists with this email")
                .build();
                CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
                .setSuccess(false)
                .setError(emailError)
                .build();
                responseObserver.onNext(createUserResponse);
                responseObserver.onCompleted();
                return;
            }
            
            
            userRepository.CreateUser(user);

            User createdUser = userRepository.findUserByEmail(user.getEmail());
            System.out.println(createdUser.toString());
            System.out.println(createdUser.getId());

            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(true)
            .setUserId(createdUser.getId())
            .build();
            
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
        } catch (SQLException e) {
            e.printStackTrace();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(Error.newBuilder().setCode("900").setMessage("Internal Server Error"))
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
        }
             
    }

}
