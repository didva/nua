package com.dm.bookschecker.service.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class SimplePasswordGenerator implements PasswordGenerator {

    private SecureRandom random = new SecureRandom();

    @Override
    public String generatePassword() {
        return new BigInteger(130, random).toString(32);
    }
}
