package giza.user.authentication.annotation;

import giza.user.authentication.config.AuthenticationRESTInterceptor;
import giza.user.authentication.core.service.RESTRequestInterceptor;
import org.springframework.context.annotation.Import;

@Import({AuthenticationRESTInterceptor.class, RESTRequestInterceptor.class})
public class UserAuthenticationImport {
}
