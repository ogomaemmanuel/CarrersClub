package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.Auth.LoginRequest;
import com.careerclub.careerclub.Config.WebSecurityConfig;
import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Repositories.CodeRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.careerclub.careerclub.Utils.EmailValidator.validate;
import static com.careerclub.careerclub.Utils.JwtGenerateToken.generateAccessToken;
import static com.careerclub.careerclub.Utils.OtpCodeGenerator.generate;

@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;

    public AuthService(UserRepository userRepository, CodeRepository codeRepository) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }
    public String login(LoginRequest loginRequest){
        //Email Validation
        var emailIsValidate = validate(loginRequest.getEmail());
        if(emailIsValidate) {
            var user = userRepository.findByEmail(loginRequest.getEmail());
            if (user != null) {
                var rolesClaim = new HashMap<String, Object>();
                if (WebSecurityConfig.passwordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
                    //Deletes All Existing codes
                    codeRepository.deleteAllByUserId(user.getId());

                    //Generate Code
                    var code = generate();

                    //Save Code
                    var newCode = new Code();
                    newCode.setCode(code);
                    newCode.setUser(user);
                    codeRepository.save(newCode);

                    //Assign Otp role
                    rolesClaim.put("roles", "otp");

                    //Token generate
                    var token = generateAccessToken(jwtSecret, user.getUsername(), rolesClaim, Date.from(Instant.now().plusSeconds(300)));
                    return token;
                } else {
                    throw new RecordNotFoundException("Password is invalid.");
                }
            } else {
                throw new RecordNotFoundException("User doesn't exist.");
            }
        }else{
            throw new RecordNotFoundException("Email format provided is invalid.");
        }
    }
}
