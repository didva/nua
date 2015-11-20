package com.dm.bookschecker.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dm.bookschecker.dao.BookDao;
import com.dm.bookschecker.domain.dto.BooksDTO;
import com.dm.bookschecker.domain.dto.BooksSearchParameters;
import com.dm.bookschecker.domain.dto.MainInfo;
import com.dm.bookschecker.domain.dto.OrderType;
import com.dm.bookschecker.domain.model.Book;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private InvalidPageService invalidPageService;
    @Autowired
    private PageService pageService;

    @Transactional
    public void update(Book book) {
        bookDao.update(book);
    }

    @Transactional
    public MainInfo getMainInfo() {
        return new MainInfo(getBooksCount(), getPagesCount());
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public void updateStatus(Book book) {
        Book existingBook = bookDao.find(book.getBookId());
        if (book.isValid() && !existingBook.getInvalidPages().isEmpty()) {
            throw new IllegalArgumentException();
        }
        existingBook.setChecked(book.isChecked());
        existingBook.setValid(book.isValid());
        bookDao.update(book);
    }

    @Transactional
    public List<Book> getBooks(int skip, int limit) {
        return bookDao.find(skip, limit);
    }

    @Transactional
    public BooksDTO searchBooks(BooksSearchParameters params) {
        List<Book> books = bookDao.find(params.getValid(), params.getChecked(), params.getOrderBy(),
                OrderType.valueOf(params.getOrder().toUpperCase()), params.getSearchText());
        return getBooksDTO(getFilteredBooks(params, books), books.size());
    }

    @Transactional
    public Book find(long bookId) {
        return bookDao.find(bookId);
    }

    @Transactional
    public int getBooksCount() {
        return bookDao.getBookCount();
    }

    private BooksDTO getBooksDTO(List<Book> books, int total) {
        BooksDTO booksDTO = new BooksDTO();
        booksDTO.setBooks(books);
        booksDTO.setTotal(total);
        return booksDTO;
    }

    private List<Book> getFilteredBooks(BooksSearchParameters params, List<Book> books) {
        int startIndex = params.getSkip();
        if (startIndex >= books.size()) {
            return Collections.emptyList();
        }
        int endIndex = params.getLimit() + startIndex;
        if (endIndex > books.size()) {
            endIndex = books.size();
        }
        return books.subList(startIndex, endIndex);
    }

    private long getPagesCount() {
        return bookDao.getPagesCount();
    }

}
