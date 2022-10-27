package com.careerclub.careerclub.Events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component

public class JobEventsHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @EventListener(value = {JobCreatedEvent.class})
    @Async
    public void handleJobCreated(JobCreatedEvent event){
     logger.info(event.getJob().getDescription());
    }
}
