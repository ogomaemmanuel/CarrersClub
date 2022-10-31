package com.careerclub.careerclub.Code;

import com.careerclub.careerclub.Entities.Code;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Repositories.CodeAttemptRepository;
import com.careerclub.careerclub.Repositories.CodeRepository;
import com.careerclub.careerclub.Repositories.RolesRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import com.careerclub.careerclub.Service.CodeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CodeServiceTest {

    @InjectMocks
    CodeService codeService;
    @Mock
    CodeRepository codeRepository;
    @Mock
    RolesRepository rolesRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    CodeAttemptRepository codeAttemptRepository;


    @Test
    @DisplayName("Testing all codes")
    public void testing_all_code(){
        when(codeRepository.findAll()).thenReturn(new ArrayList<Code>());
        codeService.getAllCodes();
        verify(codeRepository).findAll();
    }


}
