package hr.algebra.shopapp.listeners.events;

import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

    public UserRegistrationEvent(Object source) {
        super(source);
    }
}
