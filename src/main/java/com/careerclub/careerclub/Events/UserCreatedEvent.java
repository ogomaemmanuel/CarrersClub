package com.careerclub.careerclub.Events;

import com.careerclub.careerclub.Entities.User;

public class UserCreatedEvent {
    private User user;

    public UserCreatedEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
