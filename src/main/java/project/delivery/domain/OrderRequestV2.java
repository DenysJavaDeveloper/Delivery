package project.delivery.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import zakazae.lib.productcard.ProductCard;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public final class OrderRequestV2 implements Serializable {

	public String number;
	public String orderNumber;
	public ProductCard productCard;
	public int qtyToDistribute;
	public LocalDateTime createdAt;

	//FIXME: добавить проверку на превышение ценны
	boolean forTheSameProduct(final OrderedInvoiceProduct product) {
		return productCard.product.brand.equals(product.brand) &&
			productCard.product.code.equals(product.code);
	}


	boolean orderOptionsAreFitsTo(
		final ActualInvoiceProductV1 product,
		final int actualQty
	) {
		return
			onlyThisNumberFitsTo(product) &&
				onlyThisQtyFitsTo(actualQty);
	}

	private boolean onlyThisNumberFitsTo(final ActualInvoiceProductV1 product) {
		return !productCard.onlyThisNumber ||
			(
				productCard.product.brand.equals(product.brand) &&
					productCard.product.code.equals(product.code)
			);
	}

	private boolean onlyThisQtyFitsTo(final int actualQty) {
		return !productCard.onlyThisQty ||
			productCard.qty <= actualQty;
	}

}
