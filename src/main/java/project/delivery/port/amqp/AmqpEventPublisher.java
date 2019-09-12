package project.delivery.port.amqp;

import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import project.delivery.event.DeliveryWasCreatedV1_shortcut;
import project.delivery.port.amqp.publish.AmqpPublisher;

@AllArgsConstructor
@Component
public class AmqpEventPublisher {

	private final AmqpPublisher amqpPublisher;

	@EventHandler
	public void on(final DeliveryWasCreatedV1_shortcut event) {
		amqpPublisher.publish(event);
	}
}
