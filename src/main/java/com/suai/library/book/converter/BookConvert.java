package com.suai.library.book.converter;

import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.vo.BookVo;

import java.util.List;
import java.util.stream.Collectors;

public class BookConvert {
    private BookConvert() {};

    public static BookVo toVo(Book book) {
        BookVo bookVo = new BookVo();
        bookVo.setIsbn(book.getIsbn());
        bookVo.setTitle(book.getTitle());
        bookVo.setAuthor(book.getAuthor());
        bookVo.setPublisher(book.getPublisher());
        bookVo.setInventory(book.getInventory());
        return bookVo;
    }

    public static List<BookVo> toVoList(List<Book> books) {
        return books.stream().map(BookConvert::toVo).collect(Collectors.toList());
    }
}
