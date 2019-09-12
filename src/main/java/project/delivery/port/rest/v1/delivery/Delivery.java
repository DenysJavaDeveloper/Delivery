package project.delivery.port.rest.v1.delivery;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import project.delivery.domain.DeliveryBoxV1;
import project.delivery.domain.DeliveryItemV2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class Delivery implements Serializable {

	public UUID number;
	public String warehouseId;
	public String supplierId;
	public List<DeliveryItemV2> deliveryItems;
	public Set<DeliveryBoxV1> deliveryBoxes;
	public LocalDateTime createdAt;

}
