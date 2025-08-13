package com.suai.library.book.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("borrow_book_record")
@Data
public class BorrowBookRecord {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer bookId;
    private LocalDateTime borrowTime;       //借阅时间
    private LocalDateTime returnTime;   // 归还时间（空表示未归还）
    private LocalDateTime deadline;    //截止时间
}
