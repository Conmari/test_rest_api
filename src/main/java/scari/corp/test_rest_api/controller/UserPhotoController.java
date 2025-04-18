package scari.corp.test_rest_api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import scari.corp.test_rest_api.model.UserPhoto;
import scari.corp.test_rest_api.service.UserPhotoService;

@RestController

@RequestMapping("/users/{userId}/photos")
public class UserPhotoController {

    
    @Autowired
    private UserPhotoService userPhotoService;


    @GetMapping
    public ResponseEntity<List<UserPhoto>> getAllPhotosForUser(@PathVariable Long userId) {
        try {
            List<UserPhoto> photos = userPhotoService.getAllPhotosForUser(userId);
            return ResponseEntity.ok(photos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
   
    @PostMapping("/{userid}")
    public ResponseEntity<UserPhoto> uploadPhoto(@PathVariable Long userid, @RequestParam("file") MultipartFile file) {
        try {
            UserPhoto userPhoto = userPhotoService.uploadPhoto(userid, file);
            return ResponseEntity.ok(userPhoto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
        try {
            byte[] photo = userPhotoService.getPhoto(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPhoto> updatePhoto(@PathVariable Long userId, @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            UserPhoto updatedPhoto = userPhotoService.updatePhoto(userId, id, file);
            return ResponseEntity.ok(updatedPhoto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long userId, @PathVariable Long id) {
        try {
            userPhotoService.deletePhoto(userId, id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}