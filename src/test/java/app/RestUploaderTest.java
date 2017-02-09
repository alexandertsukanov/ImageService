package app;

import app.entity.FilesEntity;
import app.exceptions.InvalidFormatFileException;
import app.model.FileTypes;
import app.repository.storage.FilesEntityRepository;
import app.service.SaveService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestUploaderTest {

    @Autowired
    SaveService saveService;

    @Autowired
    FilesEntityRepository filesEntityRepository;

    @Test
    public void entityTest(){
        FilesEntity filesEntity = new FilesEntity();
        filesEntity.setPath("/user");
        Timestamp time = filesEntity.setTime(new Timestamp(System.currentTimeMillis()));
        filesEntity.setType("text");
        filesEntityRepository.save(filesEntity);

        FilesEntity fromDataBaseFileEntity = filesEntityRepository.findByTime(time);
        Assert.assertEquals(filesEntity, fromDataBaseFileEntity);
        Assert.assertEquals(fromDataBaseFileEntity.getPath(), "/user");
        Assert.assertEquals(fromDataBaseFileEntity.getType(), "text");
        Assert.assertEquals(fromDataBaseFileEntity.getTime(), time);
    }

    @Test(expected = InvalidFormatFileException.class)
    public void invalidFileFormatExceptionTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "hello.img", "text/plain", "".getBytes());
        saveService.saveFile(file);
    }
}
