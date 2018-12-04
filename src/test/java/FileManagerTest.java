import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManagerTest {

    private String filePath1 = "../resources/test_image_1.img";

    @Test
    public void testReadImage() {
        FileManager fileManager = new FileManager();
        BufferedImage bufferedImage = fileManager.readImage(filePath1);
        assert(bufferedImage != null);
    }

    @Test
    public void testWriteImage() {
        FileManager fileManager = new FileManager();
        fileManager.readImage(filePath1);
        String expectedFilePath = "../resources/new_test_image_1.img";
        String filePath2 = "../resources/test_image_2.img";

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