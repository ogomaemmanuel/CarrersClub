package com.careerclub.careerclub.ResourceAssembler;

import com.careerclub.careerclub.Controllers.JobsController;
import com.careerclub.careerclub.Controllers.MailListController;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Entities.MailList;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class JobResourceAssembler implements RepresentationModelAssembler<Job, EntityModel<Job>> {
    @Override
    public EntityModel<Job> toModel(Job entity) {
        EntityModel<Job> model = EntityModel.of(entity);
        model.add(linkTo(methodOn(JobsController.class).getJobById(entity.getId())).withSelfRel());
        return model;
    }
}
