package project.delivery.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class DeliveryItemsV2 {

	private final List<DeliveryItemV2> deliveryItems;

	private final List<InvoiceItemV2> invoiceItems;
	private final Set<OrderRequestV2> orderRequests;


	public DeliveryItemsV2(
		final List<InvoiceItemV2> invoices,
		final Set<OrderRequestV2> orders
	) {
		deliveryItems = new ArrayList<>();
		invoiceItems = invoices;
		orderRequests = orders;

		createDeliveryByReference();
		createDeliveryByOnlyThisQty();
		createDeliveryItems();
		createNotDistributedDelivery();
	}


	private void createDeliveryByReference() {

		invoiceItems.stream()
			.filter(invoiceItem -> invoiceItem.reference != null)
			.collect(groupingBy(i -> i.reference))
			.forEach(
				(reference, invItems) -> {

					final var qty = invItems.stream()
						.collect(Collectors.summarizingInt(invItem -> invItem.qty));

					final var qtyCounter = new AtomicInteger();

					invItems
						.forEach(
							invoiceItem ->
								orderRequests.stream()
									.filter(orderRequest -> orderRequest.number.equals(reference))
									.findFirst()
									.ifPresent(ordReq ->
										distribute(
											ordReq,
											invoiceItem,
											qtyCounter.addAndGet(invoiceItem.qty) == qty.getSum()

										)
									)
						);
				}
			);

	}


	private void createDeliveryByOnlyThisQty() {
		orderRequests
			.stream()
			.filter(orderRequest -> orderRequest.productCard.onlyThisQty.equals(true))
			.filter(ordReq -> ordReq.qtyToDistribute > 0)
			.forEach(this::createDeliveryItems);

	}

	private void createDeliveryItems() {

		orderRequests
			.stream()
			.filter(ordReq -> ordReq.qtyToDistribute > 0)
			.forEach(this::createDeliveryItems);
	}

	private void createDeliveryItems(final OrderRequestV2 orderRequest) {

		invoiceItems.stream()
			.filter(invoiceItem -> invoiceItem.qty > 0)
			.filter(invoiceItem -> !isDistributed(orderRequest.number))
			.filter(invoiceItem -> isCorrelated(invoiceItem, orderRequest))
			.findFirst()
			.ifPresent(
				invoiceItem -> distribute(orderRequest, invoiceItem, true)
			);

	}


	private void distribute(
		final OrderRequestV2 orderRequest,
		final InvoiceItemV2 invoiceItem,
		final boolean isComplete
	) {

		final int qtyToDistribute = Math.min(orderRequest.productCard.qty, invoiceItem.qty);

		createDeliveryItem(
			orderRequest,
			invoiceItem,
			qtyToDistribute,
			isComplete
		);

	}


	private void createDeliveryItem(
		final OrderRequestV2 orderRequest,
		final InvoiceItemV2 invoiceItem,
		final int qtyToDistribute,
		final boolean isCompleteRequest
	) {

		deliveryItems.add(
			new DeliveryItemV2(
				UUID.randomUUID(),
				orderRequest,
				new InvoiceItemV2(
					invoiceItem.actualProduct,
					invoiceItem.orderedProduct,
					invoiceItem.price,
					qtyToDistribute,
					invoiceItem.price.multipliedBy(qtyToDistribute),
					invoiceItem.barCode,
					invoiceItem.boxNumber,
					invoiceItem.reference
				),
				isCompleteRequest
			)
		);


		orderRequest.qtyToDistribute -= qtyToDistribute;
		invoiceItem.qty -= qtyToDistribute;
	}


	private void createNotDistributedDelivery() {
		invoiceItems.stream()
			.filter(invoiceItem -> invoiceItem.qty > 0)
			.forEach(
				invoiceItem ->
					deliveryItems.add(
						new DeliveryItemV2(
							UUID.randomUUID(),
							null,
							invoiceItem,
							false
						)
					)
			);
	}

	private boolean isDistributed(final String orderRequestNumber) {
		return deliveryItems.stream()
			.anyMatch(
				deliveryItem ->
					deliveryItem.orderRequest.number.equals(orderRequestNumber) &&
						deliveryItem.isCompleteRequest
			);
	}


	private Boolean isCorrelated(
		final InvoiceItemV2 invoiceItem,
		final OrderRequestV2 orderRequest
	) {
		return
			orderRequest.forTheSameProduct(invoiceItem.orderedProduct) &&
				orderRequest.orderOptionsAreFitsTo(invoiceItem.actualProduct, invoiceItem.qty);
	}

	public List<DeliveryItemV2> asList() {
		return deliveryItems;
	}
}
