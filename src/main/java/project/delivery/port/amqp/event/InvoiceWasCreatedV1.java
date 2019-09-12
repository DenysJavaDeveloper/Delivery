package project.delivery.port.amqp.event;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
public class InvoiceWasCreatedV1 {

	public String number;
	public String supplier;
	public String warehouse;
	public LocalDateTime createdAt;

}
