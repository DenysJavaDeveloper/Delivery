package project.delivery.port.amqp.publish;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@ToString
public final class Meta implements Serializable {

	public final UUID id;
	public final String correlationId;
	public final LocalDateTime publishedAt;
	public final String source;
	public final String name;

	String routingKey() {
		return source + "." + name;
	}
}
