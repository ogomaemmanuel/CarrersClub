package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Service.CodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
