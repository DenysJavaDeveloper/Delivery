package project.delivery.port.rest.v1.delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface Deliveries extends JpaRepository<DeliveryResponse, UUID> {

	Optional<DeliveryResponse> findById(UUID id);
}
