package project.delivery.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.delivery.application.basket.orderRequestV2.OrderRequestV2BasketService;
import project.delivery.application.suppliers.Suppliers;
import project.delivery.domain.DeliveryBoxesV1;
import project.delivery.domain.DeliveryItemV2;
import project.delivery.domain.DeliveryItemsV2;
import project.delivery.domain.InvoiceItemV2;
import project.delivery.domain.InvoiceV2;
import project.delivery.domain.command.CreateDeliveryV1;
import project.delivery.port.rest.v1.delivery.Deliveries;
import project.delivery.port.rest.v1.delivery.Delivery;
import project.delivery.port.rest.v1.delivery.DeliveryResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j
@Component
@RequiredArgsConstructor
public class DeliveryService {

	private final CommandGateway commandBus;
	private final Suppliers suppliers;
	private final OrderRequestV2BasketService baskets;

	private final Deliveries deliveries;

	@Value("${delivery.currency.default}")
	private String defaultCurrency;

	public void createDeliveryFor(final String invoiceNumber) {

		final InvoiceV2 invoice = suppliers.getInvoiceV2(invoiceNumber);

		final Money cost = invoice.items
			.stream()
			.map(item -> item.cost)
			.reduce(Money.zero(CurrencyUnit.of(defaultCurrency)), Money::plus);

		final int qty = invoice.items
			.stream()
			.mapToInt(item -> item.qty)
			.sum();

		final double netWeight = invoice.items
			.stream()
			.mapToDouble(InvoiceItemV2::getNetWeight)
			.sum();

		final var deliveryItems = getDeliveryFor(
			invoice.supplier,
			invoice.items
		);

		final UUID id = UUID.randomUUID();

		deliveries.save(
			new DeliveryResponse(
				id,
				new Delivery(
					id,
					invoice.warehouse,
					invoice.supplier,
					deliveryItems,
					new DeliveryBoxesV1(deliveryItems).asList(),
					LocalDateTime.now()
				)
			)
		);

		commandBus.send(
			new CreateDeliveryV1(
				id,
				invoice,
				qty,
				cost,
				netWeight,
				LocalDateTime.now()
			)
		);

	}

	public List<DeliveryItemV2> getDeliveryFor(
		final String supplier,
		final List<InvoiceItemV2> invoiceItems
	) {

		return new DeliveryItemsV2(
			invoiceItems,
			baskets.timeOrderedOrderRequests(supplier)
		).asList();
	}

}
