package sn.esmt.hlr_api.service;

import org.springframework.http.ResponseEntity;
import sn.esmt.hlr_api.dto.ServiceRequest;
import sn.esmt.hlr_api.entites.SubscriberUser;

import java.util.UUID;

public interface SubscriberUserService {

    ResponseEntity<?> activateSubscriber(SubscriberUser subscriberUser);
    ResponseEntity<?> deactivateSubscriber(String number);
    ResponseEntity<?> getUserServices(String number);
    ResponseEntity<?> displaySubscriber(String number);
    ResponseEntity<?> modifyServiceSubscriber(String number, ServiceRequest serviceRequest);
}
