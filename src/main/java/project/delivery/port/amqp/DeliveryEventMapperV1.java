package project.delivery.port.amqp;

import org.springframework.stereotype.Component;
import project.delivery.port.amqp.consume.EventMapper;
import project.delivery.port.amqp.event.InvoiceWasCreatedV1;
import project.delivery.port.amqp.event.OrderRequestWasStandbyV1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class DeliveryEventMapperV1 implements EventMapper {

	private final static Map<String, Class<?>> eventClasses = new HashMap<>();

	static {
		eventClasses.put("suppliers.InvoiceWasCreatedV1", InvoiceWasCreatedV1.class);
		eventClasses.put("order.OrderRequestWasStandbyV1", OrderRequestWasStandbyV1.class);
	}

	@Override
	public Optional<Class<?>> map(final String routingKey) {
		return Optional.ofNullable(eventClasses.get(routingKey));
	}

	@Override
	public boolean eventIsMapped(final Class<?> eventClass) {
		return eventClasses.containsValue(eventClass);
	}
}
