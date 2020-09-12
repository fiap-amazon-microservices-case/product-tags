package br.com.fiap.aoj.producttags.data;

import br.com.fiap.aoj.producttags.domain.TagDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

public interface TagRepository extends MongoRepository<TagDomain, String> { }