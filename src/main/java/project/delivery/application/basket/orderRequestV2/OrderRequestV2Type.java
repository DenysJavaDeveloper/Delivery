package project.delivery.application.basket.orderRequestV2;

import project.delivery.config.JsonbType;
import project.delivery.domain.OrderRequestV2;

public class OrderRequestV2Type extends JsonbType {

	@Override
	public Class<OrderRequestV2> returnedClass() {
		return OrderRequestV2.class;
	}
}
