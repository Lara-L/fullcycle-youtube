package com.fullcycle.youtube.fcyoutube.service;

import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public class VideoService {

  private final String TEMP_DIR = "/temp/videos/";

  public void saveChunk(String fileId, int chunkIndex, MultipartFile file) throws IOException {
    File tempFile = new File(TEMP_DIR + fileId + "_" + chunkIndex);
    file.transferTo(tempFile);
  }

  public void publishUploadCompletedEvent(String fileId) {
    rabbitTemplate.convertAndSend("upload.complete", fileId);
  }

  public File assembleFile(String fileId, int totalChunks) {
    return null;
  }
}
