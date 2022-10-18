package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Repositories.CodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {
    private final CodeRepository codeRepository;

    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public List<Code> getAllCodes(){
        return codeRepository.findAll();
    }


}
