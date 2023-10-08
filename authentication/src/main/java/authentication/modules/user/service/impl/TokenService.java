package authentication.modules.user.service.impl;

import authentication.modules.user.service.ITokenService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    private static final int EXPIRATION_IN_TEN_MINUTES = 10;

    @Override
    public String createToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Date calculateExpiryDate() {
        var calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, EXPIRATION_IN_TEN_MINUTES);
        return calendar.getTime();
    }
}

