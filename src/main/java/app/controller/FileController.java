package app.controller;

import app.entity.FilesEntity;
import app.service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    SaveService saveService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FilesEntity saveFileAndEntity(@RequestBody MultipartFile file) throws Exception {
        return saveService.saveFile(file);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(){
        return new ResponseEntity<>("Ooops! Something goes wrong =)",HttpStatus.FORBIDDEN);
    }
}
