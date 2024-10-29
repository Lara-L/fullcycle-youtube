package com.fullcycle.youtube.fcyoutube.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisher {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void publishUploadCompleted(String fileId) {
    rabbitTemplate.convertAndSend("video.processing.start", fileId);
  }

}
