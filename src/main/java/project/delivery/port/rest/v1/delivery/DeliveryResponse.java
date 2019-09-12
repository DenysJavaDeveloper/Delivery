package project.delivery.port.rest.v1.delivery;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse implements Serializable {

	@Id
	public UUID id;

	@Column(columnDefinition = "jsonb")
	@Type(type = "DeliveryType")
	public Delivery delivery;

}
