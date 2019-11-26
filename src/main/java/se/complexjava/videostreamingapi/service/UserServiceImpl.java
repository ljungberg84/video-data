package se.complexjava.videostreamingapi.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.complexjava.videostreamingapi.entity.UserEntity;
import se.complexjava.videostreamingapi.exceptionhandling.exception.ResourceNotFoundException;
import se.complexjava.videostreamingapi.model.UserModel;
import se.complexjava.videostreamingapi.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserModel createUser(UserEntity user) {

        user.setJoinDate(Instant.now());
        UserEntity savedUser = repository.save(user);

        return UserModel.fromEntity(savedUser);
    }

    @Override
    public UserModel getUser(Long userId) throws Exception{

        Optional<UserEntity> user = repository.findById(userId);

        if(!user.isPresent()){
            throw new ResourceNotFoundException(String.format("User with id: %s not found", userId));
        }

        return UserModel.fromEntity(user.get());
    }

    @Override
    public List<UserModel> getUsers() {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserModel updateUser(UserEntity user) {
        return null;
    }
}