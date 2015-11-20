package com.dm.bookschecker.domain.dto;

public class BooksSearchParameters extends SearchParameters {

    private Boolean checked;
    private Boolean valid;
    private String searchText;

    public BooksSearchParameters() {
        setOrderBy("bookId");
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
