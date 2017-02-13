package app.controller;

import app.entity.FilesEntity;
import app.exceptions.FileNotSavedException;
import app.model.FileTypes;
import app.service.SaveService;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private static final Logger LOGGER = Logger.getLogger(FileController.class);

    @Autowired
    private SaveService saveService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    private FilesEntity saveFileAndEntity(@RequestBody MultipartFile file) throws Exception {
        LOGGER.info("Entering controller...");
        Tika tika = new Tika();
        String type = tika.detect(file.getBytes());
            if (fileFormatCheck(type)) {
                return saveService.saveFile(file, type);
            }
            else{
                LOGGER.error("Error! File format \"" + type + "\" not supported. Upload canceled.");
                throw new FileNotSavedException();
            }
    }

    @ExceptionHandler(FileNotSavedException.class)
    private ResponseEntity<?> InvalidFormatExceptionHandler() {
        return new ResponseEntity<>("Invalid file format.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> exceptionHandler() {
        return new ResponseEntity<>("Ooops! Something goes wrong =)", HttpStatus.FORBIDDEN);
    }

    private boolean fileFormatCheck(String s) {
        for (FileTypes f : FileTypes.values()) {
            if (s.equals(f.getType())) {
                return true;
            }
        }
        return false;
    }
}
