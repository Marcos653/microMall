package authentication.modules.authentication.service.impl;

import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
public class TokenExtractionService {

    private static final int BEARER_TOKEN_PREFIX_LENGTH = "Bearer ".length();

    public String extractToken(String authHeader) {
        if (isEmpty(authHeader) || !startsWith(authHeader, "Bearer ")) {
            return null;
        }

        return authHeader.substring(BEARER_TOKEN_PREFIX_LENGTH);
    }
}
