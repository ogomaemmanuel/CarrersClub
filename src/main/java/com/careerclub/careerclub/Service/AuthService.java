package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.BadRequestException;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.Auth.LoginRequest;
import com.careerclub.careerclub.Config.WebSecurityConfig;
import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Repositories.CodeRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import com.careerclub.careerclub.Utils.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;

import static com.careerclub.careerclub.Utils.EmailValidator.validate;
import static com.careerclub.careerclub.Utils.JwtGenerateToken.generateAccessToken;
import static com.careerclub.careerclub.Utils.OtpCodeGenerator.generate;

@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;
    private final UserRepository userRepository;
    private final CodeRepository codeRepository;

    private final EmailServiceImplement emailServiceImplement;

    public AuthService(UserRepository userRepository, CodeRepository codeRepository, EmailServiceImplement emailServiceImplement) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
        this.emailServiceImplement = emailServiceImplement;
    }
    public String login(LoginRequest loginRequest){
        //Email Validation
        var emailIsValidate = validate(loginRequest.getEmail());
        if(emailIsValidate) {
            var user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(()->new RecordNotFoundException("User with this email " + loginRequest.getEmail()+ " not found"));
            if (user != null) {
                var rolesClaim = new HashMap<String, Object>();
                if (WebSecurityConfig.passwordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
                    //Get user codes count
                    var amount = codeRepository.countByUser(user);

                    if(amount>0){
                        codeRepository.deleteAllByUserId(user.getId());
                    }


                    //Generate Code
                    var code = generate();

                    //Save Code
                    var newCode = new Code();
                    newCode.setCode(code);
                    newCode.setUser(user);
                    codeRepository.save(newCode);

                    //Code send via email
                    var emailDetails = new EmailDetails();
                    emailDetails.setMsgBody(code);
                    emailDetails.setRecipient(user.getEmail());
                    emailDetails.setSubject("Career Club Security verification code.");
                    emailServiceImplement.sendSimpleMail(emailDetails);

                    //Assign Otp role
                    rolesClaim.put("roles", "otp");

                    //Token generate
                    var token = generateAccessToken(jwtSecret, user.getUsername(), rolesClaim, Date.from(Instant.now().plusSeconds(300)));
                    return token;
                } else {
                    throw new BadRequestException("Password is invalid.");
                }
            } else {
                throw new RecordNotFoundException("User doesn't exist.");
            }
        }else{
            throw new BadRequestException("Email format provided is invalid.");
        }
    }
}
