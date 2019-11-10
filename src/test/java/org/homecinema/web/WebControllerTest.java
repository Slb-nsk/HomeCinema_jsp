package org.homecinema.web;

import org.homecinema.TestConfig;
import org.homecinema.WebApp;
import org.homecinema.entities.ExtendedMovie;
import org.homecinema.entities.ShortMovie;
import org.homecinema.services.MovieService;
import org.homecinema.services.iMovieService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApp.class)
//@ContextConfiguration(classes = {TestConfig.class})
//@WebAppConfiguration
@SpringBootTest
//@AutoConfigureMockMvc
public class WebControllerTest {

//    @Autowired
    private MockMvc mvc;
    private ArrayList<String> testGenresList;
    private ArrayList<String> testCountriesList;

    @Autowired
    private WebApplicationContext wac;

//    @MockBean
    private iMovieService service = Mockito.mock(MovieService.class);

//    @InjectMocks
    private WebController webController;

    @Before
    public void init() {
        testGenresList = new ArrayList<String>();
        testGenresList.add(0, "");
        testGenresList.add(1, "боевик");
        testGenresList.add(2, "драма");
        testCountriesList = new ArrayList<String>();
        testCountriesList.add(0, "");
        testCountriesList.add(1, "КНР");
        testCountriesList.add(2, "США");
        webController.setService(service);
        webController.setGenres(testGenresList);
        webController.setCountries(testCountriesList);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mvc = MockMvcBuilders.standaloneSetup(webController).build();
    }

    @Test
    public void start_ReturnsValidModelAndView() throws Exception {

        //given
        ShortMovie movie1 = new ShortMovie(1, "фильм1", "movie1", 1, 1981, "http://example.com");
        ArrayList<ShortMovie> testList = new ArrayList<ShortMovie>();
        testList.add(movie1);

        Mockito.doReturn(testList).when(service).getFullCinemaList();

        //when
        ModelAndView modelAndView = webController.start();

        //then
        Assert.assertNotNull(modelAndView);
        mvc.perform(get("/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("start"))
                .andExpect(model().attribute("searchResult", testList));

    }

    @Test
    public void movieById_ReturnValidModelAndView_caseOneFilm() throws Exception {
        //given
        ArrayList<String> genres = new ArrayList<>();
        genres.add("боевик");
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Гонконг");
        ExtendedMovie movie1 = new ExtendedMovie(1, "фильм1", "movie1", 1, 1981, "", countries, genres, "http://example.com", "http://example.com");

//        Mockito.doReturn(movie1).when(service).getCinemaById(movie1.getMovieId(), genres, countries);
        given(service.getCinemaById(movie1.getMovieId(), genres, countries)).willReturn(movie1);

//        //when
//        ModelAndView modelAndView = webController.movieById(movie1.getMovieId());
        //        given(service.getCinemaById(movie1.getMovieId(), genres, countries)).willReturn(movie1);

        //then
//        Assert.assertNotNull(modelAndView);
        mvc.perform(get("/{movieId}", movie1.getMovieId()))
                .andExpect(status().isOk())
                .andExpect(view().name("film"))
                .andExpect(model().attribute("movie", movie1));
        Assert.assertTrue(movie1.getSeriesAmount() < 2);

    }

    @Test
    public void movieById_ReturnValidModelAndView_caseSerial() {
        //given
        ArrayList<String> genres = new ArrayList<>();
        genres.add("боевик");
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Гонконг");
        ExtendedMovie movie1 = new ExtendedMovie(1, "фильм1", "movie1", 1, 1981, "", countries, genres, "http://example.com", "http://example.com");

        Mockito.doReturn(movie1).when(service).getCinemaById(movie1.getMovieId(), genres, countries);


        //when
        ModelAndView modelAndView = webController.movieById(movie1.getMovieId());

        //then
        Assert.assertNotNull(modelAndView);
        Assert.assertEquals(movie1, modelAndView.getModel().get("movie"));
        Assert.assertTrue(movie1.getSeriesAmount() > 2);
        Assert.assertEquals("serial", modelAndView.getViewName());
        Assert.assertEquals(HttpStatus.OK, modelAndView.getStatus());

        Mockito.verify(service).getCinemaById(movie1.getMovieId(), genres, countries);
    }
}
