package scari.corp.test_rest_api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import scari.corp.test_rest_api.model.User;
import scari.corp.test_rest_api.model.UserPhoto;
import scari.corp.test_rest_api.repository.UserPhotoRepository;
import scari.corp.test_rest_api.repository.UserRepository;

@Service
public class UserPhotoService {

    @Autowired
    private UserPhotoRepository userPhotoRepository;

    @Autowired
    private UserRepository userRepository;

    public byte[] getPhoto(Long userid){
        UserPhoto userPhoto =userPhotoRepository.findById(userid).orElseThrow(() -> new RuntimeException("Фотография не найдена"));
        return userPhoto.getPhoto();
    }

    public List<UserPhoto> getAllPhotosForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return userPhotoRepository.findByUser(user);
    }

    public UserPhoto uploadPhoto(Long id, MultipartFile file) throws IOException{
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        byte[] photoBytes = file.getBytes();
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setPhoto(photoBytes);
        userPhoto.setUser(user);

        return userPhotoRepository.save(userPhoto);
    }

    @Transactional
    public UserPhoto updatePhoto(Long userId, Long id, MultipartFile file) throws IOException {
        UserPhoto userPhoto = userPhotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фотография не найдена"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!userPhoto.getUser().getId().equals(userId)) {
            throw new RuntimeException("У вас нет прав на обновление этой фотографии");
        }

        byte[] photoBytes = file.getBytes();
        userPhoto.setPhoto(photoBytes);

        return userPhotoRepository.save(userPhoto);
    }

    @Transactional
    public void deletePhoto(Long userId, Long id) {
        UserPhoto userPhoto = userPhotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Фотография не найдена"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Проверка, что фотография принадлежит пользователю
        if (!userPhoto.getUser().getId().equals(userId)) {
            throw new RuntimeException("У вас нет прав на удаление этой фотографии");
        }

        userPhotoRepository.delete(userPhoto);
    }
}