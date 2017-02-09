package app.service.impl;

import app.entity.FilesEntity;
import app.exceptions.FileNotSavedException;
import app.model.FileTypes;
import app.repository.storage.FilesEntityRepository;
import app.service.SaveService;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

        Tika tika = new Tika();
        String type = tika.detect(file.getBytes());

            if (fileFormatCheck(type)) {
                file.transferTo(new File(path + file.getOriginalFilename()));
                FilesEntity filesEntity = new FilesEntity();
                filesEntity.setPath(path + file.getOriginalFilename());
                filesEntity.setTime(new Timestamp(System.currentTimeMillis()));
                filesEntity.setType(type);
                System.out.println(filesEntity);
                logger.debug("The file was saved to " + path);
                return filesEntityRepository.save(filesEntity);
            }
        else {
                logger.error("Error! File format \"" + type + "\" not supported. Upload canceled.");
                throw new FileNotSavedException("Invalid file format.");
            }
    }

   private boolean fileFormatCheck(String s){
        for(FileTypes f : FileTypes.values()){
            if(s.equals(f.getType())){
                return true;
            }
        }
        return false;
    }
}
