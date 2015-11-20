package com.dm.bookschecker.domain.dto;

public class MainInfo {

    private long booksCount;
    private long pagesCount;

    public MainInfo() {
    }

    public MainInfo(long booksCount, long pagesCount) {
        this.booksCount = booksCount;
        this.pagesCount = pagesCount;
    }

    public long getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(long booksCount) {
        this.booksCount = booksCount;
    }

    public long getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(long pagesCount) {
        this.pagesCount = pagesCount;
    }
}
