package com.dm.bookschecker.dao;

import java.util.List;

import com.dm.bookschecker.domain.model.InvalidPage;

public interface InvalidPageDao {

    public InvalidPage find(long id);

    public List<InvalidPage> findByBookId(long bookId);

    public void delete(InvalidPage invalidPage);

    public void saveOrUpdate(InvalidPage invalidPage);

}
