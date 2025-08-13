package com.suai.library.book.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author 王彬权
 */
@TableName("book")
@Data
public class Book {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    //库存
    private Integer inventory;
}
