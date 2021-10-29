package com.Filmkritik.TMDB.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Filmkritik.authservice.service.AuthService;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

@Service
public class MovieService {
	private static final Logger logger = Logger.getLogger(MovieService.class);

	@Autowired
	private TheMovieDbApi movieDB;

	public ResultList<MovieInfo> getTopRatedMovies() throws MovieDbException {
		return movieDB.getTopRatedMovies(1, "en-US");
	}
	
	public ResultList<MovieInfo> getPopularMovies() throws MovieDbException{
		return movieDB.getPopularMovieList(1, "en-US");
	}
	
	public ResultList<MovieInfo> getUpcomingMovies() throws MovieDbException{
		return movieDB.getUpcoming(1, "en-US");
	}
	
	public ResultList<MovieInfo> getNowShowing() throws MovieDbException{
		return movieDB.getNowPlayingMovies(1, "en-US");
	}

	public ResultList<Genre> getGenre() throws MovieDbException {
		// TODO Auto-generated method stub
		return movieDB.getGenreMovieList("en-US");
	}

}
