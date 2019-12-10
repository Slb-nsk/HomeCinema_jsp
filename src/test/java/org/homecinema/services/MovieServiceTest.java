package org.homecinema.services;

import org.homecinema.dao.MoviesDao;
import org.homecinema.entities.ExtendedMovie;
import org.homecinema.entities.Movie;
import org.homecinema.entities.ShortMovie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;

public class MovieServiceTest {
    private final MoviesDao dao = Mockito.mock(MoviesDao.class);
    private final MovieService movieService = new MovieService(dao);

    @Test
    public void getFullCinemaList_CorrectTransformingOfList() {
        //given
        Movie movie1 = new Movie(1, "фильм1", "movie1", 1, 1971, "описание", "http://example.com");
        List<Movie> testList = new ArrayList<Movie>();
        testList.add(movie1);

        Mockito.doReturn(testList).when(dao).cinemaList();

        //when
        ArrayList<ShortMovie> outputList = movieService.getFullCinemaList();

        //then
        assertFalse(outputList.isEmpty());
        assertEquals(testList.get(0).getMovieId(), outputList.get(0).getMovieId());
        assertEquals(testList.get(0).getMovieYear(), outputList.get(0).getMovieYear());
        assertEquals(testList.get(0).getSeriesAmount(), outputList.get(0).getSeriesAmount());
        assertEquals(testList.get(0).getMovieOriginalName(), outputList.get(0).getMovieOriginalName());
        assertEquals(testList.get(0).getMovieRussianName(), outputList.get(0).getMovieRussianName());

        Mockito.verify(dao).cinemaList();
    }

    @Test
    public void getCinemaById_caseUsualMovie() {
        //given
        ArrayList<String> genres = new ArrayList<>();
        genres.add(0, "");
        genres.add(1, "боевик");
        genres.add(2, "драма");
        ArrayList<String> countries = new ArrayList<>();
        countries.add(0, "");
        countries.add(1, "КНР");
        countries.add(2, "США");

        List<Integer> listGenres = Arrays.asList(1);

        ArrayList<String> outputGenres = new ArrayList<>();
        outputGenres.add(0, "");
        outputGenres.add(1, genres.get(1));

        List<Integer> listCountries = Arrays.asList(1);

        ArrayList<String> outputCountries = new ArrayList<>();
        outputCountries.add(0, "");
        outputCountries.add(1, countries.get(1));

        ArrayList<String> sourceUrl = new ArrayList<>();

        ExtendedMovie movie1 = new ExtendedMovie(1,
                "фильм1",
                "movie1",
                1,
                1971,
                "описание",
                "",
                "",
                "http://example.com",
                sourceUrl);

        Mockito.doReturn(movie1).when(dao).cinemaById(movie1.getMovieId());
        Mockito.doReturn(listGenres).when(dao).movieGenre(movie1.getMovieId());
        Mockito.doReturn(listCountries).when(dao).movieCountry(movie1.getMovieId());

        //when
        ExtendedMovie outputMovie = movieService.getCinemaById(movie1.getMovieId(), genres, countries);

        //then
        assertNotNull(outputMovie);
        assertEquals(outputMovie.getMovieId(), movie1.getMovieId());
        assertEquals(outputMovie.getMovieYear(), movie1.getMovieYear());
        assertEquals(outputMovie.getSeriesAmount(), movie1.getSeriesAmount());
        assertEquals(outputMovie.getMovieOriginalName(), movie1.getMovieOriginalName());
        assertEquals(outputMovie.getMovieRussianName(), movie1.getMovieRussianName());
//        for (int i = 0; i < outputMovie.getGenres().size(); i++) {
//            assertTrue(outputMovie.getGenres().get(i).equals(outputGenres.get(i + 1)));
//        }
//        for (int i = 0; i < outputMovie.getCountries().size(); i++) {
//            assertTrue(outputMovie.getCountries().get(i).equals(outputCountries.get(i + 1)));
//        }

        Mockito.verify(dao).cinemaById(anyInt());
        Mockito.verify(dao).movieGenre(anyInt());
        Mockito.verify(dao).movieCountry(anyInt());
    }

