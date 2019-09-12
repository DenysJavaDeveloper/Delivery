package project.delivery.domain.command;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.joda.money.Money;
import project.delivery.domain.InvoiceV2;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class CreateDeliveryV1 {

	@TargetAggregateIdentifier
	public final UUID id;

	public final InvoiceV2 invoice;
	public final int qty;
	public final Money cost;
	public final Double netWeight;
	public final LocalDateTime createdAt;

}
