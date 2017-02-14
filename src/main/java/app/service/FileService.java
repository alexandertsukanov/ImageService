package app.service;

import app.entity.FilesEntity;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

    FilesEntity saveFile(MultipartFile file) throws Exception;

}
