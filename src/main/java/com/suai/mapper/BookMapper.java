package com.suai.mapper;

import com.suai.pojo.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("SELECT title, author, publisher, isbn, inventory" +
            "FROM books " +
            "WHERE " +
            "    title LIKE CONCAT('%', #{keyword}, '%') " +
            "    OR author LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY id DESC ")
    List<Book> findByAuthor(String keyword);

    @Insert("INSERT INTO books(title, author, publisher, isbn, inventory) " +
            "VALUES(#{title}, #{author}, #{publisher}, #{isbn}, #{inventory})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addBook(Book book);

    @Delete("DELETE FROM books WHERE isbn = #{isbn}")
    void deleteBook(String isbn);

    @Update("UPDATE books SET " +
            "title = #{title}, " +
            "author = #{author}, " +
            "publisher = #{publisher}, " +
            "isbn = #{isbn}, " +
            "inventory = #{inventory} " +
            "WHERE isbn = #{isbn}")
    void updateBook(Book book);

    @Select("SELECT * FROM books WHERE isbn = #{isbn} ")
    Book findByIsbn(String isbn);

    @Select("SELECT * FROM books WHERE isbn = #{isbn} FOR UPDATE")
    public Book findForUpdate(String isbn);

}
