package project.delivery.port.rest.v1.delivery;

import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import project.delivery.event.DeliveryWasCreatedV1;

@Component
@AllArgsConstructor
public class DeliveryProjector {

	private final Deliveries deliveries;

	@EventHandler
	public void on(final DeliveryWasCreatedV1 event) {

		deliveries.save(
			new DeliveryResponse(
				event.id,
				new Delivery(
					event.id,
					event.warehouse,
					event.supplier,
					event.deliveryItems,
					event.deliveryBoxes,
					event.createdAt
				)
			)
		);
	}

}
