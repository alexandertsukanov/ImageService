package app.service.impl;

import app.entity.FilesEntity;
import app.model.FileTypes;
import app.repository.storage.FilesEntityRepository;
import app.service.SaveService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;

@Service
public class SaveServiceImpl implements SaveService {

    private static final Logger LOGGER = Logger.getLogger(SaveServiceImpl.class);

    @Autowired
    private FilesEntityRepository filesEntityRepository;

    @Value("${filepath}")
    private String path;

    @Override
    public FilesEntity saveFile(MultipartFile file, String type) throws Exception {
        LOGGER.info("Save Service Entering parameters: " + file.getName() + " " + type);
        LOGGER.info("Saving file to " + path);
        file.transferTo(new File(path + file.getOriginalFilename()));
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setPath(path + file.getOriginalFilename());
        filesEntity.setTime(new Timestamp(System.currentTimeMillis()));
        filesEntity.setType(type);
        LOGGER.debug("The file was saved to " + path);
        return filesEntityRepository.save(filesEntity);
    }
}
