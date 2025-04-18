package scari.corp.test_rest_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scari.corp.test_rest_api.repository.UserRepository;
import scari.corp.test_rest_api.exception.ResourceNotFoundException;
import scari.corp.test_rest_api.model.User;


@Service
public class UserService {
    
    @Autowired
    private  UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() { return userRepository.findAll();}

    public User getUserById(Long id) {return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден, id: " + id));}

    public User createUser(User user){return userRepository.save(user);}

    public User updateUser(Long id, User user){
        return userRepository.findById(id)
            .map(userCheck  -> {

                if (user.getFirstName() != null) userCheck.setFirstName(user.getFirstName());
                if (user.getLastName() != null) userCheck.setLastName(user.getLastName());
                if (user.getSurName() != null) userCheck.setSurName(user.getSurName());
                if (user.getBirthDate() != null) userCheck.setBirthDate(user.getBirthDate());
                if (user.getEmail() != null) userCheck.setEmail(user.getEmail());
                if (user.getPhoneNumber() != null) userCheck.setPhoneNumber(user.getPhoneNumber());
                return userRepository.save(userCheck );
            })
            .orElse(null); 
    }

    public void deleteUser(Long id) {userRepository.deleteById(id);}

    public User getUserWithPhotos(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}


