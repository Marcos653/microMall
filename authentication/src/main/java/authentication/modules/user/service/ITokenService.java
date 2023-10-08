package authentication.modules.user.service;

import java.util.Date;

public interface ITokenService {

    String createToken();

    Date calculateExpiryDate();
}
