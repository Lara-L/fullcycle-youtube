package com.fullcycle.youtube.fcyoutube.service;

import java.io.File;
import java.io.IOException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VideoService {

  private final String TEMP_DIR = "/temp/videos/";
  public static final String ROUTING_KEY = "upload.complete";

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void saveChunk(String fileId, int chunkIndex, MultipartFile file) {
    try {
      File tempFile = new File(TEMP_DIR + fileId + "_" + chunkIndex);
      file.transferTo(tempFile);
    } catch (IOException e) {
      throw new RuntimeException("Erro ao salvar o chunk do arquivo" + fileId, e);
    }
  }

  public void publishUploadCompletedEvent(String fileId) {
    rabbitTemplate.convertAndSend(ROUTING_KEY + fileId); //no gpt tava "upload.complete"
  }

  public File assembleFile(String fileId, int totalChunks) {
    return new File(TEMP_DIR + fileId);
  }
}
