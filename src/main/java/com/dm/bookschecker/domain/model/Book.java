package com.dm.bookschecker.domain.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @Column(name = "BOOK_ID")
    private long bookId;
    @Column(name = "ENAME")
    private String name;
    @Column(name = "AUTHOR")
    private String author;
    @Column(name = "PAGE_COUNT")
    private int pageCount;
    @Column(name = "EYEAR")
    private String year;
    @Column(name = "COR_STATE_ID")
    private BigDecimal stateId;
    @Column(name = "IS_VALID")
    private boolean valid;
    @Column(name = "CHECKED")
    private boolean checked;
    @Column(name = "DESCRIPTION")
    private String comment;
    @Column(name = "PREVIEW_PAGES")
    private String previewPages;
    @OneToMany(mappedBy = "bookId", fetch = FetchType.EAGER)
    private List<InvalidPage> invalidPages;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getStateId() {
        return stateId;
    }

    public void setStateId(BigDecimal stateId) {
        this.stateId = stateId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<InvalidPage> getInvalidPages() {
        return invalidPages;
    }

    public void setInvalidPages(List<InvalidPage> invalidPages) {
        this.invalidPages = invalidPages;
    }

    public String getPreviewPages() {
        return previewPages;
    }

    public void setPreviewPages(String previewPages) {
        this.previewPages = previewPages;
    }
}
