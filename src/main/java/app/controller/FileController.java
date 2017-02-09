package app.controller;

import app.entity.FilesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import app.service.SaveService;

@RestController
public class FileController {

    @Autowired
    SaveService saveService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FilesEntity saveFile(@RequestBody MultipartFile file) throws Exception {
        return saveService.saveFile(file);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(){
        return new ResponseEntity<>("Ooops! Something goes wrong =)",HttpStatus.FORBIDDEN);
    }
}
