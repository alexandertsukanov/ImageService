package app.service;

import app.entity.FilesEntity;
import org.springframework.web.multipart.MultipartFile;


public interface SaveService {

        FilesEntity saveFile(MultipartFile file) throws Exception;

}
