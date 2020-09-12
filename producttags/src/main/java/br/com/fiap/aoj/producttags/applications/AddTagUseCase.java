package br.com.fiap.aoj.producttags.applications;

import br.com.fiap.aoj.producttags.data.TagRepository;
import br.com.fiap.aoj.producttags.domain.ProductDomain;
import br.com.fiap.aoj.producttags.domain.TagDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AddTagUseCase {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddTagUseCase.class);

	private final TagRepository tagRepository;

	public AddTagUseCase(final TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}

	public void add(final TagDomain tagDomain){
		try{
			LOGGER.debug("m=add(ticketDomain={})", tagDomain);

			if((tagRepository.existsById(tagDomain.getTag()))){
				tagRepository.findById(tagDomain.getTag())
						.map(tagFound -> append(tagFound, tagDomain.getProducts()))
						.ifPresent(tagRepository::save);
				return;
			}

			tagRepository.insert(tagDomain);
		}catch (Exception exception){
			LOGGER.error("ex(message={}, cause={})", exception.getMessage(), exception);
		}
	}

	private TagDomain append(TagDomain tagFound, Set<ProductDomain> products) {
		final Set<ProductDomain> productsMerged = Stream.of(products, tagFound.getProducts())
				.flatMap(p -> p.stream()) //
				.collect(Collectors.toSet());
		return TagDomain //
				.builder() //
				.tag(tagFound.getTag()) //
				.products(productsMerged) //
				.builder();
	}
}