package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.CodeVerificationRequest;
import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Repositories.CodeRepository;
import com.careerclub.careerclub.Repositories.RolesRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerclub.careerclub.Utils.JwtGenerateToken.generateAccessToken;

@Service
public class CodeService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.refresh.secret}")
    private String jwtRefreshSecret;


    private final CodeRepository codeRepository;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;

    public CodeService(CodeRepository codeRepository, RolesRepository rolesRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
    }

    public List<Code> getAllCodes(){
        return codeRepository.findAll();
    }

    public HashMap<Object, Object> verifyCode(CodeVerificationRequest codeVerificationRequest){
        var username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByUsername(username.toString());
        var usernameCode = codeRepository.findByUser(user);
        if(usernameCode.getCode().equals(codeVerificationRequest.getCode())){
            var validate = new HashMap<>();

            //Add member role to user
            var memberRole = rolesRepository.findByName("member");
            user.addRole(memberRole);
            userRepository.save(user);

            //Retrieving and adding roles to claims
            var rolesClaim = new HashMap<String, Object>();
            var roles = user.getRoles().stream().map(Roles::getName).collect(Collectors.joining(";"));
            rolesClaim.put("roles", roles);

            var accessToken = generateAccessToken(jwtSecret, user.getUsername(), rolesClaim, java.util.Date.from(Instant.now().plusSeconds(3600)));
            var refreshToken = generateAccessToken(jwtRefreshSecret, user.getUsername(), rolesClaim, Date.from(Instant.now().plusSeconds(86_400)));

            validate.put("accessToken", accessToken);
            validate.put("refreshToken", refreshToken);
            return validate;
        }else{
            throw new RecordNotFoundException("Code is invalid.");
        }
    }

}
