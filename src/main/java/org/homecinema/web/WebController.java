package org.homecinema.web;

import org.homecinema.entities.*;
import org.homecinema.services.iMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class WebController {
    private iMovieService service;
    private ArrayList<String> genres = new ArrayList<>();
    private ArrayList<String> countries = new ArrayList<>();

    @Autowired
    public WebController(iMovieService service) {
        this.service = service;
        this.genres = service.getGenresList();
        this.countries = service.getcountriesList();
    }

    //    @Autowired
    public void setService(iMovieService service) {
        this.service = service;
    }

    //    @Autowired
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    //    @Autowired
    public void setCountries(ArrayList<String> countries) {
        this.countries = countries;
    }

    @GetMapping("/")
    public ModelAndView start() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("start");
        mav.addObject("searchResult", service.getFullCinemaList());
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("create");
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @GetMapping("/{movieId}/{seriaSelected}")
    public ModelAndView movieById(@PathVariable int movieId, @PathVariable int seriaSelected) {
        ExtendedMovie em = service.getCinemaById(movieId, genres, countries);
        ModelAndView mav = new ModelAndView();
        if (em.getSeriesAmount() < 2) {
            mav.setViewName("film");
        } else {
            mav.setViewName("serial");
            mav.addObject("seriaSelected", seriaSelected);
        }
        mav.addObject("movie", em);
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @PostMapping(path = "/add")
    public ModelAndView addMovie(String movieRussianName,
                                 String movieOriginalName,
                                 String movieYear,
                                 String seriesAmount,
                                 String description,
                                 String movieCountries,
                                 String movieGenres,
                                 String imageUrl) {
        service.addMovie(movieRussianName, movieOriginalName, movieYear, seriesAmount, description,
                movieCountries, movieGenres, genres, countries, imageUrl);
        this.genres = service.getGenresList();
        this.countries = service.getcountriesList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("start");
        mav.addObject("searchResult", service.getFullCinemaList());
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @GetMapping("/update/{movieId}")
    public ModelAndView update(@PathVariable int movieId) {
        ExtendedMovie em = service.getCinemaById(movieId, genres, countries);
//        System.out.println(em);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("update");
        mav.addObject("movie", em);
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    //так как ftl поддерживает только чистый html, обычный put-запрос сделать невозможно, приходится оформлять через post
    @PostMapping(path = "/put/{movieId}")
    public ModelAndView updateMovie(@PathVariable Integer movieId,
                                    String movieRussianName,
                                    String movieOriginalName,
                                    String movieYear,
                                    String seriesAmount,
                                    String description,
                                    String movieCountries,
                                    String movieGenres,
                                    String imageUrl) {
        service.updateMovie(movieId, movieRussianName, movieOriginalName, movieYear, seriesAmount, description,
                movieCountries, movieGenres, genres, countries, imageUrl);
        this.genres = service.getGenresList();
        this.countries = service.getcountriesList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("start");
        mav.addObject("searchResult", service.getFullCinemaList());
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    //так как ftl поддерживает только чистый html, url приходится обновлять отдельным методом
    @GetMapping(path = "/changeurl/{movieId}")
    public ModelAndView changeUrl(@PathVariable int movieId) {
        ExtendedMovie em = service.getCinemaById(movieId, genres, countries);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("changeurl");
        mav.addObject("movie", em);
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @PostMapping(path = "/changeurl/{movieId}")
    public ModelAndView updateURL(@PathVariable Integer movieId, @RequestParam HashMap<String, String> series) {
        ArrayList<String> seriesUrl = new ArrayList<>();
        seriesUrl.add(0, "");
        for (String k : series.keySet()) {
            seriesUrl.add(Integer.parseInt(k.substring(5)), series.get(k));
        }
        service.changeUrl(movieId, seriesUrl);
        this.genres = service.getGenresList();
        this.countries = service.getcountriesList();
        ExtendedMovie em = service.getCinemaById(movieId, genres, countries);
        ModelAndView mav = new ModelAndView();
        if (em.getSeriesAmount() < 2) {
            mav.setViewName("film");
        } else {
            mav.setViewName("serial");
            mav.addObject("seriaSelected", 1);
        }
        mav.addObject("movie", em);
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @GetMapping("/delete/{movieId}")
    public ModelAndView deleteMovie(@PathVariable int movieId) {
        service.deleteMovie(movieId);
        this.genres = service.getGenresList();
        this.countries = service.getcountriesList();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("start");
        mav.addObject("searchResult", service.getFullCinemaList());
        mav.setStatus(HttpStatus.OK);
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView searchMovies(@RequestParam String kind, String country, String genre) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("start");
        if (kind.equals("Any") && country.equals("Any") && genre.equals("Any")) {
            mav.addObject("searchResult", service.getFullCinemaList());
        } else {
            mav.addObject("searchResult", service.doSearch(kind, country, genre));
        }
        mav.setStatus(HttpStatus.OK);
        return mav;
    }
}

