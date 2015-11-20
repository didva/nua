package com.dm.bookschecker.service.ftp;

import org.springframework.stereotype.Service;

@Service
public class FtpClientFactory {

    public IFtpClient getFtpClient() {
        return new ApacheFtpClientImpl();
    }

}
