package com.Filmkritik.TMDB.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Filmkritik.TMDB.services.MovieService;
import com.Filmkritik.TMDB.services.TvService;
import com.Filmkritik.authservice.controller.AuthenticationController;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.tv.TVBasic;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.ResultList;


@RestController
@CrossOrigin
public class MovieTVController {
	private static final Logger logger = Logger.getLogger(MovieTVController.class);
	
	@Autowired
	private MovieService movieService;
	@Autowired
	private TvService tvService;

	@GetMapping(value="/movie/topRatedMovies")
	public ResultList<MovieInfo> getTopRatedMovies() throws MovieDbException{
		return movieService.getTopRatedMovies();
	}
	
	@GetMapping(value="/movie/popularMovies")
	public ResultList<MovieInfo> getPopularMovies() throws MovieDbException{
		return movieService.getPopularMovies();
	}
	@GetMapping(value="/movie/upcomingMovies")
	public ResultList<MovieInfo> getUpcomingMovies() throws MovieDbException{
		return movieService.getUpcomingMovies();
	}
	@GetMapping(value="/movie/nowShowing")
	public ResultList<MovieInfo> getNowShowing() throws MovieDbException{
		return movieService.getNowShowing();
	}
	
	
	@GetMapping(value="/tv/topRatedTV")
	public ResultList<TVInfo> getTVTopRated() throws MovieDbException{
		return tvService.getTVTopRated();
	}
	
	@GetMapping(value="/tv/popularTV")
	public ResultList<TVInfo> getTVPopular() throws MovieDbException{
		return tvService.getTVPopular();
	}
	@GetMapping(value="/tv/latestTV")
	public TVInfo getLatestTV() throws MovieDbException{
		return tvService.getLatestTV();
	}
	@GetMapping(value="/tv/airingToday")
	public ResultList<TVInfo> getTVAiringToday() throws MovieDbException{
		return tvService.getTVAiringToday();
	}
	
	@GetMapping(value="/tv/onTheAir")
	public ResultList<TVInfo> getOnTheAir() throws MovieDbException{
		return tvService.getOnTheAir();
	}
	
	@GetMapping(value="/getGenre")
	public ResultList<Genre> getGenre() throws MovieDbException{
		return movieService.getGenre();
	}
	
	@GetMapping(value = "/getLikedTVShowsByUser")
	public List<TVInfo> getLikedTV(@RequestParam long userId) throws MovieDbException{
		return tvService.getLikedTV(userId);
	}
	
	@GetMapping(value = "/getLikedMovieByUser")
	public List<MovieInfo> getLikedMovie(@RequestParam long userId) throws MovieDbException{
		return movieService.getLikedMovie(userId);
	}
	
	@PostMapping(value = "/addLikedTVShowsByUser")
	public ResponseEntity<String> addLikedTV(@RequestParam long userId, @RequestParam long tvId) throws MovieDbException{
		return ResponseEntity.ok(tvService.addLikedTV(userId,tvId));
	}
	
	@PostMapping(value = "/addLikedMovieByUser")
	public ResponseEntity<String> addLikedMovie(@RequestParam long userId, @RequestParam long movieId) throws MovieDbException{
		return ResponseEntity.ok(movieService.addLikedMovie(userId,movieId)) ;
	}
	
}
