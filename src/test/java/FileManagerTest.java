import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManagerTest {

    private String filePath1 = "src/test/resources/test_image_1.png";

    @Test
    public void testReadImage() {
        System.out.println(Arrays.asList(new File(".").listFiles()));
        FileManager fileManager = new FileManager();
        BufferedImage bufferedImage = fileManager.readImage(filePath1);
        assert(bufferedImage != null);
    }

    @Test
    public void testWriteImage() {
        System.out.println(Arrays.asList(new File(".").listFiles()));
        FileManager fileManager = new FileManager();
        fileManager.readImage(filePath1);
        String expectedFilePath = "src/test/resources/new_test_image_1.png";
        String filePath2 = "src/test/resources/test_image_2.png";

        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath2));
            fileManager.saveImage(bufferedImage);
            assert(new File(expectedFilePath).exists());
            assert(ImageIO.read(new File(filePath2)).equals(ImageIO.read(new File(expectedFilePath))));
        }
        catch (IOException e) {
            System.out.println("Exception in test");
            e.printStackTrace();
            assert (false);
        }
    }
}