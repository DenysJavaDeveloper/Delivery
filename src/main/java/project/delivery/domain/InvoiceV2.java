package project.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceV2 {

	public String internalNumber;
	public String externalNumber;
	public String supplier;
	public String warehouse;
	public List<InvoiceItemV2> items;
	public Integer boxQty;
	public LocalDateTime createdAt;

}
