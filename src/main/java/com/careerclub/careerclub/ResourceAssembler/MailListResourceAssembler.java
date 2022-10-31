package com.careerclub.careerclub.ResourceAssembler;

import com.careerclub.careerclub.Controllers.MailListController;
import com.careerclub.careerclub.Entities.MailList;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MailListResourceAssembler implements RepresentationModelAssembler<MailList, EntityModel<MailList>> {

    @Override
    public EntityModel<MailList> toModel(MailList entity) {
        EntityModel<MailList> model = EntityModel.of(entity);
        model.add(linkTo(methodOn(MailListController.class).unsubscribeFormList(entity.getId(), entity.getUser().getId())).withSelfRel());
        return model;
    }
}
