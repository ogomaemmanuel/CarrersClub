package com.careerclub.careerclub.Industry;

import com.careerclub.careerclub.Repositories.IndustryRepository;
import com.careerclub.careerclub.Service.IndustryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IndustryServiceTest {


    @Mock
    IndustryRepository industryRepository;


    @InjectMocks
    IndustryService industryService;



    @Test
    @DisplayName("Testing all industry retrieval")
    public void test_all_industry(){
        when(industryRepository.findAll()).thenReturn(new ArrayList<>());
        industryService.getAllIndustries();
        verify(industryRepository).findAll();
    }

}
