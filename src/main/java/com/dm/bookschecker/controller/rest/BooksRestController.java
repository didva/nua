package com.dm.bookschecker.controller.rest;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dm.bookschecker.domain.dto.BooksDTO;
import com.dm.bookschecker.domain.dto.BooksSearchParameters;
import com.dm.bookschecker.domain.dto.RestResultDTO;
import com.dm.bookschecker.domain.model.Book;
import com.dm.bookschecker.exception.IllegalRestArguments;
import com.dm.bookschecker.service.BookService;
import com.dm.bookschecker.utils.Utils;
import com.dm.bookschecker.validator.BooksParametersValidator;

@RestController
@RequestMapping("/rest/books")
public class BooksRestController {

    @Autowired
    private BooksParametersValidator booksParametersValidator;
    @Autowired
    private BookService bookService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(booksParametersValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public BooksDTO getBooks(@Valid BooksSearchParameters booksSearchParameters, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalRestArguments(bindingResult);
        }
        return bookService.searchBooks(booksSearchParameters);
    }

    @RequestMapping(path = "/{bookId}", method = RequestMethod.GET)
    public Book getBook(@PathVariable long bookId, Principal principal) {
        Book book = bookService.find(bookId);
        if (principal != null && !StringUtils.isEmpty(principal.getName())) {
            return book;
        }
        book.setPageCount(Utils.getEndPage(book.getPreviewPages()));
        return book;
    }

    @RequestMapping(path = "/{bookId}", method = RequestMethod.PUT)
    public RestResultDTO updateStatus(@PathVariable long bookId, Book book) {
        book.setBookId(bookId);
        bookService.updateStatus(book);
        return new RestResultDTO(true);
    }

}
