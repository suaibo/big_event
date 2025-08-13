package com.suai.library.book.model.request;

import com.suai.library.common.request.PageParam;
import lombok.Data;

@Data
public class BookPageParam extends PageParam {
    private String keyword;
}
