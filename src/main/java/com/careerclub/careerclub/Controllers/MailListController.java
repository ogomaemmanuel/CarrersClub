package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.MailListSubscribeRequest;
import com.careerclub.careerclub.DTOs.MailListUnsubscribeRequest;
import com.careerclub.careerclub.Entities.MailList;
import com.careerclub.careerclub.Service.MailListService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Tag(name = "Subscription to mail list controller")
@RestController
@RequestMapping("/mail-list")
public class MailListController {
    private final MailListService mailListService;

    public MailListController(MailListService mailListService) {
        this.mailListService = mailListService;
    }

    @GetMapping
    public ResponseEntity<List<MailList>> getAllSubscription(){
        var lists = mailListService.getAllSubscription();
        return ResponseEntity.ok(lists);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<MailList> subscribeToList(@Valid @RequestBody MailListSubscribeRequest mailListSubscribeRequest){
        var mailList = mailListService.subscribeToMailList(mailListSubscribeRequest);
        return ResponseEntity.ok(mailList);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<Map<Object,Object>> unsubscribeFormList(@Valid @RequestBody MailListUnsubscribeRequest mailListUnsubscribeRequest){
        var message = mailListService.unsubscribeFromTheMailingList(mailListUnsubscribeRequest);
        return ResponseEntity.ok(message);
    }

}
