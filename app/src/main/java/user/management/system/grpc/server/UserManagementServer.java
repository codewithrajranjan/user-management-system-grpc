package user.management.system.grpc.server;


import com.cwrr.user_management.CreateUserResponse;
import com.cwrr.user_management.Error;
import com.cwrr.user_management.LoginResponse;
import com.cwrr.user_management.UserManagementServiceGrpc.UserManagementServiceImplBase;

import user.management.system.grpc.controller.UserCreateController;
import user.management.system.grpc.db.Database;
import user.management.system.grpc.db.UserRepository;
import user.management.system.grpc.controller.LoginController;
import user.management.system.grpc.exception.Exception4XX;
import user.management.system.grpc.exception.Exception5XX;
import user.management.system.grpc.service.JWTService;

public class UserManagementServer extends UserManagementServiceImplBase{

    UserCreateController userCreateController;
    LoginController loginController;

    public UserManagementServer() {

        Database db = new Database();
        JWTService jwtService = new JWTService();
        UserRepository userRepository = new UserRepository(db);
        userCreateController = new UserCreateController(db, userRepository);
        loginController = new LoginController(userRepository, jwtService);
    }

    @Override
    public void createUser(com.cwrr.user_management.CreateUserRequest createUserRequest, io.grpc.stub.StreamObserver<com.cwrr.user_management.CreateUserResponse> responseObserver) {
        
        try {
            
            int userId = userCreateController.createUser(createUserRequest);
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(true)
            .setUserId(userId)
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
        } catch(Exception5XX e){
            e.printStackTrace();
            Error error = Error.newBuilder().setCode(e.getCode()).setEntity(e.getEntity()).setMessage(e.getMessage()).build();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(error)
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
        } catch(Exception4XX e){
            e.printStackTrace();
            Error error = Error.newBuilder().setCode(e.getCode()).setEntity(e.getEntity()).setMessage(e.getMessage()).build();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(error)
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.newBuilder().setCode("900").setEntity("internal server error").setMessage("internal server error").build();
            CreateUserResponse createUserResponse = CreateUserResponse.newBuilder()
            .setSuccess(false)
            .setError(error)
            .build();
            responseObserver.onNext(createUserResponse);
            responseObserver.onCompleted();
        }
             
    }

    @Override
    public void login(com.cwrr.user_management.LoginRequest loginRequest, io.grpc.stub.StreamObserver<com.cwrr.user_management.LoginResponse> responseObserver) {
        
        try {
            LoginResponse loginResponse = loginController.login(loginRequest);
            responseObserver.onNext(loginResponse);
            responseObserver.onCompleted();
            
        } catch(Exception4XX e){
            e.printStackTrace();
            Error error = Error.newBuilder().setCode(e.getCode()).setEntity(e.getEntity()).setMessage(e.getMessage()).build();
            LoginResponse loginResponse = LoginResponse.newBuilder()
            .setSuccess(false)
            .setError(error)
            .build();
            responseObserver.onNext(loginResponse);
            responseObserver.onCompleted();
        }catch(Exception5XX e){
            e.printStackTrace();
            Error error = Error.newBuilder().setCode(e.getCode()).setEntity(e.getEntity()).setMessage(e.getMessage()).build();
            LoginResponse loginResponse = LoginResponse.newBuilder()
            .setSuccess(false)
            .setError(error)
            .build();
            responseObserver.onNext(loginResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            Error error = Error.newBuilder().setCode("900").setEntity("internal server error").setMessage("internal server error").build();
            LoginResponse loginResponse = LoginResponse.newBuilder()
            .setSuccess(false)
            .setError(error)
            .build();
            responseObserver.onNext(loginResponse);
            responseObserver.onCompleted();
        }
    }

}
