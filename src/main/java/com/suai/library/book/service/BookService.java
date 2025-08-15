package com.suai.library.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.request.BookBorrowingParam;
import com.suai.library.book.model.vo.BorrowBookRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookService extends IService<Book> {

     boolean borrowBook(BookBorrowingParam bookBorrowParam);

}
