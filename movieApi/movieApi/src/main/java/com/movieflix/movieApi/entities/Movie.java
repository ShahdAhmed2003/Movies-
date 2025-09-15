package com.movieflix.movieApi.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {
    public Movie() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false,length=200)
    @NotBlank(message="Please provide movie's title!")

    private String title;
    @Column(nullable = false)//DB level validation
    @NotBlank(message="Please provide movie's director")//App level validation
    private String director;
    @Column(nullable = false)
    @NotBlank(message="Please provide movie's studio")
    private String studio;
    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> movieCast;
    @Column(nullable = false)
    private Integer releaseYear;
    @Column(nullable = false)
    @NotBlank(message="Please provide a movie's poster")
    private String poster;

    public Movie(Integer movieId, String title, String director, String studio, Set<String> movieCast, Integer releaseYear, String poster) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.movieCast = movieCast;
        this.releaseYear = releaseYear;
        this.poster = poster;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setMovieCast(Set<String> movieCast) {
        this.movieCast = movieCast;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getStudio() {
        return studio;
    }

    public Set<String> getMovieCast() {
        return movieCast;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getPoster() {
        return poster;
    }
}
