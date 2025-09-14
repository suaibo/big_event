package com.suai.library.book.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suai.library.book.converter.BookConvert;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.entity.BorrowBookRecord;
import com.suai.library.book.model.request.BookBorrowingParam;
import com.suai.library.book.model.vo.BookVo;
import com.suai.library.book.repository.BookMapper;
import com.suai.library.book.service.BookService;
import com.suai.library.book.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //添加redis缓存层
    @Override
    public BookVo queryById(Integer id) {
        String key = "cache:book:" + id;
        //1.通过redis查询
        String stringBook = stringRedisTemplate.opsForValue().get(key);
        //2.如果有则返回
        if(StrUtil.isNotBlank(stringBook)) {
            BookVo bean = JSONUtil.toBean(stringBook, BookVo.class);
            return bean;
        }
        //防止缓存穿透
        if(stringBook != null) {
            return null;
        }
        //3.没有就通过数据库查询
        Book book = bookMapper.selectById(id);
        //4.如果没有就返回null，并保存到redis中
        if (book == null) {
            stringRedisTemplate.opsForValue().set(key, "",3, TimeUnit.MINUTES);
            return null;
        }
        //5.如果有就返回并添加到redis中
        BookVo vo = BookConvert.toVo(book);
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(vo),30, TimeUnit.MINUTES);
        return vo;
    }

    //借书操作
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean borrowBook(BookBorrowingParam bookBorrowParam) {
        //悲观锁
        Book book = bookMapper.selectByIsbnForUpdate(bookBorrowParam.getIsbn());
        if (book != null && book.getInventory() > 0) {
            bookMapper.updateForBorrow(bookBorrowParam.getIsbn());
            BorrowBookRecord borrowBookRecord = new BorrowBookRecord();
            borrowBookRecord.setUserId(bookBorrowParam.getUserId());
            borrowBookRecord.setBookId(book.getId());
            borrowBookRecord.setBorrowTime(LocalDateTime.now());
            borrowBookRecord.setDeadline(LocalDateTime.now().plusDays(30));
            //保存到借阅表中
            borrowService.save(borrowBookRecord);
            return true;
        }
        return false;
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
