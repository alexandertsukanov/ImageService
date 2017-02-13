package app.service.impl;

import app.entity.FilesEntity;
import app.model.FileTypes;
import app.repository.storage.FilesEntityRepository;
import app.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class);

    @Autowired
    private FilesEntityRepository filesEntityRepository;

    @Value("${filepath}")
    private String path;

    @Override
    public FilesEntity saveFile(MultipartFile file) throws Exception {
        LOGGER.info("File Service parameters:" +
                "\nName: " + file.getName() +
                "\nFile Type: " + file.getContentType());
        LOGGER.info("Saving file to " + path);
        file.transferTo(new File(path + file.getOriginalFilename()));
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setPath(path + file.getOriginalFilename());
        filesEntity.setTime(new Timestamp(System.currentTimeMillis()));
        filesEntity.setType(file.getContentType());
        LOGGER.debug("The file was saved to " + path);
        return filesEntityRepository.save(filesEntity);
    }

    @Override
    public boolean fileFormatCheck(MultipartFile file) throws IOException {
        String type = file.getContentType();
        for (FileTypes f : FileTypes.values()) {
            if (type.equals(f.getType())) {
                return true;
            }
        }
        return false;
    }
}
