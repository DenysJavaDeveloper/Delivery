package project.delivery.domain;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@NoArgsConstructor
@AllArgsConstructor
public class DeliveryBoxV1 implements Serializable {

	public String externalNumber;
	public Integer qty;
	public Money cost;
	public Double netWeight;
	public Double grossWeight;
	public Double volWeight;

	public DeliveryBoxV1(
		final String externalBoxNumber,
		final List<DeliveryItemV2> deliveries
	) {

		final var qtyAt = new AtomicInteger();
		final var costAt = new AtomicReference<Money>();
		final var weightAt = new AtomicDouble();

		deliveries.stream()
			.filter(delivery -> delivery.actualInvoiceItem.boxNumber != null)
			.filter(delivery -> delivery.actualInvoiceItem.boxNumber.equals(externalBoxNumber))
			.forEach(
				delivery -> {

					qtyAt.set(qtyAt.addAndGet(delivery.actualInvoiceItem.qty));

					weightAt.set(weightAt.addAndGet(delivery.actualInvoiceItem.getNetWeight()));

					Optional.ofNullable(costAt.get())
						.ifPresentOrElse(
							costs -> costAt.set(costAt.get().plus(delivery.actualInvoiceItem.cost)),
							() -> costAt.set(delivery.actualInvoiceItem.cost)
						);
				}
			);

		qty = qtyAt.get();
		cost = costAt.get();
		netWeight = weightAt.get();
		externalNumber = externalBoxNumber;

	}

}
