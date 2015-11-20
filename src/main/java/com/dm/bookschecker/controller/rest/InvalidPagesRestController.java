package com.dm.bookschecker.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dm.bookschecker.domain.dto.RestResultDTO;
import com.dm.bookschecker.domain.model.InvalidPage;
import com.dm.bookschecker.service.InvalidPageService;

@RestController
@RequestMapping("/rest/invalidPages")
public class InvalidPagesRestController {

    @Autowired
    private InvalidPageService invalidPageService;

    @RequestMapping(method = RequestMethod.POST)
    public RestResultDTO markInvalid(InvalidPage invalidPage) {
        invalidPageService.markPageInvalid(invalidPage);
        return new RestResultDTO(true);
    }

    @RequestMapping(path = "/{invalidPageId}", method = RequestMethod.DELETE)
    public RestResultDTO removeInvalid(@PathVariable long invalidPageId) {
        invalidPageService.removeInvalidPage(invalidPageId);
        return new RestResultDTO(true);
    }
}
