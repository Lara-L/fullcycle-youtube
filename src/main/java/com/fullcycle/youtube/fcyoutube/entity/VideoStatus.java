package com.fullcycle.youtube.fcyoutube.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class VideoStatus {

  @Id
  private String fileId;
  private String status;
  private String message;
}
