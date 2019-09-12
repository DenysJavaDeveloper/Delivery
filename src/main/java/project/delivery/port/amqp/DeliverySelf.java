package project.delivery.port.amqp;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "${spring.application.name}", url = "http://localhost:${server.port}")
public interface DeliverySelf {

	@PostMapping("/v1/deliveries")
	void createDelivery(
		@RequestBody String number
	);

}
