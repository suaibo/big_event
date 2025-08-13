package com.suai.library.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.request.BookPageParam;
import com.suai.library.book.model.vo.BookPageVo;

public interface BookService extends IService<Book> {


    //借书操作
    void borrowBook(String isbn, Integer quantity) throws Exception;

    //添加到购物车
    void addItem();
}
