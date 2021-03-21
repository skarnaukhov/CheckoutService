package com.sk.tdd.controller;


import com.sk.tdd.api.SampleApi;
import com.sk.tdd.domain.SampleEntity;
import com.sk.tdd.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController implements SampleApi {

  private final SampleService sampleService;

  @Autowired
  public SampleController(SampleService sampleService) {
    this.sampleService = sampleService;
  }

  @Override
  public SampleEntity getSampleEntityById(String id) {
    return sampleService.getSampleData(id);
  }
}
