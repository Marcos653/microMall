package authentication.modules.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableSpringDataWebSupport
public class WebMvcConfig implements WebMvcConfigurer {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customPageableResolver());
    }

    @Bean
    public PageableHandlerMethodArgumentResolver customPageableResolver() {
        return new PageableHandlerMethodArgumentResolver() {
            @NotNull
            @Override
            public Pageable resolveArgument(@NotNull MethodParameter methodParameter, ModelAndViewContainer mavContainer,
                                            @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
                var page = parsePageNumber(webRequest.getParameter("page"));
                var size = parsePageSize(webRequest.getParameter("size"));
                var sort = parseSort(webRequest.getParameter("orderBy"), webRequest.getParameter("orderDirection"));

                return PageRequest.of(page, size, sort);
            }
        };
    }

    private int parsePageNumber(String pageStr) {
        try {
            return pageStr != null ? Integer.parseInt(pageStr) : DEFAULT_PAGE;
        } catch (NumberFormatException ex) {
            return DEFAULT_PAGE;
        }
    }

    private int parsePageSize(String sizeStr) {
        try {
            return sizeStr != null ? Integer.parseInt(sizeStr) : DEFAULT_SIZE;
        } catch (NumberFormatException ex) {
            return DEFAULT_SIZE;
        }
    }

    private Sort parseSort(String orderBy, String orderDirection) {
        if (orderBy == null || orderDirection == null) {
            return Sort.unsorted();
        }
        Sort.Direction direction = Sort.Direction.fromString(orderDirection);
        return Sort.by(direction, orderBy);
    }
}
