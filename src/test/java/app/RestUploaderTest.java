package app;

import app.entity.FilesEntity;
import app.exceptions.FileNotSavedException;
import app.repository.storage.FilesEntityRepository;
import app.service.SaveService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestUploaderTest {

    @Autowired
    SaveService saveService;

    @Autowired
    FilesEntityRepository filesEntityRepository;

    final static String PATH = "/anypath/";

    @Test
    public void entityTest(){
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setPath(PATH);
        Timestamp time = filesEntity.setTime(new Timestamp(System.currentTimeMillis()));
        filesEntity.setType("TEXT");
        filesEntityRepository.save(filesEntity);

        FilesEntity fromDataBaseFileEntity = filesEntityRepository.findByTime(time);
        Assert.assertEquals(filesEntity, fromDataBaseFileEntity);
        Assert.assertEquals(fromDataBaseFileEntity.getPath(), PATH);
        Assert.assertEquals(fromDataBaseFileEntity.getType(), "TEXT");
        Assert.assertEquals(fromDataBaseFileEntity.getTime(), time);
        filesEntityRepository.delete(fromDataBaseFileEntity.getId());
    }

    @Test(expected = FileNotSavedException.class)
    public void FileNotSavedExceptionTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.img", "TEXT/plain", "".getBytes());
        saveService.saveFile(file, "filetype");
    }
}
