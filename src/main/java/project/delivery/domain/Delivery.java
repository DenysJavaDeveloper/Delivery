package project.delivery.domain;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import project.delivery.domain.command.CreateDeliveryV1;
import project.delivery.event.DeliveryWasCreatedV1_shortcut;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class Delivery {

	@AggregateIdentifier
	private UUID id;


	@CommandHandler
	public Delivery(final CreateDeliveryV1 command) {

		apply(
			new DeliveryWasCreatedV1_shortcut(
				command.id,
				command.invoice.internalNumber,
				command.invoice.externalNumber,
				command.invoice.supplier,
				command.invoice.warehouse,
				command.qty,
				command.cost.toString(),
				command.invoice.boxQty,
				command.createdAt
			)
		);
	}

	@EventSourcingHandler
	public void mutate(final DeliveryWasCreatedV1_shortcut event) {
		id = event.id;
	}

}
