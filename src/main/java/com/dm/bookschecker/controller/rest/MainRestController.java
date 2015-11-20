package com.dm.bookschecker.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dm.bookschecker.domain.dto.MainInfo;
import com.dm.bookschecker.service.BookService;

@RestController
@RequestMapping("/rest/main")
public class MainRestController {

    @Autowired
    private BookService bookService;

    @RequestMapping
    public MainInfo getMainInfo() {
        return bookService.getMainInfo();
    }

}
