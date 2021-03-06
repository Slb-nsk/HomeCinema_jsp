package org.homecinema.services;

import org.homecinema.dao.MoviesDao;
import org.homecinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MovieService implements iMovieService {
    private final MoviesDao dao;

    @Autowired
    public MovieService(MoviesDao dao) {
        this.dao = dao;
    }

    public ArrayList<String> getGenresList() {
        return dao.genresList();
    }

    public ArrayList<String> getcountriesList() {
        return dao.countriesList();
    }

    public ArrayList<ShortMovie> getFullCinemaList() {
        List<Movie> fullList = dao.cinemaList();
        ArrayList<ShortMovie> outputList = new ArrayList<>();
        for (Movie movie : fullList) {
            ShortMovie sm = new ShortMovie(movie.getMovieId(),
                    movie.getMovieRussianName(),
                    movie.getMovieOriginalName(),
                    movie.getSeriesAmount(),
                    movie.getMovieYear(),
                    movie.getImageUrl());
            outputList.add(sm);
        }
        return outputList;
    }

    public ExtendedMovie getCinemaById(Integer movieId, ArrayList<String> genres, ArrayList<String> countries) {
        Movie qm = dao.cinemaById(movieId);

        List<Integer> listGenres = dao.movieGenre(movieId);
        String movieGenres = "";
        if (!listGenres.isEmpty()) {
            if (listGenres.size() == 1) {
                movieGenres = firstUpperCase(genres.get(listGenres.get(0)));
            } else {
                StringBuilder result = new StringBuilder();
                for (Integer i : listGenres) {
                    result.append(genres.get(i)).append(", ");
                }
                movieGenres = firstUpperCase(result.substring(0, result.length() - 2)) + ".";
            }
        }

        List<Integer> listCountries = dao.movieCountry(movieId);
        String movieCountries = "";
        if (!listCountries.isEmpty()) {
            if (listCountries.size() == 1) {
                movieCountries = countries.get(listCountries.get(0));
            } else {
                StringBuilder result = new StringBuilder();
                for (Integer i : listCountries) {
                    result.append(countries.get(i)).append(", ");
                }
                movieCountries = result.substring(0, result.length() - 2) + ".";
            }
        }

        Integer seriesAmount = qm.getSeriesAmount();

        ArrayList<String> sourceUrl = new ArrayList<>();
        for (int i = 1; i <= seriesAmount; i++) {
            sourceUrl.add(dao.movieSource(movieId, i));
        }

        return new ExtendedMovie(movieId,
                qm.getMovieRussianName(),
                qm.getMovieOriginalName(),
                seriesAmount,
                qm.getMovieYear(),
                qm.getDescription(),
                movieCountries,
                movieGenres,
                qm.getImageUrl(),
                sourceUrl);

    }

    public void addMovie(String movieRussianName,
                         String movieOriginalName,
                         String movieYear,
                         String seriesAmount,
                         String description,
                         String movieCountries,
                         String movieGenres,
                         ArrayList<String> genres,
                         ArrayList<String> countries,
                         String imageUrl) {
        int seriesNumber;
        try {
            seriesNumber = Integer.parseInt(seriesAmount);
        } catch (NumberFormatException e) {
            seriesNumber = 1;
        }
        int year;
        try {
            year = Integer.parseInt(movieYear);
        } catch (NumberFormatException e) {
            year = 0;
        }
        Movie addingCinema = new Movie(movieRussianName,
                movieOriginalName,
                seriesNumber,
                year,
                description,
                imageUrl);
        dao.addNewCinema(addingCinema);
        Integer cinemaId = dao.cinemaByRussianName(movieRussianName);
        if (movieCountries.endsWith(".")) {
            movieCountries = movieCountries.substring(0, movieCountries.length() - 2);
        }
        ArrayList<String> countriesList = new ArrayList<>(Arrays.asList(movieCountries.split(",")));
        for (String c : countriesList) {
            if (!countries.contains(c.trim())) {
                CountryEntity newCountry = new CountryEntity();
                newCountry.setCountry(c);
                dao.addNewCountry(newCountry);
                countries = dao.countriesList();
            }
            dao.addMovieCountry(cinemaId, countries.indexOf(c.trim()));
        }
        if (movieGenres.endsWith(".")) {
            movieGenres = movieGenres.substring(0, movieGenres.length() - 2);
        }
        ArrayList<String> genresList = new ArrayList<>(Arrays.asList(movieGenres.split(",")));
        for (String g : genresList) {
            if (!genres.contains(g.trim().toLowerCase())) {
                GenreEntity newGenre = new GenreEntity();
                newGenre.setGenre(g);
                dao.addNewGenre(newGenre);
                genres = dao.genresList();
            }
            dao.addMovieGenre(cinemaId, genres.indexOf(g.trim().toLowerCase()));
        }
    }

    public void updateMovie(Integer movieId,
                            String movieRussianName,
                            String movieOriginalName,
                            String movieYear,
                            String seriesAmount,
                            String description,
                            String movieCountries,
                            String movieGenres,
                            ArrayList<String> genres,
                            ArrayList<String> countries,
                            String imageUrl) {
        int seriesNumber;
        try {
            seriesNumber = Integer.parseInt(seriesAmount);
        } catch (NumberFormatException e) {
            seriesNumber = 0;
        }
        int year;
        try {
            year = Integer.parseInt(movieYear.replaceAll(" ", ""));
        } catch (NumberFormatException e) {
            year = 0;
        }
        Movie updatingCinema = new Movie(movieId,
                movieRussianName,
                movieOriginalName,
                seriesNumber,
                year,
                description,
                imageUrl);
        dao.updateCinema(updatingCinema);

        ArrayList<Integer> oldList = new ArrayList<>(dao.movieCountry(movieId));
        if (movieCountries.isEmpty()) {
            for (Integer i : oldList) {
                dao.deleteMovieCountry(movieId, oldList.get(i));
            }
        } else {
            ArrayList<String> countriesList = new ArrayList<>(Arrays.asList(movieCountries.split(",")));
            ArrayList<Integer> newList = new ArrayList<>();
            for (String c : countriesList) {
                if (!countries.contains(c.trim())) {
                    CountryEntity newCountry = new CountryEntity();
                    newCountry.setCountry(c);
                    dao.addNewCountry(newCountry);
                    countries = dao.countriesList();
                }
                newList.add(countries.indexOf(c.trim()));
            }
            Collections.sort(oldList);
            Collections.sort(newList);
            for (int i = 0, j = 0; i < oldList.size() && j < newList.size(); ) {
                if (oldList.get(i).equals(newList.get(j))) {
                    i++;
                    j++;
                } else if (oldList.get(i) > newList.get(j)) {
                    dao.addMovieCountry(movieId, newList.get(j));
                    j++;
                } else {
                    dao.deleteMovieCountry(movieId, oldList.get(i));
                    i++;
                }
                if (i == oldList.size()) {
                    for (; j < newList.size(); j++) {
                        dao.addMovieCountry(movieId, newList.get(j));
                    }
                }
                if (j == newList.size()) {
                    for (; i < oldList.size(); i++) {
                        dao.deleteMovieCountry(movieId, oldList.get(i));
                    }
                }
            }


            oldList = new ArrayList<>(dao.movieGenre(movieId));
            if (movieGenres.isEmpty()) {
                for (Integer i : oldList) {
                    dao.deleteMovieGenre(movieId, oldList.get(i));
                }
            } else {
                ArrayList<String> genresList = new ArrayList<>(Arrays.asList(movieGenres.split(",")));
                newList = new ArrayList<>();
                for (String g : genresList) {
                    if (!genres.contains(g.trim().toLowerCase())) {
                        GenreEntity newGenre = new GenreEntity();
                        newGenre.setGenre(g);
                        dao.addNewGenre(newGenre);
                        genres = dao.genresList();
                    }
                    newList.add(genres.indexOf(g.trim().toLowerCase()));
                }
                Collections.sort(oldList);
                Collections.sort(newList);

                for (int i = 0, j = 0; i < oldList.size() && j < newList.size(); ) {
                    if (oldList.get(i).equals(newList.get(j))) {
                        i++;
                        j++;
                    } else if (oldList.get(i) > newList.get(j)) {
                        dao.addMovieGenre(movieId, newList.get(j));
                        j++;
                    } else {
                        dao.deleteMovieGenre(movieId, oldList.get(i));
                        i++;
                    }
                    if (i == oldList.size()) {
                        for (; j < newList.size(); j++) {
                            dao.addMovieGenre(movieId, newList.get(j));
                        }
                    }
                    if (j == newList.size()) {
                        for (; i < oldList.size(); i++) {
                            dao.deleteMovieGenre(movieId, oldList.get(i));
                        }
                    }
                }
            }
        }
    }

    public void changeUrl(Integer movieId, ArrayList<String> seriesUrl) {
        int seriesAmount = seriesUrl.size() - 1;
        String url;
        for (int i = 1; i <= seriesAmount; i++) {
            url = dao.movieSource(movieId, i);
            if (!seriesUrl.get(i).equals(url)) {
                if (url.isEmpty()) {
                    dao.addCinemaUrl(movieId, i, seriesUrl.get(i));
                } else if (!seriesUrl.get(i).isEmpty()) {
                    dao.updateCinemaUrl(movieId, i, seriesUrl.get(i));
                } else {
                    dao.deleteCinemaUrl(movieId, i);
                }
            }
        }
    }


    public void deleteMovie(Integer movieId) {
        Movie movie = dao.cinemaById(movieId);
        List<Integer> genres = dao.movieGenre(movieId);
        for (Integer i : genres) {
            dao.deleteMovieGenre(movieId, i);
        }
        List<Integer> countries = dao.movieCountry(movieId);
        for (Integer i : countries) {
            dao.deleteMovieCountry(movieId, i);
        }
        for (int i = 1; i <= movie.getSeriesAmount(); i++) {
            dao.deleteCinemaUrl(movieId, i);
        }
        dao.deleteMovie(movieId);
    }


    public ArrayList<ShortMovie> doSearch(String kind, String country, String genre) {
        String sql = "SELECT m.movieId, m.movieRussianName, m.movieOriginalName, m.seriesAmount, m.movieYear, m.imageUrl, m.description FROM movies m ";
        if (!country.equals("Any")) {
            sql += "JOIN moviecountries mc ON m.movieId = mc.movieId ";
            sql += "JOIN countries c ON mc.countryId = c.countryId AND c.country = '";
            sql += country;
            sql += "' ";
        }
        if (!genre.equals("Any")) {
            sql += "JOIN moviegenres mg ON m.movieId = mg.movieId ";
            sql += "JOIN genres g ON mg.genreId = g.genreId AND g.genre = '";
            sql += genre;
            sql += "' ";
        }
        if (!kind.equals("Any")){
            if (kind.equals("Mov")) {
                sql += "WHERE m.seriesamount = '1' ";
            }
            if (kind.equals("Ser")) {
                sql += "WHERE m.seriesamount > '1' ";
            }
        }

        sql += ";";

        List<Movie> fullList = dao.foundCinema(sql);
        ArrayList<ShortMovie> outputList = new ArrayList<>();
        for (Movie movie : fullList) {
            ShortMovie sm = new ShortMovie(movie.getMovieId(),
                    movie.getMovieRussianName(),
                    movie.getMovieOriginalName(),
                    movie.getSeriesAmount(),
                    movie.getMovieYear(),
                    movie.getImageUrl());
            outputList.add(sm);
        }
        return outputList;
    }



    private String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

}
