package giza.user.sync.core.service;

import giza.user.sync.core.repository.UserRepository;
import giza.user.sync.model.dto.UserSyncDTO;
import giza.user.sync.model.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final ModelMapper mapper;

    public void createUser(UserSyncDTO userSyncDTO) {
        User user = mapper.map(userSyncDTO, User.class);
        repo.save(user);
    }
}
