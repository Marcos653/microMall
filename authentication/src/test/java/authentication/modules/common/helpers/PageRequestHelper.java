package authentication.modules.common.helpers;

import authentication.modules.common.dto.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageRequestHelper {

    public static Pageable onePageRequestHelper() {
        return new PageRequest(0, 20, "id", "ASC");
    }
}
