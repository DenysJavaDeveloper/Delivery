package project.delivery.config;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.FilteringEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.delivery.port.amqp.DeliveryEventMapperV1;
import project.delivery.port.amqp.consume.RoutingEventBus;

@Configuration
public class AxonConfiguration {

	@Bean
	public RoutingEventBus routingEventBus(final EventBus eventBus) {
		return new RoutingEventBus(
			eventBus
		);
	}

	@Bean
	public EventStorageEngine eventStorageEngine(
		final EntityManagerProvider provider,
		final TransactionManager transactionManager,
		final DeliveryEventMapperV1 eventMapper
	) {
		return new FilteringEventStorageEngine(
			new JpaEventStorageEngine(provider, transactionManager),
			eventMessage -> !eventMapper.eventIsMapped(eventMessage.getPayloadType())
		);
	}
}
