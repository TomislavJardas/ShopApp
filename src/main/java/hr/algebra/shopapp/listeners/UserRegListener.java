package hr.algebra.shopapp.listeners;


import hr.algebra.shopapp.dto.UserDto;
import hr.algebra.shopapp.listeners.events.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegListener implements ApplicationListener<ApplicationEvent> {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof UserRegistrationEvent registrationEvent) {
            UserDto source = (UserDto) registrationEvent.getSource();
            System.out.println("User registered: " + source.getFirstName() );
        }
    }
}
