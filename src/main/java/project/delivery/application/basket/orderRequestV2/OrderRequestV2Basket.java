package project.delivery.application.basket.orderRequestV2;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import project.delivery.domain.OrderRequestV2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestV2Basket implements Serializable {

	@Id
	public String number;

	public String supplier;

	@Column(columnDefinition = "jsonb")
	@Type(type = "OrderRequestV2Type")
	public OrderRequestV2 orderRequest;


}
