package authentication.modules.common.dto;

import lombok.Data;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Data
public class PageRequest implements Pageable {

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE_PAGE = 10;

    private int page;
    private int size;
    private String orderBy;
    private String orderDirection;

    public PageRequest() {
        this.page = DEFAULT_PAGE;
        this.size = DEFAULT_SIZE_PAGE;
        this.orderBy = "id";
        this.orderDirection = "ASC";
    }

    public PageRequest(int page, int size, String orderBy, String orderDirection) {
        this.page = page;
        this.size = size;
        this.orderBy = orderBy;
        this.orderDirection = orderDirection;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    @Override
    public long getOffset() {
        return (long) page * size;
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public Sort getSort() {
        return Sort.by(
                Sort.Direction.fromString(orderDirection),
                orderBy);
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public Pageable next() {
        return new PageRequest(page + 1, size, orderBy, orderDirection);
    }

    @Override
    public Pageable previousOrFirst() {
        return page > 0 ? new PageRequest(page - 1, size, orderBy, orderDirection) : this;
    }

    @Override
    public Pageable first() {
        return new PageRequest(0, size, orderBy, orderDirection);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return page > 0;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }

    @Override
    public OffsetScrollPosition toScrollPosition() {
        return Pageable.super.toScrollPosition();
    }
}
