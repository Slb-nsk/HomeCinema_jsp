package org.homecinema;

import org.homecinema.dao.MoviesDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    MoviesDao dao;

    @Test
    public void start_ReturnsValidModelAndView() {

    }


}
