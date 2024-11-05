package giza.client.platform.user.api.service;

import giza.client.platform.user.model.vto.UserVTO;

import java.util.List;

public interface IPrivilegeService {
    void assignRole(Long userID,Long roleID);
    void removeRole(Long userID);
    List<UserVTO> getUsersWithRole(Long roleID);
}
