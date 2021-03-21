package com.sk.tdd.repository;

import com.sk.tdd.domain.SampleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends CrudRepository<SampleEntity, String> {
  SampleEntity getById(String id);
}
