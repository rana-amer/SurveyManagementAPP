package giza.user.sync.annotation;

import giza.user.sync.core.service.UserService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@Import(UserService.class)
@EntityScan(basePackages = "giza.user.sync.model.entity")
public class UserSyncImport {
}
