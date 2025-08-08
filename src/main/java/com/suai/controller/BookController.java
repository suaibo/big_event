package com.suai.controller;

import com.suai.pojo.Book;
import com.suai.pojo.PageReqDto;
import com.suai.pojo.PageRespDto;
import com.suai.pojo.Result;
import com.suai.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/add")
    public Result addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return Result.success();
    }
    //分页查询
    @GetMapping("/search")
    public Result<PageRespDto<Book>> findPageOfBook(@RequestBody PageReqDto pageReqDto,@RequestParam String keyword) {
        PageRespDto<Book> byAuthor = bookService.findByAuthor(keyword,pageReqDto);
        return Result.success(byAuthor);
    }

    @PutMapping("/update")
    public Result updateBook(@RequestBody Book book) {
        bookService.updateBook(book);
        return Result.success();
    }

    @GetMapping
    public Result findByIsbn(@RequestParam String isbn) {
        Book byIsbn = bookService.findByIsbn(isbn);
        if(byIsbn != null) {
            return Result.success(byIsbn);
        }
        return Result.error("图书不存在");
    }

    @DeleteMapping("/delete")
    public Result deleteBook(@RequestParam String isbn) {
        Book byIsbn = bookService.findByIsbn(isbn);
        if(byIsbn != null) {
            bookService.deleteBook(isbn);
            return Result.success();
        }
        return Result.error("图书不存在");
    }

    @PutMapping("/borrow")
    public Result borrowBook(@RequestParam String isbn, @RequestParam Integer bookId) {


    }
    //增加购物车里面的东西
    @PostMapping("/cart")
    public Result addCart(@RequestParam String isbn,@RequestParam Integer userId,@RequestParam Integer quantity) {
        redisTemplate.opsForHash().increment("cart:"+userId,isbn,quantity);
        return Result.success();
    }

    //获取购物车里的信息
    @GetMapping("/cart")
    public Result getCart(@RequestParam Integer userId) {
        Map<String, Integer> entries = redisTemplate.opsForHash().entries("cart:" + userId);

    }
}
