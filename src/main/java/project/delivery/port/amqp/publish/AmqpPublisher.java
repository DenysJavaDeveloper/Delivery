package project.delivery.port.amqp.publish;

import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AmqpPublisher {

	@Value("${spring.application.name}")
	private String source;

	private final RabbitTemplate rabbitTemplate;

	public void publish(
		final Object body
	) {
		final Meta meta = new Meta(
			UUID.randomUUID(),
			MDC.get("X-B3-TraceId"),
			LocalDateTime.now(),
			source,
			body.getClass().getSimpleName()
		);

		rabbitTemplate.convertAndSend(
			meta.routingKey(),
			Map.of(
				"meta", meta,
				"body", body
			)
		);
	}
}
