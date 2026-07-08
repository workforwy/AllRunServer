package com.workforwy.allrun.dto;

import java.util.List;

public record PageResponse<T>(
        int page,
        int size,
        List<T> items
) {
}
