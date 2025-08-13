package com.suai.library.book.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suai.library.book.model.entity.BorrowBookRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowBookRecord> {

}
