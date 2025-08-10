package com.suai.library.book.model.dto;

import com.suai.library.book.model.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//购物车封装类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Book book;
    private Integer quantity;
}
