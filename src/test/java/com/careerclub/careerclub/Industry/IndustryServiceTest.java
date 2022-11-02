package com.careerclub.careerclub.Industry;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.IndustryCreationRequest;
import com.careerclub.careerclub.Entities.Industry;
import com.careerclub.careerclub.Repositories.IndustryRepository;
import com.careerclub.careerclub.Service.IndustryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
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


    @Test
    @DisplayName("Testing industry retrieval by id")
    public void test_industry_id(){
        when(industryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Industry()));
        industryService.getIndustryById(1L);
        verify(industryRepository).findById(any(Long.class));
    }

    @Test
    @DisplayName("Testing industry creation")
    public void test_industry_create(){
        when(industryRepository.save(any(Industry.class))).thenReturn(new Industry());
        var industry = new IndustryCreationRequest();
        industry.setName("software");
        industryService.createIndustry(industry);
        verify(industryRepository).save(any(Industry.class));
    }

    @Test
    @DisplayName("Testing industry update")
    public void test_industry_update(){
        when(industryRepository.findById(any(Long.class))).thenReturn(Optional.of(new Industry()));
        when(industryRepository.save(any(Industry.class))).thenReturn(new Industry());
        var industry = new IndustryCreationRequest();
        industry.setName("hacking");
        industryService.updateIndustry(1L,industry);
        verify(industryRepository).save(any(Industry.class));
    }

//    @Test
//    @DisplayName("Testing industry deletion")
//    public void test_delete_industry(){
//        when(industryRepository.delete(any(Industry.class))).thenThrow(new RecordNotFoundException("Industry it doesn't exist"));
//        industryService.deleteIndustry("it");
//        verify(industryRepository).delete(any(Industry.class));
//    }
}
