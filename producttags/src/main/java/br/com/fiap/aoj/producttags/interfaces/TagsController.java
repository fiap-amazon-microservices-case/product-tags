package br.com.fiap.aoj.producttags.interfaces;

import br.com.fiap.aoj.producttags.applications.FindProductByTagUseCase;
import br.com.fiap.aoj.producttags.applications.AddTagUseCase;
import br.com.fiap.aoj.producttags.domain.ProductDomain;
import br.com.fiap.aoj.producttags.domain.TagDomain;
import br.com.fiap.aoj.producttags.interfaces.converters.TagDomainToTagDtoConverter;
import br.com.fiap.aoj.producttags.interfaces.converters.TagDtoToTagDomainConverter;
import br.com.fiap.aoj.producttags.interfaces.dtos.TagDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "${api.version.v1:/v1}/tags")
class TagsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TagsController.class);

	private final AddTagUseCase addTagUseCase;
	private final FindProductByTagUseCase findProductByTagUseCase;
	private final TagDtoToTagDomainConverter ticketDtoToTicketDomainConverter;
	private final TagDomainToTagDtoConverter tagDomainToTagDtoConverter;

	TagsController(final AddTagUseCase addTagUseCase,
			final FindProductByTagUseCase findProductByTagUseCase,
			final TagDtoToTagDomainConverter ticketDtoToTicketDomainConverter,
			final TagDomainToTagDtoConverter tagDomainToTagDtoConverter) {
		this.addTagUseCase = addTagUseCase;
		this.findProductByTagUseCase = findProductByTagUseCase;
		this.ticketDtoToTicketDomainConverter = ticketDtoToTicketDomainConverter;
		this.tagDomainToTagDtoConverter = tagDomainToTagDtoConverter;
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public Mono<Void> tag(@RequestBody @Valid final TagDto tagDto){
		LOGGER.debug("m=tag(tagDto={})", tagDto);

		final TagDomain tagDomain = ticketDtoToTicketDomainConverter.convert(tagDto);
		addTagUseCase.add(tagDomain);

		return Mono.empty();
	}

	@PostMapping("{tag}")
	@ResponseStatus(CREATED)
	public Mono<Void> tag(@PathVariable("tag") final String tag,
			@RequestParam("productId") final String productId,
			@RequestParam("productName") final String productName){
		LOGGER.debug("m=tag(tag={}, productId={}, productName={})", tag, productId, productName);

		final TagDomain tagDomain = buildTag(tag, productId, productName);
		addTagUseCase.add(tagDomain);

		return Mono.empty();
	}

	private TagDomain buildTag(final String tag, final String productId, final String productName) {
		final ProductDomain productDomain = ProductDomain.builder() //
												.productId(productId) //
												.name(productName) //
												.build();
		return TagDomain.builder() //
				.tag(tag) //
				.product(productDomain) //
				.builder();
	}

	@GetMapping("{tag}")
	@ResponseStatus(OK)
	public Flux<TagDto> find(@PathVariable("tag") String tag){
		LOGGER.debug("m=find(tag={})", tag);

		return Flux //
				.fromStream(findProductByTagUseCase.find(tag) //
				.map(tagDomainToTagDtoConverter::convert));
	}
}