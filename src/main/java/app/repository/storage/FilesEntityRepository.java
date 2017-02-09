package app.repository.storage;

import app.entity.FilesEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface FilesEntityRepository extends CrudRepository<FilesEntity, Long> {

    FilesEntity findById(Long id);
    FilesEntity findByTime(Timestamp timestamp);

}
