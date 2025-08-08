package com.suai.pojo;

import lombok.Data;

@Data
public class PageReqDto {
    //默认一
    private int pageNum = 1;
    //默认十
    private int pageSize = 10;

}
