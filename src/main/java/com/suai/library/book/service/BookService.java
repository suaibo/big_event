package com.suai.library.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.request.BookBorrowingParam;
import com.suai.library.book.model.vo.BookVo;

public interface BookService extends IService<Book> {

     boolean borrowBook(BookBorrowingParam bookBorrowParam);

    BookVo queryById(Integer id);
}
