package project.delivery.application.suppliers;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import project.delivery.domain.InvoiceV2;

@FeignClient(name = "suppliers")
public interface Suppliers {

	@GetMapping("/v2/invoices/{invoiceNumber}")
	InvoiceV2 getInvoiceV2(
		@PathVariable("invoiceNumber") String invoiceNumber
	);

}
