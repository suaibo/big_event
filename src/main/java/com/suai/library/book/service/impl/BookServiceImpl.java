package com.suai.library.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.entity.BorrowBookRecord;
import com.suai.library.book.model.request.BookBorrowingParam;
import com.suai.library.book.repository.BookMapper;
import com.suai.library.book.service.BookService;
import com.suai.library.book.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowService borrowService;

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
