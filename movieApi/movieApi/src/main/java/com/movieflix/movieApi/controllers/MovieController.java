package com.movieflix.movieApi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.movieApi.dto.MovieDto;
import com.movieflix.movieApi.dto.MoviePageResponse;
import com.movieflix.movieApi.exceptions.EmptyFileException;
import com.movieflix.movieApi.service.MovieService;
import com.movieflix.movieApi.utils.AppConstants;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException, EmptyFileException {
        if(file.isEmpty())
        {
           throw new EmptyFileException("File is empty! please send another file!");
        }
        MovieDto movieDto1=convertoMovieDto(movieDto);
        return new ResponseEntity<>(movieService.addMovie(movieDto1,file), HttpStatus.CREATED);

    }
    private MovieDto convertoMovieDto(String movieDtoObj) throws JsonProcessingException {

        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(movieDtoObj, MovieDto.class);


    }
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Integer movieId)
    {
       return ResponseEntity.ok(movieService.getMovie(movieId));
    }
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMovies()
    {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MovieDto> updateMovie(@RequestPart String movieDto,@RequestPart MultipartFile file,@PathVariable Integer id) throws IOException {
        MovieDto movieDto1=convertoMovieDto(movieDto);

        if(file.isEmpty())file=null;
        return ResponseEntity.ok(movieService.updateMovie(id,movieDto1,file));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> DeleteMovie(@PathVariable Integer id) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(id));
    }
    @GetMapping("/allMoviesPage")
    public ResponseEntity<MoviePageResponse>getAllMoviesPage(@RequestParam (defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,@RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize)
    {
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber,pageSize));

    }
    @GetMapping("/allMoviesPageSort")
    public ResponseEntity<MoviePageResponse>getAllMoviesPageSorted(
            @RequestParam (defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR,required = false)String sortDir)
    {
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber,pageSize,sortBy,sortDir));

    }
}
