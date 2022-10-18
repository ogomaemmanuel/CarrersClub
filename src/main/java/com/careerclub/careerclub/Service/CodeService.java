package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Repositories.CodeRepository;
import org.springframework.stereotype.Service;

@Service
public class CodeService {
    private final CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

}