    @Test
    public void getCinemaById_incompleteData() {
        //given
        ArrayList<String> genres = new ArrayList<>();
        genres.add(0, "");
        genres.add(1, "боевик");
        genres.add(2, "драма");
        ArrayList<String> countries = new ArrayList<>();
        countries.add(0, "");
        countries.add(1, "КНР");
        countries.add(2, "США");

        List<Integer> listGenres = Collections.emptyList();
        List<Integer> listCountries = Collections.emptyList();
        ArrayList<String> sourceUrl = new ArrayList<>();

        ExtendedMovie movie1 = new ExtendedMovie(1,
                "фильм1",
                "movie1",
                1,
                1971,
                "описание",
                "",
                "",
                "http://example.com",
                sourceUrl);
        Mockito.doReturn(movie1).when(dao).cinemaById(movie1.getMovieId());
        Mockito.doReturn(listGenres).when(dao).movieGenre(movie1.getMovieId());
        Mockito.doReturn(listCountries).when(dao).movieCountry(movie1.getMovieId());

        //when
        ExtendedMovie outputMovie = movieService.getCinemaById(movie1.getMovieId(), genres, countries);

        //then
        assertNotNull(outputMovie);
        assertEquals(outputMovie.getMovieId(), movie1.getMovieId());
        assertEquals(outputMovie.getMovieYear(), movie1.getMovieYear());
        assertEquals(outputMovie.getSeriesAmount(), movie1.getSeriesAmount());
        assertEquals(outputMovie.getMovieOriginalName(), movie1.getMovieOriginalName());
        assertEquals(outputMovie.getMovieRussianName(), movie1.getMovieRussianName());
        assertEquals(outputMovie.getGenres(), "");
        assertEquals(outputMovie.getCountries(), "");

        Mockito.verify(dao).cinemaById(anyInt());
        Mockito.verify(dao).movieGenre(anyInt());
        Mockito.verify(dao).movieCountry(anyInt());

    }

    @Test
    public void updateMovie_noSeries() {
        //given
        ArrayList<String> genres = new ArrayList<>();
        ArrayList<String> countries = new ArrayList<>();
        ArgumentCaptor<Movie> valueCapture = ArgumentCaptor.forClass(Movie.class);
        doNothing().when(dao).updateCinema(valueCapture.capture());

        //when
        movieService.updateMovie(0,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                genres,
                countries,
                "");

        //then
        assertEquals(valueCapture.getValue().getSeriesAmount().intValue(), 0);

    }

    @Test
    public void updateMovie_noYear() {
        //given
        ArrayList<String> genres = new ArrayList<>();
        ArrayList<String> countries = new ArrayList<>();
        ArgumentCaptor<Movie> valueCapture = ArgumentCaptor.forClass(Movie.class);
        doNothing().when(dao).updateCinema(valueCapture.capture());

        //when
        movieService.updateMovie(0,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                genres,
                countries,
                "");

        //then
        assertEquals(valueCapture.getValue().getMovieYear().intValue(), 0);

    }

    @Test
    public void updateMovie_stringToList() {
        //given
        ArrayList<String> genres = new ArrayList<>();
        genres.add("боевик");
        genres.add("драма");
        ArrayList<String> countries = new ArrayList<>();
        countries.add("КНР");
        countries.add("США");
        ArgumentCaptor<Movie> valueCapture = ArgumentCaptor.forClass(Movie.class);
        doNothing().when(dao).updateCinema(valueCapture.capture());

        //when
        movieService.updateMovie(0,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                genres,
                countries,
                "");

        //then
        assertEquals(valueCapture.getValue().getMovieYear().intValue(), 0);

    }

}
