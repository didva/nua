package com.dm.bookschecker.service.ftp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface IFtpClient extends AutoCloseable {

    public void connect(String host, String login, String password) throws IOException;

    public void downloadFile(String fileName, OutputStream outputStream) throws IOException;

    public void changeDirectory(String path) throws IOException;

    public List<String> getImageList() throws IOException;

    public void close() throws IOException;
}
