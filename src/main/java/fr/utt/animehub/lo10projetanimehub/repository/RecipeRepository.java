package fr.utt.animehub.lo10projetanimehub.repository;

import fr.utt.animehub.lo10projetanimehub.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAllByAnimeid(Integer animeid);

    List<Recipe> findByNameContainingAndAnimeid(String name, Integer animeid);

    List<Recipe> findByNameContainingAndAnime(String name, String anime);

    List<Recipe> findAllByAuthor(String author);
}
