package com.dm.bookschecker.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dm.bookschecker.domain.dto.BooksSearchParameters;
import com.dm.bookschecker.domain.model.Book;
import com.dm.bookschecker.exception.FtpException;
import com.dm.bookschecker.utils.Constants;

@Service
public class PageNumberUpdateService {

    private static final Logger logger = LogManager.getLogger(PageNumberUpdateService.class);

    @Autowired
    private BookService bookService;
    @Autowired
    private PageService pageService;

    public void updatePageNumber() {
        logger.info("Update page count started");
        synchronized (this) {
            int booksCount = bookService.getBooksCount();
            for (int i = 0; i < booksCount; i += Constants.UPDATE_PER_ITERATION) {
                try {
                    logger.info("Updated books {}", i);
                    List<Book> books = bookService.getBooks(i, Constants.UPDATE_PER_ITERATION);
                    Map<Book, Integer> booksPages = getBooksPageCount(books);
                    updateBooks(booksPages);
                } catch (FtpException e) {
                    logger.error("Failed while updating books. FTP error. Stopping updating.", e);
                    break;
                } catch (RuntimeException e) {
                    logger.warn("Failed while updating books. Continue trying another ones.", e);
                }
            }
        }
        logger.info("Update page count ended");
    }

    @Transactional
    protected void updateBooks(Map<Book, Integer> booksPages) {
        for (Map.Entry<Book, Integer> bookPage : booksPages.entrySet()) {
            Book book = bookPage.getKey();
            book.setPageCount(bookPage.getValue());
            bookService.update(book);
        }
    }

    private Map<Book, Integer> getBooksPageCount(List<Book> books) {
        Map<Book, Integer> booksPages = new HashMap<>();
        for (Book book : books) {
            booksPages.put(book, pageService.getPagesCount(book));
        }
        return booksPages;
    }

}
