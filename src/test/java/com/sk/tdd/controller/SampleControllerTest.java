package com.sk.tdd.controller;

import com.sk.tdd.domain.SampleEntity;
import com.sk.tdd.exception.SampleEntityNotFoundException;
import com.sk.tdd.service.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SampleController.class)
@RunWith(SpringRunner.class)
public class SampleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SampleService sampleService;

  @Test
  public void getSampleData_notFound() throws Exception {
    given(sampleService.getSampleData(null)).willThrow(SampleEntityNotFoundException.class);

    mockMvc.perform(MockMvcRequestBuilders.get("/sample/SampleEntity"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getSampleDataById() throws Exception {
    given(sampleService.getSampleData("ID")).willReturn(new SampleEntity("ID", "name"));

    mockMvc.perform(MockMvcRequestBuilders.get("/sample/SampleEntity/ID"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value("ID"));
  }

}
