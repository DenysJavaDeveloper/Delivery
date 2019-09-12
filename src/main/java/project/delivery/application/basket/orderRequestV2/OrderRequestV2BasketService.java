package project.delivery.application.basket.orderRequestV2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import project.delivery.domain.DeliveryItemV2;
import project.delivery.domain.OrderRequestV2;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

@Component
@AllArgsConstructor
public class OrderRequestV2BasketService {

	private final OrderRequestV2Baskets orderRequestV2Baskets;

	public void addToBasket(final OrderRequestV2 orderRequest) {
		orderRequestV2Baskets.save(
			new OrderRequestV2Basket(
				orderRequest.number,
				orderRequest.productCard.supplier,
				orderRequest
			)
		);
	}

	public Set<OrderRequestV2> timeOrderedOrderRequests(final String supplier) {
		return orderRequestV2Baskets
			.findAllBySupplier(supplier)
			.stream()
			.map(basket -> basket.orderRequest)
			.collect(toCollection(LinkedHashSet::new));
	}

	public void changeQtyOrDeleteOrderRequest(final List<DeliveryItemV2> items) {

		items.stream()
			.filter(deliveryItemV2 -> deliveryItemV2.orderRequest != null)
			.forEach(deliveryItem ->
				orderRequestV2Baskets
					.findByNumber(deliveryItem.orderRequest.number)
					.ifPresent(
						basket -> {
							if (deliveryItem.isCompleteRequest) {
								orderRequestV2Baskets.delete(deliveryItem.orderRequest.number);
							} else {
								basket.orderRequest.productCard.edit(
									basket.orderRequest.productCard.qty - deliveryItem.orderRequest.productCard.qty
								);
							}
							orderRequestV2Baskets.save(basket);
						}
					)
			);
	}

}
