package scari.corp.test_rest_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scari.corp.test_rest_api.model.User;
import scari.corp.test_rest_api.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Методы для работы с Пользователями
    @GetMapping
    public List<User> getAllUsers() {return userService.getAllUsers();}

    @GetMapping("/{id}")
    public ResponseEntity <User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser (@RequestBody User user) {        
        User createdUser  = userService.createUser (user);
        return ResponseEntity.status(201).body(createdUser ); 
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser (@PathVariable Long id, @RequestBody User user) {
        User updatedUser  = userService.updateUser (id, user);
        return updatedUser  != null ? ResponseEntity.ok(updatedUser ) : ResponseEntity.notFound().build();
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        userService.deleteUser (id);
        return ResponseEntity.noContent().build(); 
    }

    
}

