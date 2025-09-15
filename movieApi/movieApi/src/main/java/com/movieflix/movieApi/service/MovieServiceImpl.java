package com.movieflix.movieApi.service;

import com.movieflix.movieApi.dto.MovieDto;
import com.movieflix.movieApi.dto.MoviePageResponse;
import com.movieflix.movieApi.entities.Movie;
import com.movieflix.movieApi.exceptions.FileExistsException;
import com.movieflix.movieApi.exceptions.MovieNotFoundException;
import com.movieflix.movieApi.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class MovieServiceImpl implements MovieService{
    private final MovieRepository movieRepository;

    private final FileService fileService;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService=fileService;
    }
    @Value("${poster.upload-dir}")
    private String path;
    @Value("${base.url}")
    private String baseUrl;


    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        if(Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename())))
        {
            throw new FileExistsException("file already exists! Please enter another file name!");
        }
        String uploadedFileName=fileService.uploadFile(path,file);
        movieDto.setPoster(uploadedFileName);
        //map dto to Movie object
        Movie movie=new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()

        );
        Movie savedMovie=movieRepository.save(movie);
        String posterUrl=baseUrl+"/file/"+uploadedFileName;
        return new MovieDto(
              savedMovie.getMovieId(),
              savedMovie.getTitle(),
              savedMovie.getDirector(),
              savedMovie.getStudio(),
              savedMovie.getMovieCast(),
              savedMovie.getReleaseYear(),
              savedMovie.getPoster(),
              posterUrl

        );

    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        Movie movie=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie not found!"));
        String posterUrl=baseUrl+"/file/"+movie.getPoster();
        return new MovieDto(movie.getMovieId(),
                            movie.getTitle(),
                            movie.getDirector(),
                            movie.getStudio(),
                            movie.getMovieCast(),
                            movie.getReleaseYear(),
                            movie.getPoster(),
                            posterUrl);
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie>movies=movieRepository.findAll();
        List<MovieDto>movieDtos=new ArrayList<>();
        for(Movie movie:movies)
        {
            String posterUrl=baseUrl+"/file/"+movie.getPoster();
            MovieDto movieDto= new MovieDto(movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl);
            movieDtos.add(movieDto);


        }
        return movieDtos;
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException {
        Movie movie=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie not found"));
        String fileName=movie.getPoster();
        String filePath=path+ File.separator+fileName;
        if(file!=null)
        {
            Files.deleteIfExists(Paths.get(filePath));
            fileName=fileService.uploadFile(path,file);

        }
        movieDto.setPoster(fileName);
        Movie updatedMovie=new Movie(
                movieId,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()

        );
        movieRepository.save(updatedMovie);
        String posterUrl=baseUrl+"/file/"+fileName;
        //movieDto.setPosterUrl(fileName);
        return new MovieDto(
                updatedMovie.getMovieId(),
                updatedMovie.getTitle(),
                updatedMovie.getDirector(),
                updatedMovie.getStudio(),
                updatedMovie.getMovieCast(),
                updatedMovie.getReleaseYear(),
                updatedMovie.getPoster(),
                posterUrl

        );






    }

    @Override
    public String deleteMovie(Integer movieId)throws IOException {
        Movie dm=movieRepository.findById(movieId).orElseThrow(()->new MovieNotFoundException("Movie not found"));
        Integer id= dm.getMovieId();
        String filePath=path+ File.separator+dm.getPoster();
        Files.deleteIfExists(Paths.get(filePath));
        movieRepository.delete(dm);

        return "Movie with id "+id+" Deleted successfully";
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable= PageRequest.of(pageNumber,pageSize);//select * from movies limit 5 offset 0
        Page<Movie>moviePages=movieRepository.findAll(pageable);
        List<Movie> movies=moviePages.getContent();
        List<MovieDto>movieDtos=new ArrayList<>();
               for(Movie movie:movies)
        {
            String posterUrl=baseUrl+"/file/"+movie.getPoster();
            MovieDto movieDto= new MovieDto(movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl);
            movieDtos.add(movieDto);


        }
        //return movieDtos;

        return new MoviePageResponse(
                movieDtos,
                pageNumber,
                pageSize,
                moviePages.getTotalElements(), // long
                moviePages.getTotalPages(),    // int
                moviePages.isLast()
        );

    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {
        Sort sort=dir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);//select * from movies limit 5 offset 0
        Page<Movie>moviePages=movieRepository.findAll(pageable);
        List<Movie> movies=moviePages.getContent();
        List<MovieDto>movieDtos=new ArrayList<>();
        for(Movie movie:movies)
        {
            String posterUrl=baseUrl+"/file/"+movie.getPoster();
            MovieDto movieDto= new MovieDto(movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl);
            movieDtos.add(movieDto);


        }
        //return movieDtos;

        return new MoviePageResponse(
                movieDtos,
                pageNumber,
                pageSize,
                moviePages.getTotalElements(),
                moviePages.getTotalPages(),
                moviePages.isLast()
        );
    }
}
