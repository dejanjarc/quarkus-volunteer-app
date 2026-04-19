package si.rsj.pu.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {
    public String hello(){
        return "Hello from the service module :)";
    }
}
