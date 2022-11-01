package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.DuplicateException;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.IndustryUpdateRequest;
import com.careerclub.careerclub.DTOs.IndustryCreationRequest;
import com.careerclub.careerclub.Entities.Industry;
import com.careerclub.careerclub.Repositories.IndustryRepository;
import com.careerclub.careerclub.Utils.IndustryValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class IndustryService {
    private final IndustryRepository industryRepository;

    public IndustryService(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;

    }

    public List<Industry> getAllIndustries(){
        var industries = industryRepository.findAll();
        return industries;
    }

    public Optional<Industry> getIndustryById(Long id){
        var industry = industryRepository.findById(id);
        if(industry.isEmpty()){
            throw new RecordNotFoundException("Industry with the given id doesn't exist.");
        }
        return industry;
    }

    public Industry createIndustry(IndustryCreationRequest industryCreationRequest){
        var industry = new Industry();
        var industryName = industryRepository.findByName(industryCreationRequest.getName());
        if(industryName.isPresent()){
            throw new DuplicateException("Industry with the given name already exists.");
        }

        industry.setName(industryCreationRequest.getName());


        return industryRepository.save(industry);
    }

    public Optional<Industry> updateIndustry(Long id, IndustryCreationRequest industryCreationRequest){
        var industry = industryRepository.findById(id);

        industry.ifPresentOrElse(i->{
            i.setName(industryCreationRequest.getName());
            industryRepository.save(i);
        },()->{
            throw new RecordNotFoundException("Industry with the given id doesn't exist.");
        });
        return industry;
    }

    public HashMap<Object,Object> deleteIndustry(Long industryId){
        var validate = new HashMap<>();
        var industry = industryRepository.findById(industryId);
        industry.ifPresentOrElse(i->{
            industryRepository.delete(i);
            validate.put("message","Industry deleted successfully ✅");
        },()->{
            throw new RecordNotFoundException("Industry with the given id doesn't exist.");
        });
        return validate;
    }

}
