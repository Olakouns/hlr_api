package sn.esmt.hlr_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.hlr_api.entites.SubscriberUser;

import java.util.Optional;
import java.util.UUID;

public interface SubscriberUserRepository extends JpaRepository<SubscriberUser, UUID>{
    Optional<SubscriberUser> findByPhoneNumberOrImsi(String phoneNumber, String imsi);

    Optional<SubscriberUser> findFirstByPhoneNumberOrImsi(String number, String number1);
}
