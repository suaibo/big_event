package com.suai.library.book.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.entity.BorrowBookRecord;
import com.suai.library.book.model.vo.BorrowBookRecordVo;
import com.suai.library.book.repository.BorrowRecordMapper;
import com.suai.library.book.service.BookService;
import com.suai.library.book.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowRecordMapper, BorrowBookRecord> implements BorrowService {
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Autowired
    private BookService bookService;
    //实现连表模糊查询
    @Override
    public IPage<BorrowBookRecordVo> searchBorrowBookRecord(Integer userId, String keyword,Integer pageNum,Integer pageSize) {
        Page<BorrowBookRecordVo> page = new Page<>(pageNum, pageSize);
        IPage<BorrowBookRecordVo> voPage = borrowRecordMapper.searchBorrowByKeyword(
                page, userId, keyword);
        return voPage;
    }

    //还书
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean returnBook(Integer recordId) {
        // 1. 获取借阅记录
        BorrowBookRecord record = getById(recordId);
        if (record == null || record.getReturnTime() != null) {
            return false; // 记录不存在或已归还
        }

        // 2. 设置归还时间
        record.setReturnTime(LocalDateTime.now());

        // 3. 更新借阅记录
        if (!updateById(record)) {
            return false;
        }

        // 4. 增加图书库存
        Book book = bookService.getById(record.getBookId());
        if (book != null) {
            book.setInventory(book.getInventory() + 1);
            return bookService.updateById(book);
        }
        return false;
    }
}
