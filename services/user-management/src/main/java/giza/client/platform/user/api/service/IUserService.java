package giza.client.platform.user.api.service;

import giza.client.platform.user.model.UserListFilter;
import giza.client.platform.user.model.dto.UserDTO;
import giza.client.platform.user.model.entity.User;
import giza.client.platform.user.model.vto.UserListFilterVTO;
import giza.client.platform.user.model.vto.UserVTO;

import java.util.List;

public interface IUserService {
    Long createUser(UserDTO obj);

    List<User> getAllDetails();

    UserVTO getDetailsById(Long id);
    UserListFilterVTO getUsers(UserListFilter userListFilter);

    UserVTO getDetailsByUsername(String username);

    void updateUserById(UserDTO obj, Long id);

    void updateUserByUsername(UserDTO obj, String username);

    void deleteUserById(Long id);

    void deleteUserByUsername(String username);

    void activateUser(Long Id);
    void deactivateUser(Long Id);


}
