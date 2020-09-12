package br.com.fiap.aoj.producttags.domain;

import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class TagDomain implements Serializable {

	private static final long serialVersionUID = 1649219665299117651L;

	@MongoId
	private String tag;

	private Set<ProductDomain> products;

	private TagDomain(final Builder builder){
		this.tag = builder.tag;
		this.products = builder.products;
	}

	//Construtor padrão para serialização do mongo
	public TagDomain(){}

	public String getTag() {
		return tag;
	}

	public Set<ProductDomain> getProducts() {
		return products;
	}

	public static final Tag builder(){
		return new Builder();
	}

	public static final class Builder implements Tag, Build{
		private String tag;
		private Set<ProductDomain> products= Collections.emptySet();

		@Override
		public Build tag(final String tag) {
			this.tag = tag;
			return this;
		}

		@Override
		public Build product(final ProductDomain productDomain) {
			products.add(productDomain);
			return this;
		}

		@Override
		public Build products(final Set<ProductDomain> productsDomain) {
			this.products = productsDomain;
			return this;
		}

		@Override
		public TagDomain builder() {
			return new TagDomain(this);
		}
	}

	public interface Tag{
		public Build tag(final String tag);
	}

	public interface Build{
		public Build product(final ProductDomain productDomain);
		public Build products(final Set<ProductDomain> productsDomain);
		public TagDomain builder();
	}
}
