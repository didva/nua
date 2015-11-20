package com.dm.bookschecker.domain.dto;

import java.util.List;

import com.dm.bookschecker.domain.model.Book;

public class BooksDTO {

    private int total;
    private List<Book> books;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
