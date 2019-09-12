package project.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InvoiceItemV2 implements Serializable {

	public ActualInvoiceProductV1 actualProduct;
	public OrderedInvoiceProduct orderedProduct;

	public Money price;
	public Integer qty;
	public Money cost;
	public String barCode;
	public String boxNumber;
	public String reference;

	public Double getNetWeight() {
		return qty * actualProduct.weight;
	}

}
