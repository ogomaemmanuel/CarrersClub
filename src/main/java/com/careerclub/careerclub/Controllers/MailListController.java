package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.MailListSubscribeRequest;
import com.careerclub.careerclub.Entities.MailList;
import com.careerclub.careerclub.ResourceAssembler.MailListResourceAssembler;
import com.careerclub.careerclub.Service.MailListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Tag(name = "Subscription to mail list controller")
@RestController
@RequestMapping("/mail-list")
public class MailListController {
    private final MailListService mailListService;
    private final MailListResourceAssembler mailListResourceAssembler;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    public MailListController(MailListService mailListService, MailListResourceAssembler mailListResourceAssembler, PagedResourcesAssembler pagedResourcesAssembler) {
        this.mailListService = mailListService;
        this.mailListResourceAssembler = mailListResourceAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<List<MailList>> getAllSubscription(){
        var lists = mailListService.getAllSubscription();
        return ResponseEntity.ok(lists);
    }

    @GetMapping("/mails/{userId}")
    public ResponseEntity<?> getAllMailsOfUser(@PathVariable Long userId){
        var mails = mailListService.getAllMailsOfUser(userId);
        return ResponseEntity.ok(mails);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeToList(@Valid @RequestBody MailListSubscribeRequest mailListSubscribeRequest){
        var mailList = mailListService.subscribeToMailList(mailListSubscribeRequest);
        //Map an unsubscribe link
        var mailListResource = mailListResourceAssembler.toModel(mailList);

        return ResponseEntity.ok(mailListResource);
    }

    @PutMapping("/unsubscribe/{id}/{userId}")
    public ResponseEntity<Map<Object,Object>> unsubscribeFormList(@PathVariable Long id,@PathVariable Long userId){
        var message = mailListService.unsubscribeFromTheMailingList(id,userId);
        return ResponseEntity.ok(message);
    }

}
