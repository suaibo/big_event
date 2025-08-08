package com.suai.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.suai.mapper.BookMapper;
import com.suai.pojo.Book;
import com.suai.pojo.PageReqDto;
import com.suai.pojo.PageRespDto;
import com.suai.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public PageRespDto<Book> findByAuthor(String keyword, PageReqDto pageReqDto) {
        PageRespDto<Book> bookPageRespDto = new PageRespDto<>();
        //使用pagehelper插件
        PageHelper.startPage(pageReqDto.getPageNum(), pageReqDto.getPageSize());
        List<Book> byAuthor = bookMapper.findByAuthor(keyword);
        Page<Book> page = (Page<Book>) byAuthor;
        bookPageRespDto.setTotal(page.getTotal());
        bookPageRespDto.setList(page.getResult());
        return bookPageRespDto;
    }


    @Override
    public void addBook(Book book) {
        bookMapper.addBook(book);
    }

    @Override
    public void deleteBook(String isbn) {
        bookMapper.deleteBook(isbn);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return bookMapper.findByIsbn(isbn);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void borrowBook(String isbn, Integer quantity) throws Exception {
//        //悲观锁
//        Book forUpdate = bookMapper.findForUpdate(isbn);
//        if (forUpdate != null) {
//            //更新数据
//            forUpdate.setInventory(forUpdate.getInventory() - quantity);
//            bookMapper.updateBook(forUpdate);
//        }else {throw new Exception();}
//    }




}
