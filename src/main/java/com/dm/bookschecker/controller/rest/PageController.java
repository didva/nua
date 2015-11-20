package com.dm.bookschecker.controller.rest;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dm.bookschecker.domain.dto.PageDTO;
import com.dm.bookschecker.service.PageService;

@RestController
@RequestMapping("/rest/pages")
public class PageController {

    @Autowired
    private PageService pageService;

    @RequestMapping(path = "/{bookId}/{pageNum}", method = RequestMethod.GET)
    public PageDTO getPage(HttpServletResponse httpServletResponse, @PathVariable long bookId,
            @PathVariable int pageNum, Principal principal) throws IOException {

        if (bookId <= 0 || pageNum <= 0) {
            throw new IllegalArgumentException();
        }
        return pageService.getPage(principal, bookId, pageNum);
    }



}
