package team.codex.trial;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import team.codex.trial.model.DataPoint;
import team.codex.trial.model.DataPointType;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherApiTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void weatherApiTests() throws Exception {
    mvc.perform(get("/collect/ping"))
        .andDo(print())
        .andExpect(status().isOk());

    addAirport("BOS", 42, -71).andDo(print()).andExpect(status().isOk());
    addAirport("EWR", 40, -74).andDo(print()).andExpect(status().isOk());
    addAirport("JFK", 40, -73).andDo(print()).andExpect(status().isOk());
    addAirport("LGA", 40, -75).andDo(print()).andExpect(status().isOk());
    addAirport("MMU", 40, -76).andDo(print()).andExpect(status().isOk());

    populate("WIND", 0, 10, 6, 4, 20)
        .andDo(print()).andExpect(status().isOk());

    query("BOS").andDo(print()).andExpect(status().isOk());
    query("EWR").andDo(print()).andExpect(status().isOk());
    query("JFK").andDo(print()).andExpect(status().isOk());
    query("LGA").andDo(print()).andExpect(status().isOk());
    query("MMU").andDo(print()).andExpect(status().isOk());

    pingQuery().andDo(print()).andExpect(status().isOk());
  }

  private ResultActions addAirport(String iata, int lat, int lng) throws Exception {
    return mvc.perform(post("/collect/airport/" + iata + "/" + lat + "/" + lng));
  }

  public ResultActions populate(String pointType, int first, int last, int mean, int median, int count)
      throws Exception {
    return mvc.perform(
        post("/collect/weather/BOS/" + pointType)
          .content(objectMapper.writeValueAsString(new DataPoint(first, last, mean, median, count, DataPointType.valueOf(pointType))))
          .contentType(MediaType.APPLICATION_JSON));
  }

  public ResultActions query(String iata) throws Exception {
    return mvc.perform(get("/query/weather/" + iata + "/0"));
  }

  public ResultActions pingQuery() throws Exception {
    return mvc.perform(get("/query/ping"));
  }
}
