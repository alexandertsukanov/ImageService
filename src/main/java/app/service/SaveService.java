package app.service;

import app.entity.FilesEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface SaveService {

        FilesEntity saveFile(MultipartFile file) throws Exception;

}
