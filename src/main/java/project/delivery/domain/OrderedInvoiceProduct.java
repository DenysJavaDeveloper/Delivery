package project.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public final class OrderedInvoiceProduct implements Serializable {

	public String brand;
	public String code;

}
