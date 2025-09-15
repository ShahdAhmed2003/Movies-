package com.movieflix.movieApi.dto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MovieDto {
    public MovieDto(Integer movieId, String title, String director, String studio, Set<String> movieCast, Integer releaseYear, String poster, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.studio = studio;
        this.movieCast = movieCast;
        this.releaseYear = releaseYear;
        this.poster = poster;
        this.posterUrl = posterUrl;
    }

    private Integer movieId;


    @NotBlank(message="Please provide movie's title!")
    private String title;

    @NotBlank(message="Please provide movie's director")//App level validation
    private String director;

    @NotBlank(message="Please provide movie's studio")
    private String studio;

    private Set<String> movieCast;


    private Integer releaseYear;

    @NotBlank(message="Please provide a movie's poster")
    private String poster;
    @NotBlank(message="Please provide poster's url")
    private String posterUrl;

    public MovieDto() {

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

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
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

    public String getPosterUrl() {
        return posterUrl;
    }
}
