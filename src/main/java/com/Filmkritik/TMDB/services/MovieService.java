package com.Filmkritik.TMDB.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Filmkritik.authservice.entities.UserMovieMap;
import com.Filmkritik.authservice.entities.UserTVMap;
import com.Filmkritik.authservice.repository.UserMovieMapRepository;
import com.Filmkritik.authservice.repository.UserTVMapRepository;
import com.Filmkritik.authservice.service.AuthService;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.enumeration.ExternalSource;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.model.tv.TVInfo;
import com.omertron.themoviedbapi.results.ResultList;

@Service
public class MovieService{
	private static final Logger logger = Logger.getLogger(MovieService.class);

	@Autowired
	private TheMovieDbApi movieDB;
	
	@Autowired
	private UserMovieMapRepository userMovieMapRepo;

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


	public List<MovieInfo> getLikedMovie(long userId) throws MovieDbException{
		List<Long> movieIdList = userMovieMapRepo.getLikedMovieByUser(userId);
		List<MovieInfo> movieInfo = new ArrayList<MovieInfo>();
		for (Long id : movieIdList) {
			movieInfo.add( movieDB.getMovieInfo(id.intValue(), "en-US", "")); ;
		}
		return movieInfo;
	}

	public String addLikedMovie(long userId, long movieId) {
		// TODO Auto-generated method stub
		UserMovieMap newEntry = new UserMovieMap();
		newEntry.setUid(userId);
		newEntry.setMid(movieId);
		userMovieMapRepo.save(newEntry);
		return "Success";
	}
}
