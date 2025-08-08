package com.suai.pojo;

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
