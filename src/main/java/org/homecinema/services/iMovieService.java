package org.homecinema.services;

import org.homecinema.entities.ExtendedMovie;
import org.homecinema.entities.ShortMovie;

import java.util.ArrayList;

public interface iMovieService {

    /**
     * @return Список всех жанров из базы
     */
    ArrayList<String> getGenresList();

    /**
     * @return Список всех стран из базы
     */
    ArrayList<String> getcountriesList();

    /**
     * @return Список всех фильмов из базы в краткой форме
     */
    ArrayList<ShortMovie> getFullCinemaList();

    /**
     * @param movieId   Номер фильма в базе
     * @param genres    Текущий список имеющихся жанров (для удобства составления списка жанров фильма)
     * @param countries Текущий список стран (для удобства сопоставления стран фильму)
     * @return Полные данные по конкретному фильму
     */
    ExtendedMovie getCinemaById(Integer movieId,
                                ArrayList<String> genres,
                                ArrayList<String> countries);

    /**
     * @param movieRussianName  название добавляемой киноработы на русском
     * @param movieOriginalName оригинальное название добавляемой киноработы
     * @param movieYear         год выхода добавляемой киноработы
     * @param seriesAmount      количество серий в добавляемой киноработе
     * @param description       описание добавляемой киноработы
     * @param movieCountries    страны-производители киноработы
     * @param movieGenres       жанры киноработы
     * @param genres            Текущий список имеющихся жанров (для удобства обновления общего списка жанров)
     * @param countries         Текущий список стран (для удобства обновления общего списка стран)
     * @param imageUrl          URL картинки
     */
    void addMovie(String movieRussianName,
                  String movieOriginalName,
                  String movieYear,
                  String seriesAmount,
                  String description,
                  String movieCountries,
                  String movieGenres,
                  ArrayList<String> genres,
                  ArrayList<String> countries,
                  String imageUrl);

    /**
     * @param movieId           номер исправляемой киноработы на русском
     * @param movieRussianName  название исправляемой киноработы на русском
     * @param movieOriginalName оригинальное исправляемой добавляемой киноработы
     * @param movieYear         год выхода исправляемой киноработы
     * @param seriesAmount      количество серий в исправляемой киноработе
     * @param description       описание исправляемой киноработы
     * @param movieCountries    страны-производители киноработы
     * @param movieGenres       жанры киноработы
     * @param genres            Текущий список имеющихся жанров (для удобства обновления общего списка жанров)
     * @param countries         Текущий список стран (для удобства обновления общего списка стран)
     * @param imageUrl          URL картинки
     */
    void updateMovie(Integer movieId,
                     String movieRussianName,
                     String movieOriginalName,
                     String movieYear,
                     String seriesAmount,
                     String description,
                     String movieCountries,
                     String movieGenres,
                     ArrayList<String> genres,
                     ArrayList<String> countries,
                     String imageUrl);

    /**
     * @param movieId   Номер фильма в базе *
     * @param seriesUrl Массив с url конкретных серий
     */
    void changeUrl(Integer movieId, ArrayList<String> seriesUrl);

    /**
     * @param movieId   Номер фильма в базе *
     */
    void deleteMovie(Integer movieId);

}
