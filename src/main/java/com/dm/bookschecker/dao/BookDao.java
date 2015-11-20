package com.dm.bookschecker.dao;

import java.util.List;

import com.dm.bookschecker.domain.dto.OrderType;
import com.dm.bookschecker.domain.model.Book;

public interface BookDao {

    public List<Book> find(String orderBy, OrderType order);

    public List<Book> find(Boolean valid, Boolean checked, String orderBy, OrderType order, String searchText);

    public Book find(long id);

    public List<Book> find(int skip, int limit);

    public void update(Book book);

    public int getBookCount();

    public long getPagesCount();

}
