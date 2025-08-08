package com.suai.service;

import com.suai.pojo.Book;
import com.suai.pojo.PageReqDto;
import com.suai.pojo.PageRespDto;

import java.util.List;

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
