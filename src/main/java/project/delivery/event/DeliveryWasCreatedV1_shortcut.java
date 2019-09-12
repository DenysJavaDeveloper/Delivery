package project.delivery.event;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
public class DeliveryWasCreatedV1_shortcut {

	public final UUID id;
	public final String invoiceInternalNumber;
	public final String invoiceExternalNumber;
	public final String supplier;
	public final String warehouse;
	public final int qty;
	public final String cost;
	public final int boxQty;
	public final LocalDateTime createdAt;

}
