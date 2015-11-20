package com.dm.bookschecker.dao;

import com.dm.bookschecker.domain.model.Book;

public interface PageDao {

    public byte[] getPage(Book book, int pageNum);

    public int getPagesCount(Book book);

}
