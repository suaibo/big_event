package com.suai.library.book.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.entity.BorrowBookRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
