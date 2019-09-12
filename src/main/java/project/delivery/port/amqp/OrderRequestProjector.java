package project.delivery.port.amqp;

import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import project.delivery.application.basket.orderRequestV2.OrderRequestV2BasketService;
import project.delivery.domain.OrderRequestV2;
import project.delivery.event.DeliveryWasCreatedV1;
import project.delivery.port.amqp.event.OrderRequestWasStandbyV1;

@Component
@AllArgsConstructor
public class OrderRequestProjector {

	private final OrderRequestV2BasketService orderRequestBasketService;

	@EventHandler
	public void on(final OrderRequestWasStandbyV1 event) {

		orderRequestBasketService.addToBasket(
			new OrderRequestV2(
				event.number,
				event.orderNumber,
				event.productCard,
				event.productCard.qty,
				event.createdAt
			)
		);
	}

	@EventHandler
	public void on(final DeliveryWasCreatedV1 event) {

		orderRequestBasketService.changeQtyOrDeleteOrderRequest(event.deliveryItems);
	}
}
