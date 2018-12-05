import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class FileManagerTest {

    private String dirPath = "src/test/resources/";
    private String fileName1 = "test_image_1.png";

    @Test
    public void testReadImage() {
        FileManager fileManager = new FileManager(dirPath);
        BufferedImage bufferedImage = fileManager.readImage(fileName1);
        assert(bufferedImage != null);
    }

    @Test
    public void testWriteImage() {
        FileManager fileManager = new FileManager(dirPath);
        fileManager.readImage(fileName1);
        String expectedFilePath = dirPath + "new_test_image_1.png";
        String filePath = dirPath + "test_image_2.png";

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            fileManager.saveImage(bufferedImage);
            File expectedFile = new File(expectedFilePath);
            expectedFile.deleteOnExit();
            assert(expectedFile.exists());
        }
        catch (IOException e) {
            System.out.println("Exception in test");
            e.printStackTrace();
            assert (false);
        }
    }
}