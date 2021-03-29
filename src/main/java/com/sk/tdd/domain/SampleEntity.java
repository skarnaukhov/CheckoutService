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
  private String name;

  public SampleEntity(String name) {
    this.name = name;
  }

  public SampleEntity() {
    id = "default";
  }
}
