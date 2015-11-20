package com.dm.bookschecker.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INVALID_PAGES")
public class InvalidPage {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;
    @Column(name = "BOOK_ID")
    private long bookId;
    @Column(name = "PAGE_NUMBER")
    private int pageNumber;
    @Column(name = "COMMENT")
    private String comment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
