package fr.utt.animehub.lo10projetanimehub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String author;

    private String anime;

    private Integer animeid;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String steps;

    public Recipe() {
    }

    public Recipe(String name, String author, String anime, Integer animeid, String ingredients, String steps) {
        this.name = name;
        this.author = author;
        this.anime = anime;
        this.animeid = animeid;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(Integer id, String name, String author, String anime, Integer animeid, String ingredients, String steps) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.anime = anime;
        this.animeid = animeid;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public Integer getAnimeid() {
        return animeid;
    }

    public void setAnimeid(Integer animeid) {
        this.animeid = animeid;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
