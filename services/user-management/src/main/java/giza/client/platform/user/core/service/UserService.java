package giza.client.platform.user.core.service;

import giza.client.platform.user.api.service.IUserService;
import giza.client.platform.user.core.repository.RoleRepository;
import giza.client.platform.user.core.repository.UserCriteriaRepository;
import giza.client.platform.user.core.repository.UserRepository;
import giza.client.platform.user.exceptions.UserException;
import giza.client.platform.user.model.UserListFilter;
import giza.client.platform.user.model.dto.UserDTO;
import giza.client.platform.user.model.entity.User;
import giza.client.platform.user.model.vto.UserListFilterVTO;
import giza.client.platform.user.model.vto.UserVTO;
import giza.user.sync.model.dto.UserSyncDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repo;
    private final ModelMapper mapper;
    private final JmsTemplate template;
    private final UserCriteriaRepository userCriteriaRepository;
    private final RoleRepository roleRepo;

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
            throw new UserException("Username already existed");

//        if (!obj.getGender().equals("male") && !obj.getGender().equals("female"))
//            throw new SurveysException("Invalid User gender");
//        obj.setCreatedOn(DateTime.);
        user = mapper.map(obj, User.class);
//        user.setCreatedOn(LocalDateTime.now());
        repo.save(user);
        UserSyncDTO userSyncDTO = mapper.map(user, UserSyncDTO.class);
//        mapper.map(user,userSyncDTO);
        template.convertAndSend("giza.survey.save.user",userSyncDTO);
        template.convertAndSend("giza.ntf.save.user",userSyncDTO);
        return user.getId();
    }

    @Override
    public List<User> getAllDetails() {
        if (repo.findAll().isEmpty())
            throw new UserException("No users found");
//        List<User> users = repo.findAll();
//        UserVTO userVTO = mapper.map(users, UserVTO.class);
        return repo.findAll();

    }

    @Override
    public UserVTO getDetailsById(Long id) {
        Optional<User> user = repo.findById(id);
//        User user = mapper.map(user_old, User.class);
        if (user.isEmpty())
            throw new UserException("User not found");
        UserVTO userVTO = mapper.map(user, UserVTO.class);
        return userVTO;

    }

    @Override
    public UserVTO getDetailsByUsername(String username) {
        User user = repo.findByUsername(username);
        if (user == null)
            throw new UserException("User not found");

        UserVTO userVTO = mapper.map(user, UserVTO.class);
        return userVTO;

    }

    @Override
    public void updateUserById(UserDTO obj, Long id) {
        Optional<User> user_old = repo.findById(id);
        User user = mapper.map(user_old, User.class);
        if (user == null)
            throw new UserException("User not found");
//        if (repo.findByUsername(obj.getUsername()) != null)
//            throw new SurveysException("Username already existed");
        User objUser = mapper.map(obj, User.class);
        mapper.map(obj,user);
//        if(obj.getGender() != "male"||obj.getGender() != "female")
//            throw new SurveysException("Unspecified gender");

        repo.save(user);

    }

    @Override
    public void updateUserByUsername(UserDTO obj, String username) {
        User user = repo.findByUsername(username);

        if (user == null)
            throw new UserException("User not found");
//        if(obj.getGender() != "male"||obj.getGender() != "female")
//            return ResponseEntity.badRequest().body("Unspecified gender");
        mapper.map(obj, User.class);
        mapper.map(obj, user);
        repo.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> user = repo.findById(id);
//        User user = mapper.map(user_old, User.class);
        if (user.isEmpty())
            throw new UserException("User Not Found");
        repo.deleteById(id);
    }

    @Override
    public void deleteUserByUsername(String username) {
        User user = repo.findByUsername(username);
        if (user == null)
            throw new UserException("User Not Found");
        repo.delete(user);
    }

    @Override
    public void activateUser(Long Id) {
        Optional<User> userOptional = repo.findById(Id);
        if (userOptional.isEmpty())
            throw new UserException("User not found");
        User user = mapper.map(userOptional, User.class);
        user.setIsActive(true);
        repo.save(user);
    }

    @Override
    public void deactivateUser(Long Id) {
        Optional<User> userOptional = repo.findById(Id);
        if (userOptional.isEmpty())
            throw new UserException("User not found");
        User user = mapper.map(userOptional, User.class);
        user.setIsActive(false);
        repo.save(user);
    }




}
