package app.controller;

import app.entity.FilesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import app.service.SaveService;



@RestController
public class FileController {

    @Autowired
    SaveService saveService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FilesEntity saveFile(@RequestParam(value = "file") MultipartFile multipartFile) throws Exception {
        return saveService.saveFile(multipartFile);
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(){
        return "Error! The file was not saved!";
    }
}
