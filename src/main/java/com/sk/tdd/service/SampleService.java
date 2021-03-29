package com.sk.tdd.service;

import com.sk.tdd.domain.SampleEntity;
import com.sk.tdd.exception.SampleEntityNotFoundException;
import com.sk.tdd.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class SampleService {

  private SampleRepository sampleRepository;

  @Autowired
  public SampleService(SampleRepository sampleRepository) {
    this.sampleRepository = sampleRepository;
  }

  @Cacheable("samples")
  public SampleEntity getSampleData(String id) {
    final SampleEntity sampleEntity = sampleRepository.getById(id);
    if (isNull(sampleEntity)) {
      throw new SampleEntityNotFoundException("ID: " + id);
    }
    return sampleEntity;
  }

  public void addSampleEntity(String id, String name) {
    sampleRepository.save(new SampleEntity(id, name));
  }
}
