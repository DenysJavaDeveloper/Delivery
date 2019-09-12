package project.delivery.event;

import lombok.AllArgsConstructor;
import lombok.ToString;
import project.delivery.domain.DeliveryBoxV1;
import project.delivery.domain.DeliveryItemV2;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ToString
@AllArgsConstructor
public class DeliveryWasCreatedV1 {

	public final UUID id;
	public final String invoiceInternalNumber;
	public final String invoiceExternalNumber;
	public final String supplier;
	public final String warehouse;
	public final List<DeliveryItemV2> deliveryItems;
	public final Set<DeliveryBoxV1> deliveryBoxes;
	public final LocalDateTime createdAt;

}
