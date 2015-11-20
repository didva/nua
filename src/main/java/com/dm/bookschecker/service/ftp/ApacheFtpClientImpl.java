package com.dm.bookschecker.service.ftp;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.dm.bookschecker.utils.Constants;

public class ApacheFtpClientImpl implements IFtpClient {

    private FTPClient client;

    public ApacheFtpClientImpl() {
        this.client = new FTPClient();
    }

    @Override
    public void connect(String host, String login, String password) throws IOException {
        client.connect(host);
        client.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
        client.login(login, password);
    }

    @Override
    public void downloadFile(String fileName, OutputStream outputStream) throws IOException {
        client.retrieveFile(fileName, outputStream);
    }

    @Override
    public void changeDirectory(String path) throws IOException {
        client.changeWorkingDirectory(path);
    }

    @Override
    public List<String> getImageList() throws IOException {
        FTPFile[] files = client.listFiles(null, file -> file.getName().endsWith(Constants.IMAGE_EXTENSION));
        return Arrays.asList(files).stream().map(FTPFile::getName).collect(toList());
    }

    @Override
    public void close() throws IOException {
        client.disconnect();
    }
}
