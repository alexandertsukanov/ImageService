package app.validator;

import app.model.FileTypes;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileTypeValidator {

    private static final Logger logger = Logger.getLogger(FileTypeValidator.class);

    public static boolean fileFormatCheck(MultipartFile file) throws IOException {
        String type = file.getContentType();
        logger.info("Checking " + type + " format...");
        for (FileTypes f : FileTypes.values()) {
            if (type.equals(f.getType())) {
                return true;
            }
        }
        return false;
    }
}
