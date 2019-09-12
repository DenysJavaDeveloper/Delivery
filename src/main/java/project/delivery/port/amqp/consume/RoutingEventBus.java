package project.delivery.port.amqp.consume;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.messaging.MetaData;

@AllArgsConstructor
@Slf4j
public class RoutingEventBus {

	private final EventBus eventBus;

	void publish(Object event) {
		log.debug("Event gonna be published:\n" + event.toString());
		eventBus.publish(
			createMessage(event)
		);
	}

	private EventMessage createMessage(Object payload) {
		return new GenericEventMessage<>(payload, MetaData.emptyInstance());
	}
}
