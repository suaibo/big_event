package com.suai.library.book.model.request;

import lombok.Data;

@Data
public class BookBorrowingParam {
    private Integer userId;
    private String isbn;
}
