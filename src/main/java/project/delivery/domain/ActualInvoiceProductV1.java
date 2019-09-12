package project.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public final class ActualInvoiceProductV1 implements Serializable {

	public String brand;
	public String code;
	public String description;
	public Double weight;
	public Boolean original;
	public String countryOfOrigin;

}
