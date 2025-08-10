package com.suai.library.book.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author 王彬权
 */
@Data
public class Book {
    @Id
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    //库存
    private Integer inventory;
}
