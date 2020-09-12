package br.com.fiap.aoj.producttags.interfaces.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class TagDto {

	@NotNull(message = "Campo obrigatório")
	@Size(min = 2, max = 64, message = "Campo deve ter entre {min} e {max} caracteres.")
	private String tag;

	@Valid
	@NotNull(message = "Campo obrigatório")
	private Set<ProductDto> products = new HashSet<>();

	public String getTag() {
		return tag;
	}

	public Set<ProductDto> getProducts() {
		return products;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setProducts(Set<ProductDto> products) {
		this.products = products;
	}
}
