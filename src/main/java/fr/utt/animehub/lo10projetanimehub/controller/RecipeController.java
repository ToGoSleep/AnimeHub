package fr.utt.animehub.lo10projetanimehub.controller;

import fr.utt.animehub.lo10projetanimehub.model.Recipe;
import fr.utt.animehub.lo10projetanimehub.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "get a specific recipe by id")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Integer id) {
        Recipe recipe = null;
        if (id == null) {
            LOGGER.info("L'id est null, mauvaise requete");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                recipe = recipeService.getRecipeById(id);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Erreur l'id est null ou n'est pas un integer");
            }
            if (recipe == null) {
                LOGGER.info("Aucune recette trouvé avec l'id renseigné : {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(recipe, HttpStatus.OK);
            }
        }
    }

    @GetMapping(value = "/anime", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "get all recipes from an anime by its id")
    public ResponseEntity<List<Recipe>> getRecipesByAnimeid(@RequestParam Integer animeid) {
        try {
            if (animeid == null) {
                LOGGER.info("Le nom de l'anime est null, mauvaise requete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Recipe> recipes = recipeService.getRecipesByAnimeid(animeid);
            if (recipes.isEmpty()) {
                LOGGER.info("Aucune recette trouvé avec l'id de l'anime renseigné : {}", animeid);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(recipes, HttpStatus.OK);
            }
        } catch(Exception e){
            LOGGER.error("Erreur l'id de l'anime est null ou n'est pas un Integer");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/author", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "get all recipes from an author")
    public ResponseEntity<List<Recipe>> getRecipesByAuthor(@RequestParam String author) {
        try {
            if (author == null || author.isEmpty()) {
                LOGGER.info("Le nom de l'auteur est null, mauvaise requete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Recipe> recipes = recipeService.getRecipesByAuthor(author);
            if (recipes.isEmpty()) {
                LOGGER.info("Aucune recette trouvé avec le nom de l'auteur renseigné : {}", author);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(recipes, HttpStatus.OK);
            }
        } catch(Exception e){
            LOGGER.error("Erreur le nom de l'auteur est null ou n'est pas une chaine de caracteres");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "get all recipes containing part of the name of the recipe from an anime by its id")
    public ResponseEntity<List<Recipe>> getRecipesByNameContainingAndAnimeid(@RequestParam String name, @RequestParam Integer animeid) {
        try {
            if (name == null || name.isEmpty() || animeid == null) {
                LOGGER.info("L'id de l'anime ou le nom de la recette est null, mauvaise requete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Recipe> recipes = recipeService.getRecipesByNameContainingAndAnimeid(name, animeid);
            if (recipes.isEmpty()) {
                LOGGER.info("Aucune recette {} trouvée avec l'id de l'anime renseigné : {}", name, animeid);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(recipes, HttpStatus.OK);
            }
        } catch(Exception e){
            LOGGER.error("Erreur l'id de l'anime est null ou n'est pas un Integer ou le nom de la recette est null ou n'est pas une chaine de caracteres");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/search-anime", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "get all recipes containing part of the name of the recipe from an anime by its name")
    public ResponseEntity<List<Recipe>> getRecipesByNameContainingAndAnime(@RequestParam String name, @RequestParam String anime) {
        try {
            if (name == null || name.isEmpty() || anime == null || anime.isEmpty()) {
                LOGGER.info("Le nom de l'anime ou de la recette est null, mauvaise requete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<Recipe> recipes = recipeService.getRecipesByNameContainingAndAnime(name, anime);
            if (recipes.isEmpty()) {
                LOGGER.info("Aucune recette {} trouvée avec le nom de l'anime renseigné : {}", name, anime);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(recipes, HttpStatus.OK);
            }
        } catch(Exception e){
            LOGGER.error("Erreur le nom de l'anime ou de la recette est null ou n'est pas une chaine de caracteres");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "create a recipe")
    public ResponseEntity<Recipe> addRecipe (@RequestBody Recipe recipe) {
        try {
            if (recipe == null || recipe.getName() == null || recipe.getAuthor() == null || recipe.getAnime() == null || recipe.getAnimeid() == null || recipe.getIngredients() == null || recipe.getSteps() == null) {
                LOGGER.info("Un des champs est null ou a recette est null, mauvaise requête");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                Recipe addedRecipe = recipeService.saveRecipe(recipe);
                return new ResponseEntity<>(addedRecipe, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @Operation(summary = "update a recipe by id")
    public ResponseEntity<Recipe> updateRecipeById(@RequestBody Recipe recipe, @PathVariable Integer id) {
        try {
            if (id == null) {
                LOGGER.info("L'id est null, mauvaise requete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (recipe == null || recipe.getName() == null || recipe.getAuthor() == null || recipe.getAnime() == null || recipe.getAnimeid() == null || recipe.getIngredients() == null || recipe.getSteps() == null) {
                LOGGER.info("Un des champs est null ou a recette est null, mauvaise requête");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
                if (updatedRecipe == null) {
                    LOGGER.info("Aucune recette trouvé avec l'id renseigné : {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete a recipe by id")
    public ResponseEntity<Boolean> deleteRecipeById (@PathVariable Integer id){
        try {
            if (id == null){
                LOGGER.info("L'id est null, mauvaise requete");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                boolean deleted = recipeService.deleteRecipe(id);
                if (deleted) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    LOGGER.info("Aucune recette trouvé avec l'id renseigné : {}", id);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
