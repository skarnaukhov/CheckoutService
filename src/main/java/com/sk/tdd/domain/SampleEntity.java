package com.sk.tdd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
public class SampleEntity {

  @Id
  private String id;

  public SampleEntity() {
    id = "default";
  }
}
