package com.pm.infrastructure.entity;

import com.alibaba.cola.dto.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PageResponse<T> extends Response {
    private static final long serialVersionUID = -2485419729448727849L;

    private Long total;

    private Collection<T> data;

    public static <T> PageResponse<T> of(Collection<T> data, long totalCount) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setData(data);
        response.setTotal(totalCount);
        return response;
    }

}
