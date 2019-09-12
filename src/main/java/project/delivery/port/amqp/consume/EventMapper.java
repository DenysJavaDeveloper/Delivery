package project.delivery.port.amqp.consume;

import java.util.Optional;

public interface EventMapper {

	Optional<Class<?>> map(String routingKey);

	boolean eventIsMapped(Class<?> eventClass);

}
