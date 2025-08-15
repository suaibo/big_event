package com.suai.library.book.model.vo;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BorrowBookRecordVo {
    private Integer id;
    private LocalDateTime borrowTime;   // 借阅时间
    private LocalDateTime returnTime;   // 归还时间（空表示未归还）
    private LocalDateTime deadline;     //截止时间
    private String title;            // 书名
    private String author;              // 作者
    private String isbn;                //isbn
    private String status;              // 未归还 / 已归还 / 逾期
}
