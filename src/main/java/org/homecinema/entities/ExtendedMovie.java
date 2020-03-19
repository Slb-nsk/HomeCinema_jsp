package org.homecinema.entities;

import java.util.ArrayList;

public class ExtendedMovie extends Movie {
    private String countries;
    private String genres;
    private ArrayList<String> sourceUrl;

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public ArrayList<String> getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(ArrayList<String> sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public ExtendedMovie(Integer movieId,
                         String movieRussianName,
                         String movieOriginalName,
                         Integer seriesAmount,
                         Integer movieYear,
                         String description,
                         String countries,
                         String genres,
                         String imageUrl,
                         ArrayList<String> sourceUrl) {
        super(movieId, movieRussianName, movieOriginalName, seriesAmount, movieYear, description, imageUrl);
        this.countries = countries;
        this.genres = genres;
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String toString() {
        return "Номер фильма в базе: " + getMovieId() + "; " +
                "Название на русском: " + getMovieRussianName() + "; " +
                "Оригинальное название: " + getMovieOriginalName() + "; " +
                "Количество серий: " + getSeriesAmount() + "; " +
                "Жанр(ы): " + getGenres()  + "; " +
                "Год выхода на экраны: " + getMovieYear() + "; " +
                "Страны: " + getCountries()  + "; " +
                "Описание: " + this.getDescription() + ";" +
                "URL картинки: " +getImageUrl() + ";" +
                "URL файла: " + getSourceUrl() + ".";
    }
}

