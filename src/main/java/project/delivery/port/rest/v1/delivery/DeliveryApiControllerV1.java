package project.delivery.port.rest.v1.delivery;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.delivery.application.DeliveryService;
import project.delivery.domain.DeliveryItemV2;
import zakazae.lib.rest.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryApiControllerV1 {

	private final Deliveries deliveries;
	private final DeliveryService application;

	@GetMapping("/{id}")
	public Delivery getDeliveryById(
		@PathVariable final UUID id
	) {

		return deliveries
			.findById(id)
			.orElseThrow(
				() -> new ResourceNotFoundException(id.toString(), "Delivery not found")
			).delivery;
	}


	@PostMapping("/deliveryitems")
	public List<DeliveryItemV2> getDeliveryItems(
		@RequestBody final DeliveryItemsRequestV1 request
	) {
		return application.getDeliveryFor(
			request.supplier,
			request.items
		);
	}


}
