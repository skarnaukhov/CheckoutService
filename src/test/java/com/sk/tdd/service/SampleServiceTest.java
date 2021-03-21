package com.sk.tdd.service;

import com.sk.tdd.domain.SampleEntity;
import com.sk.tdd.exception.SampleEntityNotFoundException;
import com.sk.tdd.repository.SampleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class SampleServiceTest {

  @Mock
  private SampleRepository sampleRepository;

  private SampleService sampleService;

  @Before
  public void setUp() throws Exception {
    sampleService = new SampleService(sampleRepository);
  }

  @Test
  public void getSampleData_returnSampleData() {
    given(sampleRepository.getById("ID")).willReturn(new SampleEntity("ID"));
    SampleEntity sampleEntity = sampleService.getSampleData("ID");

    assertThat(sampleEntity).isNotNull();
    assertThat(sampleEntity.getId()).isNotNull();
    assertThat(sampleEntity.getId()).isEqualTo("ID");
  }

  @Test(expected = SampleEntityNotFoundException.class)
  public void getSampleData_notFound() {
    given(sampleRepository.getById(null)).willReturn(null);

    sampleService.getSampleData(null);
  }
}
