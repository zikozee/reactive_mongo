package com.zikozee.services;

import com.zikozee.commands.RecipeCommand;
import com.zikozee.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
public interface RecipeService {

    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String id);

    Mono<RecipeCommand> findCommandById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
    void deleteById(String idToDelete);
}
