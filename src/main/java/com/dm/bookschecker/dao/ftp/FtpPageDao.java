package com.dm.bookschecker.dao.ftp;

import static java.util.stream.Collectors.toList;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.dm.bookschecker.dao.PageDao;
import com.dm.bookschecker.domain.model.Book;
import com.dm.bookschecker.exception.FtpException;
import com.dm.bookschecker.service.PageCacheService;
import com.dm.bookschecker.service.ftp.FtpClientFactory;
import com.dm.bookschecker.service.ftp.IFtpClient;
import com.dm.bookschecker.utils.Constants;
import com.dm.bookschecker.utils.Utils;

@Repository
public class FtpPageDao implements PageDao {

    private final ExecutorService executorService = Executors.newFixedThreadPool(Constants.PRELOAD_THREADS_COUNT);

    @Value("${ftp.server.url}")
    private String host;
    @Value("${ftp.server.login}")
    private String login;
    @Value("${ftp.server.password}")
    private String password;
    @Value("${ftp.temp.directory}")
    private String tempDirectory;

    @Autowired
    private FtpClientFactory ftpClientFactory;
    @Autowired
    private PageCacheService pageCacheService;

    @PreDestroy
    protected void destroy() {
        executorService.shutdown();
    }

    @Override
    public byte[] getPage(Book book, int pageNum) {
        String bookPath = Utils.createPath(tempDirectory, book.getBookId());
        String pagePath = Utils.createPath(bookPath, String.valueOf(pageNum) + Constants.IMAGE_EXTENSION);
        File page = new File(pagePath);
        String bookFolder = book.getStateId().toString();
        if (!page.exists()) {
            download(bookFolder, book.getBookId(), pageNum - 1, 1);
        }
        executorService.execute(() -> download(bookFolder, book.getBookId(), pageNum, Constants.PRELOAD_AMOUNT));
        return getPageBytes(pagePath);
    }

    @Override
    public int getPagesCount(Book book) {
        try (IFtpClient ftpClient = ftpClientFactory.getFtpClient()) {
            connectAndChangeDir(book.getStateId().toString(), book.getBookId(), ftpClient);
            return ftpClient.getImageList().size();
        } catch (IOException e) {
            throw new FtpException();
        }
    }

    private byte[] getPageBytes(String pagePath) {
        File page = new File(pagePath);
        if (!page.exists()) {
            throw new FtpException();
        }
        try (FileInputStream fis = new FileInputStream(pagePath)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(fis, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    private void download(String bookFolder, long bookId, int skip, int limit) {
        try (IFtpClient ftpClient = ftpClientFactory.getFtpClient()) {
            connectAndChangeDir(bookFolder, bookId, ftpClient);

            List<String> fileNamesStr = getSortedFileNames(skip, limit, ftpClient);
            String bookPath = Utils.createPath(tempDirectory, bookId);
            createDirIfNotExist(tempDirectory);
            createDirIfNotExist(bookPath);

            downloadPages(ftpClient, fileNamesStr, bookPath);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    private void downloadPages(IFtpClient ftpClient, List<String> fileNamesStr, String bookPath) throws IOException {
        for (String fileName : fileNamesStr) {
            String pagePath = Utils.createPath(bookPath, fileName);
            File page = new File(pagePath);
            if (!page.exists()) {
                try (FileOutputStream fos = new FileOutputStream(page)) {
                    ftpClient.downloadFile(fileName, fos);
                }
            }
            pageCacheService.add(pagePath);
        }
    }

    private void connectAndChangeDir(String booksFolder, long bookId, IFtpClient ftpClient) throws IOException {
        ftpClient.connect(host, login, password);
        ftpClient.changeDirectory(booksFolder);
        ftpClient.changeDirectory(Long.toString(bookId));
    }

    private List<String> getSortedFileNames(int skip, int limit, IFtpClient ftpClient) throws IOException {
        return ftpClient.getImageList().stream().map(s -> s.replace(Constants.IMAGE_EXTENSION, ""))
                .mapToInt(Integer::parseInt).sorted().skip(skip).limit(limit)
                .mapToObj(i -> String.valueOf(i) + Constants.IMAGE_EXTENSION).collect(toList());
    }

    private void createDirIfNotExist(String path) {
        File bookDirectoryFile = new File(path);
        if (!bookDirectoryFile.exists()) {
            if (!bookDirectoryFile.mkdir()) {
                throw new FtpException();
            }
        }
    }

}
