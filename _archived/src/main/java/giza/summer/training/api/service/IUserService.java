package giza.summer.training.api.service;

import giza.summer.training.model.UserListFilter;
import giza.summer.training.model.dto.UserDTO;
import giza.summer.training.model.entity.User;
import giza.summer.training.model.vto.UserListFilterVTO;
import giza.summer.training.model.vto.UserVTO;

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

}
