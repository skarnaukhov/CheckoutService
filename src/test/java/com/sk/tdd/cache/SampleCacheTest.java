package com.sk.tdd.cache;

import com.sk.tdd.domain.SampleEntity;
import com.sk.tdd.repository.SampleRepository;
import com.sk.tdd.service.SampleService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Ignore
public class SampleCacheTest {

  @Autowired
  private SampleService sampleService;

  @MockBean
  private SampleRepository sampleRepository;

  @Test
  public void testSample_isCached() throws Exception {
    given(sampleRepository.getById(anyString())).willReturn(new SampleEntity());

    sampleService.getSampleData("default");
    sampleService.getSampleData("default");

    verify(sampleRepository, times(1)).getById("default");
  }

}
