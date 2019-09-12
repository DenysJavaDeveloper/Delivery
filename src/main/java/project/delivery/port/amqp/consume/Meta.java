package project.delivery.port.amqp.consume;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@ToString
public final class Meta implements Serializable {

	public UUID id;
	public String correlationId;
	public LocalDateTime publishedAt;
	public String source;
	public String name;

	public String routingKey() {
		return source + "." + name;
	}
}
