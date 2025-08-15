package com.suai.library.book.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suai.library.book.converter.BookConvert;
import com.suai.library.book.converter.BorrowConvert;
import com.suai.library.book.model.entity.Book;
import com.suai.library.book.model.entity.BorrowBookRecord;
import com.suai.library.book.model.request.BookBorrowingParam;
import com.suai.library.book.model.request.BookPageParam;
import com.suai.library.book.model.vo.BookPageVo;
import com.suai.library.book.model.vo.BookVo;
import com.suai.library.book.model.vo.BorrowBookRecordVo;
import com.suai.library.book.model.vo.RecordPageVo;
import com.suai.library.book.service.BorrowService;
import com.suai.library.common.resp.Result;
import com.suai.library.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private RedisTemplate redisTemplate;

    //增加图书，管理员操作
    @PostMapping("manager")
    @SaCheckRole("admin")
    public Result addBook(@RequestBody Book book) {
        if (bookService.save(book)) {
            return Result.success();
        }
        return Result.error("操作失败");
    }
    //分页模糊查询
    @GetMapping("/page_book")
    public Result<BookPageVo> fuzzyQuery(BookPageParam param) {
        IPage<Book> iPage = new Page<>(param.getPageNum(), param.getPageSize());
        //wrapper实现模糊查询
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<Book>()
                        .select(Book::getIsbn, Book::getTitle, Book::getAuthor)
                .and(i->i.like(Book::getAuthor,param.getKeyword())
                        .or()
                        .like(Book::getTitle,param.getKeyword())
                        .or()
                        .like(Book::getPublisher,param.getKeyword())
                        .or()
                        .like(Book::getIsbn,param.getKeyword()));
        //查询操作
        IPage<Book> page = bookService.page(iPage, wrapper);
        List<Book> records = page.getRecords();
        BookPageVo bookPageVo = new BookPageVo();
        List<BookVo> voList = BookConvert.toVoList(records);
        bookPageVo.setList(voList);
        bookPageVo.setTotal(page.getTotal());
        //封装返回
        return Result.success(bookPageVo);
    }

    //更新图书，管理员操作
    @PutMapping("manager")
    @SaCheckRole("admin")
    public Result updateBook(@RequestBody Book book) {
        if(bookService.updateById(book)){
            return Result.success();
        }
        return Result.error("操作失败");
    }

    //根据isbn查询，管理员接口
    @GetMapping("manager")
    @SaCheckRole("admin")
    public Result findByIsbn(@RequestParam String isbn) {
        Wrapper<Book> wrapper = Wrappers.<Book>lambdaQuery().eq(Book::getIsbn, isbn);
        Book book = bookService.getOne(wrapper);
        if(book != null) {
            return Result.success(book);
        }
        return Result.error("图书不存在");
    }

    //根据isbn删除图书，管理员接口
    @DeleteMapping("manager")
    @SaCheckRole("admin")
    public Result deleteBook(@RequestParam String isbn) {
        Wrapper<Book> wrapper = Wrappers.<Book>lambdaQuery().eq(Book::getIsbn, isbn);
        Book book = bookService.getOne(wrapper);
        if(book != null) {
            if(bookService.removeById(book)){
                return Result.success();
            }
            return Result.error("操作失败");
        }
        return Result.error("图书不存在");
    }
//借阅操作
    @PutMapping("/borrow")
    public Result borrowBook(@RequestBody BookBorrowingParam bookBorrowParam) {
        boolean flag = bookService.borrowBook(bookBorrowParam);
        if(flag){
            return Result.success();
        }
        return Result.error("操作失败");
    }

    //归还操作
    @PutMapping("/return")
    public Result returnBook(@RequestParam Integer recordId) {
        if (borrowService.returnBook(recordId)) {
            return Result.success();
        }
        return Result.error("操作失败");
    }
    //模糊查询借阅记录
    @GetMapping("/borrow/record_info")
    public Result<RecordPageVo> getBorrowBookInfo(@RequestParam Integer userId, @RequestParam String keyword,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<BorrowBookRecordVo> borrowBookRecordVoIPage = borrowService.searchBorrowBookRecord(userId, keyword, pageNum, pageSize);
        List<BorrowBookRecordVo> records = borrowBookRecordVoIPage.getRecords();
        RecordPageVo recordPageVo = new RecordPageVo();
        recordPageVo.setList(records);
        recordPageVo.setTotal(borrowBookRecordVoIPage.getTotal());
        return Result.success(recordPageVo);
    }
//    //分页模糊查询
//    @GetMapping("/page_book")
//    public Result<BookPageVo> fuzzyQuery(BookPageParam param) {
//        IPage<Book> iPage = new Page<>(param.getPageNum(), param.getPageSize());
//        //wrapper实现模糊查询
//        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<Book>()
//                .select(Book::getIsbn, Book::getTitle, Book::getAuthor)
//                .and(i->i.like(Book::getAuthor,param.getKeyword())
//                        .or()
//                        .like(Book::getTitle,param.getKeyword())
//                        .or()
//                        .like(Book::getPublisher,param.getKeyword())
//                        .or()
//                        .like(Book::getIsbn,param.getKeyword()));
//        //查询操作
//        IPage<Book> page = bookService.page(iPage, wrapper);
//        List<Book> records = page.getRecords();
//        BookPageVo bookPageVo = new BookPageVo();
//        List<BookVo> voList = BookConvert.toVoList(records);
//        bookPageVo.setList(voList);
//        bookPageVo.setTotal(page.getTotal());
//        //封装返回
//        return Result.success(bookPageVo);
//    }

    //增加或修改记录
    @PostMapping("/borrow/record")
    public Result addBorrowBookRecord(@RequestBody BorrowBookRecord record) {
        record.setBorrowTime(LocalDateTime.now());
        record.setDeadline(LocalDateTime.now().plusDays(30));
        if (borrowService.saveOrUpdate(record)){
            return Result.success();
        }

        return Result.error("操作失败");
    }

}
