package com.dm.bookschecker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dm.bookschecker.dao.InvalidPageDao;
import com.dm.bookschecker.domain.model.InvalidPage;

@Service
public class InvalidPageService {

    @Autowired
    private InvalidPageDao invalidPageDao;

    @Transactional
    @Secured("ROLE_ADMIN")
    public void markPageInvalid(InvalidPage invalidPage) {
        invalidPageDao.saveOrUpdate(invalidPage);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public void removeInvalidPage(long invalidPageId) {
        InvalidPage invalidPage = invalidPageDao.find(invalidPageId);
        if (invalidPage != null) {
            invalidPageDao.delete(invalidPage);
        }
    }
}
