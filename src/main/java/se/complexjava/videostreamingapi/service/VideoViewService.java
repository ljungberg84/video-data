package se.complexjava.videostreamingapi.service;

import se.complexjava.videostreamingapi.exceptionhandling.exception.ResourceNotFoundException;
import se.complexjava.videostreamingapi.model.VideoViewModel;

import java.sql.SQLException;
import java.util.List;

public interface VideoViewService {

  VideoViewModel createVideoView(VideoViewModel videoViewModel) throws Exception;

  Iterable<VideoViewModel> getVideoViews() throws Exception;

  void deleteVideoViewByVideoId(Long videoId) throws Exception;
  void deleteVideoViewByUserId(Long userId) throws Exception;

  VideoViewModel updateVideoView(VideoViewModel videoViewModel) throws ResourceNotFoundException;

  Iterable<VideoViewModel> findVideoViewsByVideoId(Long videoId) throws ResourceNotFoundException;
  Iterable<VideoViewModel> findVideoViewsByUserId(Long userId) throws ResourceNotFoundException;
}
