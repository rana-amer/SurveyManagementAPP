package giza.user.authentication.core.service;


//import giza.client.platform.survey.model.dto.Token;
import giza.user.authentication.model.dto.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RESTRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        final Token token = new Token();
        final String authorization = request.getHeader("Authorization");
        final String auth = authorization.substring(7);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9100/api-user/security/token";
        HttpEntity<Token> tokenHttpEntity = new HttpEntity<>(new Token(auth));
        //        Token token = new Token();
//        token.setToken(authorization);
        Boolean res = restTemplate.postForObject(url,tokenHttpEntity, Boolean.class);
        return res;

    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
