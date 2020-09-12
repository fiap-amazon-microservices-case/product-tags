package br.com.fiap.aoj.producttags.interfaces.dtos;

import javax.validation.constraints.NotNull;

public class ProductDto {

	@NotNull(message = "Campo obrigatório")
	private String productId;

	@NotNull(message = "Campo obrigatório")
	private String name;

	public String getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setName(String name) {
		this.name = name;
	}
}