package com.suai.library.book.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suai.library.book.model.entity.Book;
import org.apache.ibatis.annotations.*;


@Mapper
public interface BookMapper extends BaseMapper<Book> {

    @Select("SELECT * FROM book WHERE isbn = #{isbn} FOR UPDATE")
    Book selectByIsbnForUpdate(String isbn);

    @Update("UPDATE book SET inventory = inventory - 1 WHERE isbn = #{isbn} AND inventory > 0")
    void updateForBorrow(String isbn);
}
