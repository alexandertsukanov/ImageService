package app.service.impl;

import app.entity.FilesEntity;
import app.model.FileTypes;
import app.repository.storage.FilesEntityRepository;
import app.service.SaveService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;

@Service
public class SaveServiceImpl implements SaveService {

    private static final Logger logger = Logger.getLogger(SaveServiceImpl.class);

    @Autowired
    private FilesEntityRepository filesEntityRepository;

    @Value("${filepath}")
    String path;

    @Override
    public FilesEntity saveFile(MultipartFile file) throws Exception {

        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        try {
            if (fileExtension.equals(FileTypes.valueOf(fileExtension).toString())) {
                file.transferTo(new File(path + file.getOriginalFilename()));
                FilesEntity filesEntity = new FilesEntity();
                filesEntity.setPath(path + file.getOriginalFilename());
                filesEntity.setTime(new Timestamp(System.currentTimeMillis()));
                filesEntity.setType(fileExtension);
                System.out.println(filesEntity);
                logger.debug("The file was saved to " + path);
                return filesEntityRepository.save(filesEntity);
            }
        } catch (Exception ex) {
            logger.error("Error! File format \"" + fileExtension + "\" not supported." +
                    " Upload canceled.");
            throw new Exception("Invalid file format.");
        }
        return null;
    }
}
