package project.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class DeliveryItemV2 implements Serializable {

	public UUID id;
	public OrderRequestV2 orderRequest;
	public InvoiceItemV2 actualInvoiceItem;
	public Boolean isCompleteRequest;

}
