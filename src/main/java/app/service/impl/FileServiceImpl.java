package app.service.impl;

import app.entity.FilesEntity;
import app.repository.storage.FilesEntityRepository;
import app.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = Logger.getLogger(FileServiceImpl.class);

    @Autowired
    private FilesEntityRepository filesEntityRepository;

    @Value("${savePath}")
    private String savePath;

    @Override
    public FilesEntity saveFile(MultipartFile file) throws Exception {
        logger.info("File Service parameters:" +
                "\nName: " + file.getName() +
                "\nFile Type: " + file.getContentType() +
                "\nSaving file to " + savePath);
        file.transferTo(new File(savePath + file.getOriginalFilename()));
        logger.info("Saving file to database...");
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setPath(savePath + file.getOriginalFilename());
        filesEntity.setTime(new Timestamp(System.currentTimeMillis()));
        filesEntity.setType(file.getContentType());
        logger.info("The file was saved to database.");
        logger.info("The file was saved to upload storage at path:" + savePath);
        return filesEntityRepository.save(filesEntity);
    }
}
