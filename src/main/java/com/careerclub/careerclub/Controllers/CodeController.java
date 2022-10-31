package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.CodeVerificationRequest;
import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Service.CodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Code controller")
@RestController
@RequestMapping("/code")
public class CodeController {
    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping
    public ResponseEntity<List<Code>> getAllCodes(){
        var codes = codeService.getAllCodes();
        return ResponseEntity.ok(codes);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@Valid @RequestBody CodeVerificationRequest codeVerificationRequest){
        var validate = codeService.verifyCode(codeVerificationRequest);
        return ResponseEntity.ok(validate);
    }
}
