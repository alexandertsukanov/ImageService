package app.service;

import app.entity.FilesEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FileService {

    FilesEntity saveFile(MultipartFile file) throws Exception;

    public boolean fileFormatCheck(MultipartFile s) throws IOException;
}
