package com.zikozee.services;

import com.zikozee.converters.RecipeCommandToRecipe;
import com.zikozee.exceptions.NotFoundException;
import com.zikozee.repositories.RecipeRepository;
import com.zikozee.commands.RecipeCommand;
import com.zikozee.converters.RecipeToRecipeCommand;
import com.zikozee.domain.Recipe;
import com.zikozee.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;



    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        return recipeReactiveRepository.findAll();

    }

    @Override
    public Mono<Recipe> findById(String id) {

        return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {

        return  recipeReactiveRepository.findById(id)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(rc -> rc.setRecipeId(recipeCommand.getId()));

                    return recipeCommand;
                });

    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Mono<RecipeCommand> savedRecipe = recipeReactiveRepository.save(detachedRecipe).map(recipeToRecipeCommand::convert);
        log.debug("Saved RecipeId:" + savedRecipe.block().getId());
        return savedRecipe;
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeReactiveRepository.deleteById(idToDelete).block();

    }
}
