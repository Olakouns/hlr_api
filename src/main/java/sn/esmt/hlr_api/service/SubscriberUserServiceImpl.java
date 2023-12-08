package sn.esmt.hlr_api.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sn.esmt.hlr_api.dto.ServiceRequest;
import sn.esmt.hlr_api.entites.ServiceType;
import sn.esmt.hlr_api.entites.SubscriberUser;
import sn.esmt.hlr_api.entites.TLService;
import sn.esmt.hlr_api.repositories.SubscriberUserRepository;
import sn.esmt.hlr_api.repositories.TLServiceRepository;

import java.util.*;


@Service
@RequiredArgsConstructor
public class SubscriberUserServiceImpl implements SubscriberUserService {

    private final SubscriberUserRepository subscriberUserRepository;
    private final TLServiceRepository tlServiceRepository;
    private final EntityManager entityManager;

    @Override
    public ResponseEntity<?> activateSubscriber(SubscriberUser subscriberUser) {
        subscriberUser.setCreatedAt(new Date());
        subscriberUser.setTlServices(new ArrayList<>());
        return ResponseEntity.ok(subscriberUserRepository.save(subscriberUser));
    }

    @Override
    public ResponseEntity<?> deactivateSubscriber(String number) {
        Optional<SubscriberUser> optionalSubscriberUser = subscriberUserRepository.findByPhoneNumberOrImsi(number, number);

        if (optionalSubscriberUser.isPresent()) {
            subscriberUserRepository.delete(optionalSubscriberUser.get());
            return ResponseEntity.ok("User with phone  = " + number + "deactivated");
        } else {
            return new ResponseEntity<>("User with phone  = " + number + "not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getUserServices(String number) {
        Optional<SubscriberUser> optionalSubscriberUser = subscriberUserRepository.findByPhoneNumberOrImsi(number, number);
        if (optionalSubscriberUser.isPresent()) {
            return ResponseEntity.ok(optionalSubscriberUser.get());
        } else {
            return new ResponseEntity<>("User with phone  = " + number + "not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> displaySubscriber(String number) {
        Optional<SubscriberUser> optionalSubscriberUser = subscriberUserRepository.findByPhoneNumberOrImsi(number, number);
        if (optionalSubscriberUser.isPresent()) {
            SubscriberUser user = optionalSubscriberUser.get();
            entityManager.detach(user);
            List<TLService> tlServiceList = new ArrayList<>();
            for (TLService tlService : optionalSubscriberUser.get().getTlServices()) {
                if (tlService.isActivated()) {
                    tlServiceList.add(tlService);
                }
            }
            user.setTlServices(tlServiceList);
            return ResponseEntity.ok(user);
        } else {
            return new ResponseEntity<>("User with phone  = " + number + "not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> modifyServiceSubscriber(String number, ServiceRequest serviceRequest) {
        Optional<SubscriberUser> optionalSubscriberUser = subscriberUserRepository.findFirstByPhoneNumberOrImsi(number, number);
        if (optionalSubscriberUser.isPresent()) {
            SubscriberUser user = optionalSubscriberUser.get();

            Optional<TLService> tlService = user.getTlServices().stream().filter(tls -> tls.getServiceType().name().equals(serviceRequest.getServiceType())).findFirst();

            if (tlService.isPresent()) {
                TLService tlServiceSave = tlService.get();
                tlServiceSave.setActivated(serviceRequest.isActive());
                tlServiceRepository.save(tlServiceSave);
            } else {
                System.err.println(serviceRequest);
                user.getTlServices().add(TLService.builder()
                        .activated(serviceRequest.isActive())
                        .serviceType(ServiceType.valueOf(serviceRequest.getServiceType().toUpperCase()))
                        .targetNumber(serviceRequest.getTargetNumber())
                        .build());
                subscriberUserRepository.save(user);
            }
            return new ResponseEntity<>(serviceRequest.getServiceType() + "is " + (serviceRequest.isActive() ? "activated" : "unactivated") + " to " + number, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with phone  = " + number + "not found", HttpStatus.NOT_FOUND);
        }
    }
}
