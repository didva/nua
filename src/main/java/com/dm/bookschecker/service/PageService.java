package com.dm.bookschecker.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dm.bookschecker.dao.PageDao;
import com.dm.bookschecker.domain.dto.PageDTO;
import com.dm.bookschecker.domain.model.Book;
import com.dm.bookschecker.utils.Utils;

@Service
public class PageService {

    @Autowired
    private PageDao pageDao;
    @Autowired
    private BookService bookService;

    public PageDTO getPage(Principal principal, long bookId, int pageNum) {
        Book book = bookService.find(bookId);
        if (book == null || book.getPageCount() < pageNum) {
            throw new IllegalArgumentException();
        }
        if(!isPreviewAllowed(principal, book, pageNum)) {
            throw new AccessDeniedException("Not authorized");
        }
        return new PageDTO(book.getBookId(), pageNum, new String(Base64.encode(pageDao.getPage(book, pageNum))));
    }

    public int getPagesCount(Book book) {
        return pageDao.getPagesCount(book);
    }

    private boolean isPreviewAllowed(Principal principal, Book book, int pageNum) {
        if (principal != null && !StringUtils.isEmpty(principal.getName())) {
            return true;
        }
        String previewPages = book.getPreviewPages();
        if (StringUtils.isEmpty(previewPages)) {
            return false;
        }
        try {
            int startPage = Utils.getStartPage(previewPages);
            int endPage = Utils.getEndPage(previewPages);
            if (pageNum < startPage || pageNum > endPage) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
