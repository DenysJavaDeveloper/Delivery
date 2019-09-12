package project.delivery.port.amqp.consume;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@NoArgsConstructor
@ToString
public class ConsumedEvent implements Serializable {

	public Meta meta;
	public Map<String, Object> body;

}
