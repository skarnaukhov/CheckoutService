package com.sk.tdd.api;

import com.sk.tdd.domain.SampleEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE)
public interface SampleApi {

  @GetMapping("/SampleEntity/{id}")
  SampleEntity getSampleEntityById(@PathVariable("id") final String id);

  @PutMapping("/SampleEntity/{id}/add")
  void addSampleEntity(@PathVariable("id") final String id, @RequestParam("name") final String name);
}
