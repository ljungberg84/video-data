package se.complexjava.videostreamingapi.service;

import org.springframework.stereotype.Service;
import se.complexjava.videostreamingapi.entity.User;
import se.complexjava.videostreamingapi.entity.Video;
import se.complexjava.videostreamingapi.exceptionhandling.exception.ResourceCreationException;
import se.complexjava.videostreamingapi.exceptionhandling.exception.ResourceNotFoundException;
import se.complexjava.videostreamingapi.model.VideoModel;
import se.complexjava.videostreamingapi.repository.UserRepository;
import se.complexjava.videostreamingapi.repository.VideoRepository;

import java.util.List;
import java.util.Optional;


@Service
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;

    private UserRepository userRepository;


    public VideoServiceImpl(VideoRepository videoRepository, UserRepository userRepository) {

        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }


    @Override
    public VideoModel createVideo(VideoModel video, long userId) throws ResourceCreationException{


        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            Video videoEntity = Video.fromModel(video);
            videoEntity.setUser(optionalUser.get());

            return VideoModel.fromEntity(videoRepository.save(videoEntity));

        }else{
            throw new ResourceCreationException("User associated with created video not found");
        }
    }


    @Override
    public VideoModel getVideo(long videoId) throws Exception{

        Optional<Video> optionalVideo = videoRepository.findById(videoId);

        if(!optionalVideo.isPresent()){
            throw new ResourceNotFoundException(String.format("video with id %s not found", videoId));
        }

        return VideoModel.fromEntity(optionalVideo.get());
    }


    @Override
    public List<VideoModel> getVideos() {

        return VideoModel.fromEntity(videoRepository.findAll());
    }


    @Override
    public void deleteVideo(long videoId) {

        videoRepository.deleteById(videoId);
    }


    @Override
    public VideoModel updateVideo(VideoModel videoModel, long videoId) throws Exception{

        Optional<Video> optionalVideo = videoRepository.findById(videoId);

        if(!optionalVideo.isPresent()){
            throw new ResourceCreationException(String.format("video with id %s not found", videoId));
        }

        Video videoToUpdate = optionalVideo.get();
        videoToUpdate.setTitle(videoModel.getTitle());
        videoToUpdate.setDescription(videoModel.getDescription());

        return VideoModel.fromEntity(videoRepository.save(videoToUpdate));
    }

    //temp
    @Override
    public List<VideoModel> getVideosByUserId(long userId) {

        return VideoModel.fromEntity(videoRepository.findByUserId(userId));
    }


    //temp
    @Override
    public List<VideoModel> getVideosByCategoryId(long categoryId) {

        return VideoModel.fromEntity(videoRepository.findByCategoriesId(categoryId));
    }
}
