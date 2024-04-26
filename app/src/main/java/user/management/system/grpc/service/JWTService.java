package user.management.system.grpc.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import user.management.system.grpc.exception.Exception5XX;



public class JWTService {
    JWTKeyGenerator jwtKeyGenerator = new JWTKeyGenerator();
    public String generateToken(String firstName, String email, String lastName, int id) throws Exception5XX {
       
        Algorithm algorithm = Algorithm.RSA256(jwtKeyGenerator.getPublicKey(), jwtKeyGenerator.getPrivateKey());
        String token = JWT.create()
                .withIssuer("user-managment-system-grpc")
                .withClaim("firstName", firstName)
                .withClaim("lastName", lastName)
                .withClaim("email", email)
                .withClaim("id", id)
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                .sign(algorithm);
               
        return token;
    }
    
}
