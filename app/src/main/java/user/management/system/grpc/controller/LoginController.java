package user.management.system.grpc.controller;

import java.sql.SQLException;

import com.cwrr.user_management.LoginRequest;
import com.cwrr.user_management.LoginResponse;

import user.management.system.grpc.db.UserRepository;
import user.management.system.grpc.exception.Exception4XX;
import user.management.system.grpc.exception.Exception5XX;
import user.management.system.grpc.model.User;
import user.management.system.grpc.service.JWTService;

public class LoginController {

    UserRepository userRepository = new UserRepository();
    JWTService jwtService = new JWTService();

    public LoginResponse login(LoginRequest loginRequest) throws Exception4XX, Exception5XX, SQLException {
     
        if(loginRequest.getEmail().isEmpty()) {
            throw new Exception4XX("300", "email", "Email can not be empty");
        }
        if(loginRequest.getPassword().isEmpty()) {
            throw new Exception4XX("300", "password", "Password can not be empty");
        }

        
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
        if(user == null) {
            throw new Exception4XX("304", "email and password", "invalid email and password combination");
        }

        String token = jwtService.generateToken(user.getFirstName(), user.getEmail() , user.getLastName(), user.getId());


        return LoginResponse.newBuilder()
        .setSuccess(true)
        .setJwtToken(token)
        .build();

        

    }
    
}
