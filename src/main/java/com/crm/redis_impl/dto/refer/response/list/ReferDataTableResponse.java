package com.crm.redis_impl.dto.refer.response.list;

import lombok.Data;

import java.util.List;

@Data
public class ReferDataTableResponse {
    private Integer totalElements;
    private Integer totalPages;
    private List<ReferListResponse> referList;
}
