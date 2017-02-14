package app.validator;

import app.model.FileTypes;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileTypeValidator {

    private static final Logger LOGGER = Logger.getLogger(FileTypeValidator.class);

    public static boolean fileFormatCheck(MultipartFile file) throws IOException {
        String type = file.getContentType();
        LOGGER.info("Checking " + type + " format...");
        for (FileTypes f : FileTypes.values()) {
            if (type.equals(f.getType())) {
                return true;
            }
        }
        return false;
    }
}
