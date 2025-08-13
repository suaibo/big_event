package com.suai.library.book.model.vo;

import lombok.Data;

@Data
public class BookVo {
    private String author;
    private String publisher;
    private String isbn;
    private Integer inventory;
    private String title;
}
