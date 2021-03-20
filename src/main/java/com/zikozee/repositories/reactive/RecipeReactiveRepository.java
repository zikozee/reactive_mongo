package com.zikozee.repositories.reactive;

import com.zikozee.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
