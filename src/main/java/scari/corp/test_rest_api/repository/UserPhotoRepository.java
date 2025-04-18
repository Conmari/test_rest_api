package scari.corp.test_rest_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import scari.corp.test_rest_api.model.User;
import scari.corp.test_rest_api.model.UserPhoto;

public interface UserPhotoRepository extends JpaRepository <UserPhoto, Long>  {
    List<UserPhoto> findByUser(User user);
}
