package project.delivery.domain;

import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
public class DeliveryBoxesV1 implements Serializable {

	private final Set<DeliveryBoxV1> boxes = new HashSet<>();

	public DeliveryBoxesV1(final List<DeliveryItemV2> deliveries) {

		deliveries
			.forEach(
				delivery -> {

					final boolean has = boxes.stream()
						.anyMatch(box -> box.externalNumber.equals(delivery.actualInvoiceItem.boxNumber));

					if (!has)
						boxes.add(
							new DeliveryBoxV1(
								delivery.actualInvoiceItem.boxNumber,
								deliveries
							)
						);
				}
			);
	}

	public Set<DeliveryBoxV1> asList() {
		return boxes;
	}

}
