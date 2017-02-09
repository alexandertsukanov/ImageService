package app.repository.storage;

import app.entity.FilesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface FilesEntityRepository extends CrudRepository<FilesEntity, Long> {

    FilesEntity findById(Long id);
    FilesEntity findByTime(Timestamp timestamp);

}
