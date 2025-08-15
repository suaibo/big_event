package com.suai.library.book.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suai.library.book.model.entity.BorrowBookRecord;
import com.suai.library.book.model.vo.BorrowBookRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowBookRecord> {

    IPage<BorrowBookRecordVo> searchBorrowByKeyword(IPage<BorrowBookRecordVo> page,@Param("userId") Integer userId,
                                                    @Param("keyword") String keyword);
}
