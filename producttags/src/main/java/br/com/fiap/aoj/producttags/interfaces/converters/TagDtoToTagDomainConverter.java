package br.com.fiap.aoj.producttags.interfaces.converters;

import br.com.fiap.aoj.producttags.domain.ProductDomain;
import br.com.fiap.aoj.producttags.domain.TagDomain;
import br.com.fiap.aoj.producttags.interfaces.dtos.TagDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Lazy
@Component
public class TagDtoToTagDomainConverter implements Converter<TagDto, TagDomain> {

	private final ProductDtoToProductDomainConverter productDtoToProductDomainConverter;

	public TagDtoToTagDomainConverter(final ProductDtoToProductDomainConverter productDtoToProductDomainConverter) {
		this.productDtoToProductDomainConverter = productDtoToProductDomainConverter;
	}

	@Override
	public TagDomain convert(final TagDto source) {
		final Set<ProductDomain> productsDomain = source.getProducts()
				.stream()
				.map(productDtoToProductDomainConverter::convert)
				.collect(Collectors.toSet());
		return TagDomain //
				.builder() //
				.tag(source.getTag()) //
				.products(productsDomain) //
				.builder();
	}
}