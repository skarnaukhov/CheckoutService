package com.sk.tdd;

import com.sk.tdd.domain.SampleEntity;
import com.sk.tdd.repository.SampleRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class ApplicationIntegrationTest {

  private static final String SAMPLE_ID_1 = "SAMPLE_ID_1";
  private static final String SAMPLE_ID_2 = "SAMPLE_ID_2";

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private SampleRepository sampleRepository;

  @Before
  public void setUp() throws Exception {
    this.sampleRepository.save(new SampleEntity(SAMPLE_ID_1));
  }

  @Test
  public void testSampleEndpoint_returnsNotFoundWhenNoId() throws Exception {
    webTestClient.get().uri("/sample/SampleEntity/{id}", SAMPLE_ID_2)
        .exchange().expectStatus().isNotFound();
  }

  @Test
  public void testSampleEndpoint_returnsEntityById() throws Exception {
    final SampleEntity sampleEntity = webTestClient.get().uri("/sample/SampleEntity/{id}", SAMPLE_ID_1)
        .exchange().expectStatus().isOk()
        .expectBody(SampleEntity.class).returnResult().getResponseBody();

    assertThat(sampleEntity.getId()).isEqualTo(SAMPLE_ID_1);

  }

}
