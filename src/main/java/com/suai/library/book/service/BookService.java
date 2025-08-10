package com.suai.library.book.service;

import com.suai.library.book.model.entity.Book;
import com.suai.library.pojo.dto.PageReqDto;
import com.suai.library.pojo.dto.PageRespDto;

public interface BookService {

    //模糊查询通过作者或书名
    PageRespDto<Book> findByAuthor(String keyword, PageReqDto pageReqDto);


    void addBook(Book book);
    void deleteBook(String isbn);
    void updateBook(Book book);

    Book findByIsbn(String isbn);

    //借书操作
    void borrowBook(String isbn, Integer quantity) throws Exception;

    //添加到购物车
    void addItem(String isbn, );
}
