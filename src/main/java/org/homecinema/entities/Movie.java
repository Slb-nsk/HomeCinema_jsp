package org.homecinema.entities;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private Integer movieId;

    @Column(name = "movieRussianName")
    private String movieRussianName;

    @Column(name = "movieOriginalName")
    private String movieOriginalName;

    @Column(name = "seriesAmount")
    private Integer seriesAmount;

    @Column(name = "movieYear")
    private Integer movieYear;

    @Column(name = "description")
    private String description;

    @Column(name = "imageUrl")
    private String imageUrl;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieRussianName() {
        return movieRussianName;
    }

    public void setMovieRussianName(String movieRussianName) {
        this.movieRussianName = movieRussianName.trim();
    }

    public String getMovieOriginalName() {
        return movieOriginalName;
    }

    public void setMovieOriginalName(String movieOriginalName) {
        this.movieOriginalName = movieOriginalName.trim();
    }

    public Integer getSeriesAmount() {
        return seriesAmount;
    }

    public void setSeriesAmount(Integer seriesAmount) {
        this.seriesAmount = seriesAmount;
    }

    public Integer getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Integer movieYear) {
        this.movieYear = movieYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Movie() {
    }

    public Movie(String movieRussianName, String movieOriginalName, Integer seriesAmount, Integer movieYear, String description, String imageUrl) {
        this.movieRussianName = movieRussianName.trim();
        this.movieOriginalName = movieOriginalName.trim();
        this.seriesAmount = seriesAmount;
        this.movieYear = movieYear;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Movie(Integer movieId, String movieRussianName, String movieOriginalName, Integer seriesAmount, Integer movieYear, String description) {
        this.movieId = movieId;
        this.movieRussianName = movieRussianName.trim();
        this.movieOriginalName = movieOriginalName.trim();
        this.seriesAmount = seriesAmount;
        this.movieYear = movieYear;
        this.description = description;
    }

    public Movie(Integer movieId, String movieRussianName, String movieOriginalName, Integer seriesAmount, Integer movieYear, String description, String imageUrl) {
        this.movieId = movieId;
        this.movieRussianName = movieRussianName.trim();
        this.movieOriginalName = movieOriginalName.trim();
        this.seriesAmount = seriesAmount;
        this.movieYear = movieYear;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Номер фильма в базе: " + movieId + "; " +
                "Название на русском: " + movieRussianName + "; " +
                "Оригинальное название: " + movieOriginalName + "; " +
                "Количество серий: " + seriesAmount + "; " +
                "Год выхода на экраны: " + movieYear + "; " +
                "Описание: " + description + ";" +
                "URL картинки:" + imageUrl + ".";
    }
}

