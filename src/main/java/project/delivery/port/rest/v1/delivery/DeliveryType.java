package project.delivery.port.rest.v1.delivery;

import project.delivery.config.JsonbType;

public class DeliveryType extends JsonbType {

	@Override
	public Class<Delivery> returnedClass() {
		return Delivery.class;
	}
}
