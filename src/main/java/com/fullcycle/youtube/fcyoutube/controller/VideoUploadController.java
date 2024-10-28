package com.fullcycle.youtube.fcyoutube.controller;

import com.fullcycle.youtube.fcyoutube.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/videos")
public class VideoUploadController {

  @Autowired
  private VideoService videoService;

//  public VideoUploadController(VideoService videoService) {
//    this.videoService = videoService;
//  }

  public ResponseEntity<String> uploadChunk(
      @RequestParam("fileId") String fileId,
      @RequestParam("chunkIndex") int chunkIndex,
      @RequestParam("totalChunks") int totalChunks,
      @RequestParam("file") MultipartFile file) {

    videoService.saveChunk(fileId, chunkIndex, file);

    if (chunkIndex == totalChunks - 1) {
      videoService.publishUploadCompletedEvent(fileId);
    }

    return ResponseEntity.ok("Chunk received");
  }
}
