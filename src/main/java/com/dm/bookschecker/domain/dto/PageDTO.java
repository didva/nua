package com.dm.bookschecker.domain.dto;

public class PageDTO {

    private long bookId;
    private int pageNum;
    private String content;

    public PageDTO() {
    }

    public PageDTO(long bookId, int pageNum, String content) {
        this.bookId = bookId;
        this.pageNum = pageNum;
        this.content = content;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
