package fr.utt.animehub.lo10projetanimehub.service;

import fr.utt.animehub.lo10projetanimehub.model.Recipe;
import fr.utt.animehub.lo10projetanimehub.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    public Recipe getRecipeById(Integer id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()){
            Recipe recipeMetier = optionalRecipe.get();
            LOGGER.info("Recette trouvée avec l'id {}", id);
            return recipeMetier;
        } else {
            LOGGER.info("Null, Aucune recette trouvée avec l'id renseigné : {}", id);
            return null;
        }
    }

    public List<Recipe> getRecipesByAnimeid(Integer animeid) {
        return recipeRepository.findAllByAnimeid(animeid);
    }

    public List<Recipe> getRecipesByAuthor(String author) {
        return recipeRepository.findAllByAuthor(author);
    }

    public List<Recipe> getRecipesByNameContainingAndAnimeid(String name, Integer animeid) {
        return recipeRepository.findByNameContainingAndAnimeid(name, animeid);
    }

    public List<Recipe> getRecipesByNameContainingAndAnime(String name, String anime) {
        return recipeRepository.findByNameContainingAndAnime(name, anime);
    }

    public Recipe saveRecipe(Recipe recipe) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipe.getName());
        newRecipe.setAuthor(recipe.getAuthor());
        newRecipe.setAnime(recipe.getAnime());
        newRecipe.setAnimeid(recipe.getAnimeid());
        newRecipe.setIngredients(recipe.getIngredients());
        newRecipe.setSteps(recipe.getSteps());
        return recipeRepository.save(newRecipe);
    }

    public boolean deleteRecipe(Integer id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return !recipeRepository.existsById(id);
        } else {
            return false;
        }
    }

    public Recipe updateRecipe(Integer id, Recipe updatedRecipe){
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe existingRecipe = optionalRecipe.get();
            existingRecipe.setName(updatedRecipe.getName());
            existingRecipe.setAuthor(updatedRecipe.getAuthor());
            existingRecipe.setAnime(updatedRecipe.getAnime());
            existingRecipe.setAnimeid(updatedRecipe.getAnimeid());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setSteps(updatedRecipe.getSteps());
            return recipeRepository.save(existingRecipe);
        } else {
            return null;
        }
    }
}
