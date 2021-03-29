package com.sk.tdd.repository;

import com.sk.tdd.domain.SampleEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@Ignore
public class SampleRepositoryTest {

  @Autowired
  private SampleRepository repository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  public void getSample_returnSampleData() throws Exception {
    SampleEntity savedSampleEntity = testEntityManager.persistFlushFind(new SampleEntity("ID", "name"));
    SampleEntity foundSampleEntity = repository.getById("ID");

    assertThat(foundSampleEntity).isNotNull();
    assertThat(foundSampleEntity.getId()).isEqualTo(savedSampleEntity.getId());
  }

}
