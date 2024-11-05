package giza.client.platform.user.core.service;

import giza.client.platform.user.api.service.IPrivilegeService;
import giza.client.platform.user.core.repository.RoleRepository;
import giza.client.platform.user.core.repository.UserRepository;
import giza.client.platform.user.model.entity.Role;
import giza.client.platform.user.model.entity.User;
import giza.client.platform.user.model.vto.UserVTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrivilegService implements IPrivilegeService {
    private final ModelMapper mapper;
    private final UserRepository repo;
    private final RoleRepository roleRepo;
    @Override
    public void assignRole(Long userID, Long roleID) {
        Optional<User> userOptional = repo.findById(userID);
        User user = mapper.map(userOptional, User.class);
        Optional<Role> roleOptional = roleRepo.findById(roleID);
        Role role = mapper.map(roleOptional, Role.class);
//        role.setUsers(Arrays.asList(user));
        user.addRole(role);
        repo.save(user);
    }

    @Override
    public void removeRole(Long userID) {
        Optional<User> userOptional = repo.findById(userID);
        User user = mapper.map(userOptional, User.class);
//        Set<Role> roleSet = user.getRoles();
//        Role role = mapper.map(roleSet, Role.class);
        user.removeRole();
        repo.save(user);
    }

    @Override
    public List<UserVTO> getUsersWithRole(Long roleID) {
        List<User> userList = repo.findUsersByRoles(roleID);
        List<UserVTO> userVTOS = userList.stream().map(user -> mapper.map(user, UserVTO.class)).collect(Collectors.toList());
//       List<Role> userList = roleRepo.findAllById(roleID);
        return userVTOS;
    }

}
