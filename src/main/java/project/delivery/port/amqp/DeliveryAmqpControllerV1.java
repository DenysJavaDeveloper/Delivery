package project.delivery.port.amqp;

import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import project.delivery.port.amqp.event.InvoiceWasCreatedV1;

@Component
@AllArgsConstructor
public class DeliveryAmqpControllerV1 {

	private final DeliverySelf deliverySelf;

	@EventHandler
	public void on(final InvoiceWasCreatedV1 event) {
		deliverySelf.createDelivery(event.number);
	}
}
