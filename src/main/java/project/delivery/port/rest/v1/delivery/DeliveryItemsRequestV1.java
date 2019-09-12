package project.delivery.port.rest.v1.delivery;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import project.delivery.domain.InvoiceItemV2;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class DeliveryItemsRequestV1 {

	public String supplier;
	public List<InvoiceItemV2> items;
}
