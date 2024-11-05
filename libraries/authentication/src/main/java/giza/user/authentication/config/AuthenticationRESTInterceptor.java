package giza.user.authentication.config;

import giza.user.authentication.core.service.RESTRequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@AllArgsConstructor
public class AuthenticationRESTInterceptor extends WebMvcConfigurationSupport {
    private final RESTRequestInterceptor RESTRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(RESTRequestInterceptor);//.addPathPatterns("/**").excludePathPatterns("/users/**");
    }
}
