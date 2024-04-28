package user.management.system.grpc.controller;

import java.sql.SQLException;

import com.cwrr.user_management.CreateUserRequest;

import user.management.system.grpc.db.UserRepository;
import user.management.system.grpc.exception.Exception4XX;
import user.management.system.grpc.exception.Exception5XX;
import user.management.system.grpc.model.User;

public class UserCreateController {

    UserRepository userRepository;

    public UserCreateController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int createUser(CreateUserRequest createUserRequest) throws Exception4XX, Exception5XX, SQLException {
            if(createUserRequest.getFirstName().isEmpty()) {
                throw new Exception4XX("300", "first_name", "First Name can not be empty");
            }
            if(createUserRequest.getEmail().isEmpty()) {
                throw new Exception4XX("300", "email", "Email can not be empty");
            }
            if(createUserRequest.getPassword().isEmpty()) {
                throw new Exception4XX("300", "password", "Password can not be empty");
            }

            User user = new User();
            user.setFirstName(createUserRequest.getFirstName());
            user.setLastName(createUserRequest.getLastName());
            user.setEmail(createUserRequest.getEmail());
            user.setPassword(createUserRequest.getPassword());
            
          
            if(userRepository.findUserByEmail(createUserRequest.getEmail()) != null) {
                throw new Exception4XX("301", "email", "user already exists with this email");
            }
            userRepository.CreateUser(user);
            User createdUser = userRepository.findUserByEmail(user.getEmail());
            return createdUser.getId();
        
    }
}
