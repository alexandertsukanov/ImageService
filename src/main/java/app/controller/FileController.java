package app.controller;

import app.entity.FilesEntity;
import app.exceptions.FileNotSavedException;
import app.service.FileService;
import app.validator.FileTypeValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private static final Logger LOGGER = Logger.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    private FilesEntity saveFileAndEntity(@RequestBody MultipartFile file) throws Exception {
        LOGGER.info("Entering controller...");
        if (FileTypeValidator.fileFormatCheck(file)) {
                return fileService.saveFile(file);
            }
            else{
                LOGGER.error("Error! File format "+ file.getContentType()  + " not supported. Upload canceled.");
                throw new FileNotSavedException("Invalid file format.");
            }
    }

    @ExceptionHandler(FileNotSavedException.class)
    private ResponseEntity<?> InvalidFormatExceptionHandler(HttpServletRequest req, Exception ex) {
        LOGGER.error("Request can't be completed properly. " +
                "\nMethod: " + req.getMethod() + "" +
                "\nURL: " + req.getRequestURI() + "" +
                "\nException Message: " + ex.getMessage());
        return new ResponseEntity<>("Invalid file format.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<?> exceptionHandler(HttpServletRequest req, Exception ex) {
        LOGGER.error("Request can't be completed properly. " +
                "\nMethod: " + req.getMethod() + "" +
                "\nURL: " + req.getRequestURI() + "" +
                "\nException Message: " + ex.getMessage());
        return new ResponseEntity<>("Ooops! Something goes wrong =)", HttpStatus.FORBIDDEN);
    }
}
