package dancing.school.helpers;

import dancing.school.enums.RoleEnum;
import dancing.school.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class JwtAuthHelper {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    @Autowired
    private JwtService jwtService;

    public void checkRole(String token, RoleEnum checkedRole) throws ResponseStatusException {
        if (StringUtils.isEmpty(token) || !token.startsWith(BEARER_PREFIX))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        String jwt = token.substring(BEARER_PREFIX.length());

        RoleEnum roleEnum = this.jwtService.extractRole(jwt);

        if(!roleEnum.equals(checkedRole))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
