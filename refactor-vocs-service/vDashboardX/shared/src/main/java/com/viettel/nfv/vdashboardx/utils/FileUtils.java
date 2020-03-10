package com.viettel.nfv.vdashboardx.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.codec.multipart.FilePart;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    private static String currentFilePath = "";

    private FileUtils(){}

    public static boolean createFolder(String pathUploadFile){
        if (StringUtils.isEmpty(pathUploadFile)) {
            return false;
        }

        File folder = new File(pathUploadFile);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        return true;
    }

    public static File createUploadFile(FilePart part, String pathUploadFile) throws IOException {
        final String filename = part.filename();
        FileUtils.currentFilePath = pathUploadFile + filename;
        File clientFile = new File(FileUtils.currentFilePath);
        // if a file with the same name already exists in a upload folder, delete and recreate it
        if (clientFile.exists()) {
            Files.delete(Paths.get(pathUploadFile + filename));
        }

        boolean isCreatedFile = clientFile.createNewFile();
        if (!isCreatedFile) {
            throw new IOException("Failed to create the upload file");
        }

        return clientFile;
    }

    public static void deleteUploadFile() throws IOException{
        if (StringUtils.isEmpty(FileUtils.currentFilePath)) {
            return;
        }

        File file = new File(FileUtils.currentFilePath);
        if (!file.exists()) {
            return;
        }

        Files.delete(Paths.get(FileUtils.currentFilePath));
        FileUtils.currentFilePath = "";
    }

}
