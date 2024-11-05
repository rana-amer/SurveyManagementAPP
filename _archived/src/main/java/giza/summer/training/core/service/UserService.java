package giza.summer.training.core.service;

import giza.summer.training.api.service.IUserService;
import giza.summer.training.core.repository.UserCriteriaRepository;
import giza.summer.training.core.repository.UserRepository;
import giza.summer.training.model.UserListFilter;
import giza.summer.training.model.dto.UserDTO;
import giza.summer.training.model.entity.User;
import giza.summer.training.model.exceptions.SurveysException;
import giza.summer.training.model.vto.UserListFilterVTO;
import giza.summer.training.model.vto.UserVTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repo;
    private final ModelMapper mapper;

    private final UserCriteriaRepository userCriteriaRepository;
    @Override
    public UserListFilterVTO getUsers(UserListFilter userListFilter){
        Page<User> userPage = userCriteriaRepository.findAllWithFilters(userListFilter);
        Integer count = userPage.getNumberOfElements();
        List<UserVTO> userVTOS = userPage.getContent().stream()
                .map(user -> mapper.map(user, UserVTO.class))
                .collect(Collectors.toList());
        UserListFilterVTO userListFilterVTO = new UserListFilterVTO();
        userListFilterVTO.setUserVTOS(userVTOS);
        userListFilterVTO.setCount(count);
        return userListFilterVTO;
    }

    @Override
    public Long createUser(UserDTO obj) {
        User user = repo.findByUsername(obj.getUsername());
        if (user != null)
            throw new SurveysException("Username already existed");

//        if (!obj.getGender().equals("male") && !obj.getGender().equals("female"))
//            throw new SurveysException("Invalid User gender");

        user = mapper.map(obj, User.class);
        user.setCreatedOn(LocalDateTime.now());
        repo.save(user);
        return user.getId();
    }

    @Override
    public List<User> getAllDetails() {
        if (repo.findAll().isEmpty())
            throw new SurveysException("No users found");
//        List<User> users = repo.findAll();
//        UserVTO userVTO = mapper.map(users, UserVTO.class);
        return repo.findAll();

    }

    @Override
    public UserVTO getDetailsById(Long id) {
        Optional<User> user = repo.findById(id);
//        User user = mapper.map(user_old, User.class);
        if (user == null)
            throw new SurveysException("User not found");
        UserVTO userVTO = mapper.map(user, UserVTO.class);
        return userVTO;

    }

    @Override
    public UserVTO getDetailsByUsername(String username) {
        User user = repo.findByUsername(username);
        if (user == null)
            throw new SurveysException("User not found");

        UserVTO userVTO = mapper.map(user, UserVTO.class);
        return userVTO;

    }

    @Override
    public void updateUserById(UserDTO obj, Long id) {
        Optional<User> user_old = repo.findById(id);
        User user = mapper.map(user_old, User.class);
        if (user == null)
            throw new SurveysException("User not found");
//        if (repo.findByUsername(obj.getUsername()) != null)
//            throw new SurveysException("Username already existed");
        mapper.map(obj, User.class);
        mapper.map(obj,user);
//        if(obj.getGender() != "male"||obj.getGender() != "female")
//            throw new SurveysException("Unspecified gender");
//        if (obj.getFullname() != null) {
//            user.setFullname(obj.getFullname());
//        }
//        if (obj.getUsername() != null) {
//            user.setUsername(obj.getUsername());
//        }
//        if (obj.getEmail() != null) {
//            user.setEmail(obj.getEmail());
//        }
//        if (obj.getGender() != null) {
//            user.setGender(obj.getGender());
//        }
//        if (obj.getIsActive() != null) {
//            user.setIsActive(obj.getIsActive());
//        }
//        if (obj.getUserPassword() != null) {
//            user.setUserPassword(obj.getUserPassword());
//        }
        repo.save(user);

    }

    @Override
    public void updateUserByUsername(UserDTO obj, String username) {
        User user = repo.findByUsername(username);

        if (user == null)
            throw new SurveysException("User not found");
//        if(obj.getGender() != "male"||obj.getGender() != "female")
//            return ResponseEntity.badRequest().body("Unspecified gender");
        mapper.map(obj, User.class);
        mapper.map(obj, user);
//        if (obj.getFullname() != null) {
//            user.setFullname(obj.getFullname());
//        }
//        if (obj.getEmail() != null) {
//            user.setEmail(obj.getEmail());
//        }
//        if (obj.getGender() != null) {
//            user.setGender(obj.getGender());
//        }
//        if (obj.getIsActive() != null) {
//            user.setIsActive(obj.getIsActive());
//        }
//        if (obj.getUserPassword() != null) {
//            user.setUserPassword(obj.getUserPassword());
//        }
        repo.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> user = repo.findById(id);
//        User user = mapper.map(user_old, User.class);
        if (user == null)
            throw new SurveysException("User Not Found");
        repo.deleteById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = repo.findByUsername(username);
        if (user == null)
            throw new SurveysException("User Not Found");
        repo.delete(user);
    }


}
