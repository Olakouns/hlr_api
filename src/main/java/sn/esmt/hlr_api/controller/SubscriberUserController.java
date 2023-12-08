package sn.esmt.hlr_api.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.esmt.hlr_api.dto.ServiceRequest;
import sn.esmt.hlr_api.entites.SubscriberUser;
import sn.esmt.hlr_api.service.SubscriberUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hlr-api")
public class SubscriberUserController {

    private final SubscriberUserService subscriberUserService;


    @PostMapping("/activated")
    public ResponseEntity<?> activateSubscriber(@RequestBody SubscriberUser subscriberUser) {
        return subscriberUserService.activateSubscriber(subscriberUser);
    }

    @DeleteMapping("/un-activated/{number}")
    public ResponseEntity<?> deactivateSubscriber(@PathVariable String number) {
        return subscriberUserService.deactivateSubscriber(number);
    }

    @GetMapping("/display/{number}")
    public ResponseEntity<?> displaySubscriber(@PathVariable String number) {
        return subscriberUserService.displaySubscriber(number);
    }

    @GetMapping("/display-info/{number}")
    public ResponseEntity<?> getUserServices(@PathVariable String number) {
        return subscriberUserService.getUserServices(number);
    }

    @PutMapping("/modify/{number}")
    public ResponseEntity<?> modifyServiceSubscriber(@PathVariable String number, @RequestBody ServiceRequest serviceRequest) {
        return subscriberUserService.modifyServiceSubscriber(number, serviceRequest);
    }
}
