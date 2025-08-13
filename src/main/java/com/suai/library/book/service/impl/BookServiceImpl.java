package com.suai.library.book.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageParam;
import com.suai.library.book.model.request.BookPageParam;
import com.suai.library.book.model.vo.BookPageVo;
import com.suai.library.book.repository.BookMapper;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.service.BookService;
import com.suai.library.common.resp.PageVo;
import com.suai.library.pojo.dto.PageReqDto;
import com.suai.library.pojo.dto.PageRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void borrowBook(String isbn, Integer quantity) {

    }

    @Override
    public void addItem() {

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
