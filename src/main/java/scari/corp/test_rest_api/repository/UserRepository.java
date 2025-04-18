package scari.corp.test_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import scari.corp.test_rest_api.model.User;

public interface UserRepository extends JpaRepository <User, Long> {
    
}

