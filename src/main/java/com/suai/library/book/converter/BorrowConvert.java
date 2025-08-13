package com.suai.library.book.converter;

import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.entity.BorrowBookRecord;
import com.suai.library.book.model.vo.BorrowBookRecordVo;
import com.suai.library.book.service.BookService;

import java.time.LocalDateTime;

public final class BorrowConvert {
    private BorrowConvert() {}

    public static BorrowBookRecordVo toVo(BorrowBookRecord borrowBookRecord, BookService bookService) {
        if(borrowBookRecord == null) {
            return null;
        }
        Book byId = bookService.getById(borrowBookRecord.getBookId());

        BorrowBookRecordVo vo = new BorrowBookRecordVo();
        vo.setIsbn(byId.getIsbn());
        vo.setAuthor(byId.getAuthor());
        vo.setBorrowTime(borrowBookRecord.getBorrowTime());
        vo.setReturnTime(borrowBookRecord.getReturnTime());
        vo.setTitle(byId.getTitle());
        vo.setStatus(statusOf(borrowBookRecord));
        return vo;
    }

    public static String statusOf(BorrowBookRecord borrowBookRecord) {
        if(borrowBookRecord.getReturnTime()!=null) {
            return "已归还";
        }
        return LocalDateTime.now().isAfter(borrowBookRecord.getDeadline()) ? "逾期" : "待还";
    }

}
