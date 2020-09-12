package br.com.fiap.aoj.producttags.interfaces.converters;

import br.com.fiap.aoj.producttags.domain.TagDomain;
import br.com.fiap.aoj.producttags.interfaces.dtos.ProductDto;
import br.com.fiap.aoj.producttags.interfaces.dtos.TagDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Lazy
@Component
public class TagDomainToTagDtoConverter implements Converter<TagDomain, TagDto> {

	private final ProductDomainToProductDtoConverter productDomainToProductDtoConverter;

	public TagDomainToTagDtoConverter(final ProductDomainToProductDtoConverter productDomainToProductDtoConverter) {
		this.productDomainToProductDtoConverter = productDomainToProductDtoConverter;
	}

	@Override
	public TagDto convert(final TagDomain source) {
		final TagDto tagDto = new TagDto();
		tagDto.setTag(source.getTag());
		tagDto.setProducts(buildClient(source));

		return tagDto;
	}

	private Set<ProductDto> buildClient(final TagDomain source) {
		return source.getProducts()
				.stream()
				.map(productDomainToProductDtoConverter::convert) //
				.collect(Collectors.toSet());
	}
}