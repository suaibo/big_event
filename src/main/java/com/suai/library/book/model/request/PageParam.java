package com.suai.library.book.model.request;

import lombok.Data;

@Data
public class PageParam {
    //默认一
    private int pageNum = 1;
    //默认十
    private int pageSize = 10;

}
