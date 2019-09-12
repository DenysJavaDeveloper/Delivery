package project.delivery.port.rest.v1.delivery;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.delivery.application.DeliveryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class DeliveryRestControllerV1 {

	private final DeliveryService application;
	private final Deliveries deliveries;

	@GetMapping("/v1/deliveries")
	public List<Delivery> getAll() {
		return deliveries
			.findAll()
			.stream()
			.map(response -> response.delivery)
			.collect(Collectors.toList());
	}


	@PostMapping("/v1/deliveries")
	public void createDelivery(
		@RequestBody String invoiceNumber
	) {
		application.createDeliveryFor(invoiceNumber);
	}
}
