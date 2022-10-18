package com.careerclub.careerclub.Auth;

import com.careerclub.careerclub.Config.WebSecurityConfig;
import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Repositories.CodeRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.careerclub.careerclub.Utils.JwtGenerateToken.generateAccessToken;
import static com.careerclub.careerclub.Utils.OtpCodeGenerator.generate;

@RestController
@RequestMapping("/auth")
@Transactional
public class AuthController {

    private final UserRepository userRepository;
    private final CodeRepository codeRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.refresh.secret}")
    private String jwtRefreshSecret;

    public AuthController(UserRepository userRepository, CodeRepository codeRepository) {
        this.userRepository = userRepository;
        this.codeRepository = codeRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        var user = userRepository.findByUsername(loginRequest.getUsername());
        if(user!=null){
            var rolesClaim=  new HashMap<String,Object>();
            if(WebSecurityConfig.passwordEncoder().matches(loginRequest.getPassword(),user.getPassword())){
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
                rolesClaim.put("roles","otp");

                //Token generate
                var token = generateAccessToken(jwtSecret,user.getUsername(), rolesClaim, Date.from(Instant.now().plusSeconds(300)));
                return ResponseEntity.ok(Map.of("accessToken",token));
            }
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest refreshRequest) {
        var username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByUsername(username.toString());
        try {
            var jwt = Jwts.parserBuilder()
                    .setSigningKey(jwtRefreshSecret.getBytes()).build().parseClaimsJws(refreshRequest.getToken());

            //Retrieving and adding roles to claims
            var rolesClaim = new HashMap<String, Object>();
            var roles = user.getRoles().stream().map(Roles::getName).collect(Collectors.joining(";"));
            rolesClaim.put("roles", roles);


            var accessToken = generateAccessToken(jwtSecret,jwt.getBody().getSubject(), rolesClaim, java.util.Date.from(Instant.now().plusSeconds(360)));
            String refreshToken = generateAccessToken(jwtRefreshSecret, jwt.getBody().getSubject(), rolesClaim, java.util.Date.from(Instant.now().plusSeconds(86_400)));
            return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }


}
