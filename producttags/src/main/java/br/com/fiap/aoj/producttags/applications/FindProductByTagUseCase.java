package br.com.fiap.aoj.producttags.applications;

import br.com.fiap.aoj.producttags.domain.TagDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class FindProductByTagUseCase {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddTagUseCase.class);

	private final MongoOperations mongoOperations;

	public FindProductByTagUseCase(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public Stream<TagDomain> find(final String tag){
		LOGGER.debug("m=find(tag={})", tag);

		final Query query = buildQueryByContainsTag(tag);
		return mongoOperations.find(query, TagDomain.class).stream();
	}

	private Query buildQueryByContainsTag(final String tag) {
		final Query query = new Query();
		query.addCriteria(Criteria.where("tag").regex(tag));

		return query;
	}
}