package project.delivery.application.basket.orderRequestV2;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRequestV2Baskets extends JpaRepository<OrderRequestV2Basket, String> {

	Optional<OrderRequestV2Basket> findByNumber(String number);

	List<OrderRequestV2Basket> findAllBySupplier(String supplier);

}
