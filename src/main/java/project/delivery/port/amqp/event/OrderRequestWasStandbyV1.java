package project.delivery.port.amqp.event;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import zakazae.lib.productcard.ProductCard;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestWasStandbyV1 {

	public String number;
	public String orderNumber;
	public ProductCard productCard;
	public LocalDateTime createdAt;
	public LocalDateTime standbyAt;

}
