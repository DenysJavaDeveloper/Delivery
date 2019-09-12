package project.delivery.port.amqp.consume;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class RabbitAmqpListener {

	private final RoutingEventBus eventBus;
	private final ObjectMapper mapper;
	private final EventMapper eventMapper;

	@RabbitListener(queues = "${spring.rabbitmq.queue}")
	public void on(final ConsumedEvent message) {
		log.debug("Event was consumed:\n" + message.toString());
		route(message);
	}

	private void route(final ConsumedEvent event) {
		eventMapper.map(event.meta.routingKey()).ifPresentOrElse(
			eventClass -> {
				try {
					eventBus.publish(
						mapper.readValue(
							mapper.writeValueAsString(event.body),
							eventClass
						)
					);
				} catch (IOException e) {
					log.error(
						"Fail to process event " + event.meta.routingKey() + " (" + event.meta.id + ")",
						e
					);
				}
			},
			() -> {
				log.warn("Consumes not mapped event: " + event.meta.routingKey());
				log.warn(event.toString());
			}
		);
	}
}
