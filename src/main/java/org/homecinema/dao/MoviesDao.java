package org.homecinema.dao;

import org.homecinema.entities.CountryEntity;
import org.homecinema.entities.GenreEntity;
import org.homecinema.entities.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MoviesDao {

    @PersistenceContext
    private EntityManager em;


    @Autowired
    public MoviesDao(EntityManager em) {
        this.em = em;
    }

    //список всех фильмов в базе
    public List<Movie> cinemaList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cinemaCriteria = cb.createQuery(Movie.class);
        Root<Movie> cinemaRoot = cinemaCriteria.from(Movie.class);
        cinemaCriteria.select(cinemaRoot);
        return em.createQuery(cinemaCriteria).getResultList();
    }

    //список всех жанров в базе
    public ArrayList<String> genresList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GenreEntity> genreCriteria = cb.createQuery(GenreEntity.class);
        Root<GenreEntity> genreRoot = genreCriteria.from(GenreEntity.class);
        genreCriteria.select(genreRoot);
        List<GenreEntity> resultList = em.createQuery(genreCriteria).getResultList();

        ArrayList<String> outputList = new ArrayList<>();
        outputList.add(0, "");
        for (GenreEntity g : resultList) {
            while (outputList.size() < g.getGenreId()) {
                outputList.add("");
            }
            outputList.add(g.getGenreId(), g.getGenre());
        }
        return outputList;
    }

    //список всех стран в базе
    public ArrayList<String> countriesList() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CountryEntity> countryCriteria = cb.createQuery(CountryEntity.class);
        Root<CountryEntity> countryRoot = countryCriteria.from(CountryEntity.class);
        countryCriteria.select(countryRoot);
        List<CountryEntity> resultList = em.createQuery(countryCriteria).getResultList();

        ArrayList<String> outputList = new ArrayList<>();
        outputList.add(0, "");

        for (CountryEntity c : resultList) {
            while (outputList.size() < c.getCountryId()) {
                outputList.add("");
            }
            outputList.add(c.getCountryId(), c.getCountry());
        }
        return outputList;
    }

    //конкретный фильм в таблице Movies
    public Movie cinemaById(int id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> movieCriteria = cb.createQuery(Movie.class);
        Root<Movie> movieRoot = movieCriteria.from(Movie.class);
        movieCriteria.select(movieRoot);
        movieCriteria.where(cb.equal(movieRoot.get("movieId"), id));
        return em.createQuery(movieCriteria).getSingleResult();
    }

    //Id конкретного фильма в таблице Movies по его русскому названию
    public Integer cinemaByRussianName(String movieRussianName) {
        Query q = em.createNativeQuery("SELECT movieId FROM movies WHERE movieRussianName=?")
                .setParameter(1, movieRussianName);
        return (Integer) q.getSingleResult();
    }

    //конкретный жанр в таблице Genres
    public String genreById(int genreId) {
        Query q = em.createNativeQuery("SELECT genre FROM genres WHERE genreId=?")
                .setParameter(1, genreId);
        return (String) q.getSingleResult();
    }

    //Id конкретного жанра в таблице Genres
    public Integer genreByName(String genre) {
        Query q = em.createNativeQuery("SELECT genreId FROM genres WHERE genre=?")
                .setParameter(1, genre);
        return (Integer) q.getSingleResult();
    }

    //конкретная страна в таблице Countries
    public String countryById(int countryId) {
        Query q = em.createNativeQuery("SELECT country FROM countries WHERE countryId=?")
                .setParameter(1, countryId);
        return (String) q.getSingleResult();
    }

    //Id конкретной страны в таблице Countries
    public Integer countryByName(String country) {
        Query q = em.createNativeQuery("SELECT countryId FROM countries WHERE country=?")
                .setParameter(1, country);
        return (Integer) q.getSingleResult();
    }

    //коды жанров, к которым относится конкретный фильм
    public List<Integer> movieGenre(int movieId) {
        Query q = em.createNativeQuery("SELECT genreId FROM moviegenres WHERE movieId=?")
                .setParameter(1, movieId);
        return q.getResultList();
    }

    //коды стран, к которым относится конкретный фильм
    public List<Integer> movieCountry(int movieId) {
        Query q = em.createNativeQuery("SELECT countryId FROM moviecountries WHERE movieId=?")
                .setParameter(1, movieId);
        return q.getResultList();
    }

    //url места в интернете, где находится конкретный фильм
    public String movieSource(int movieId, int seriaNumber) {
        Query q = em.createNativeQuery("SELECT fileUrl FROM series WHERE movieId=? AND seriaNumber=?")
                .setParameter(1, movieId).setParameter(2, seriaNumber);
        try {
            return (String) q.getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
    }

    //добавление нового фильма/сериала
    public void addNewCinema(Movie newMovie) {
        em.persist(newMovie);
    }

    //добавление нового жанра
    public void addNewGenre(GenreEntity newGenre) {
        em.persist(newGenre);
    }

    //добавление новой страны
    public void addNewCountry(CountryEntity newCountry) {
        em.persist(newCountry);
    }

    //добавление страны фильма/сериала
    public void addMovieCountry(Integer movieId, Integer countryId) {
        Query q = em.createNativeQuery("INSERT INTO moviecountries (movieId, countryId)"
                + "VALUES (?,?)")
                .setParameter(1, movieId)
                .setParameter(2, countryId);
        q.executeUpdate();
    }

    //добавление жанра фильма/сериала
    public void addMovieGenre(Integer movieId, Integer genreId) {
        Query q = em.createNativeQuery("INSERT INTO moviegenres (movieId, genreId)"
                + "VALUES (?,?)")
                .setParameter(1, movieId)
                .setParameter(2, genreId);
        q.executeUpdate();
    }

    //добавление места в интернете, где находится фильм/сериал
    public void addCinemaUrl(Integer movieId, Integer seriaNumber, String fileUrl) {
        Query q = em.createNativeQuery("INSERT INTO series (movieId, seriaNumber, fileUrl)"
                + "VALUES (?,?,?)")
                .setParameter(1, movieId)
                .setParameter(2, seriaNumber)
                .setParameter(3, fileUrl);
        q.executeUpdate();
    }

    //обновление данных о месте в интернете, где находится фильм/сериал
    public void updateCinemaUrl(Integer movieId, Integer seriaNumber, String fileUrl) {
        Query q = em.createNativeQuery("UPDATE series SET fileUrl=:fileUrl WHERE movieId = :movieId AND seriaNumber = :seriaNumber")
                .setParameter("movieId", movieId)
                .setParameter("seriaNumber", seriaNumber)
                .setParameter("fileUrl", fileUrl);
        q.executeUpdate();
    }

    //очистка данных о месте в интернете, где находится фильм/сериал
    public void deleteCinemaUrl(Integer movieId, Integer seriaNumber) {
        Query query = em.createNativeQuery("DELETE FROM series WHERE movieId=:movieId AND seriaNumber = :seriaNumber")
                .setParameter("movieId", movieId)
                .setParameter("seriaNumber", seriaNumber);
        query.executeUpdate();
    }

    //обновление данных о фильме/сериале
    public void updateCinema(Movie newMovie) {
        em.merge(newMovie);
    }

    //удаление фильма/сериала из базы
    public void deleteMovie(Integer movieId) {
        Movie movie = cinemaById(movieId);
        em.remove(movie);
    }

    //удаление жанра фильма
    public void deleteMovieGenre(Integer movieId, Integer genreId) {
        Query query = em.createNativeQuery("DELETE FROM moviegenres WHERE movieId=:movieId AND genreId = :genreId")
                .setParameter("movieId", movieId)
                .setParameter("genreId", genreId);
        query.executeUpdate();
    }

    //удаление страны фильма
    public void deleteMovieCountry(Integer movieId, Integer countryId) {
        Query query = em.createNativeQuery("DELETE FROM moviecountries WHERE movieId=:movieId AND countryId = :countryId")
                .setParameter("movieId", movieId)
                .setParameter("countryId", countryId);
        query.executeUpdate();
    }

    //поиск фильма/сериала
    public List<Movie> foundCinema(String sql) {
        return em.createNativeQuery(sql, Movie.class).getResultList();
    }
}

