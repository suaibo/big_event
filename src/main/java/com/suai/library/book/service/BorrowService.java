package com.suai.library.book.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suai.library.book.model.entity.BorrowBookRecord;
import com.suai.library.book.model.vo.BorrowBookRecordVo;

public interface BorrowService extends IService<BorrowBookRecord> {

    //模糊查询借阅记录
    IPage<BorrowBookRecordVo> searchBorrowBookRecord(Integer userId, String keyword, Integer pageNum, Integer pageSize);

    //还书
    boolean returnBook(Integer recordId);
}
