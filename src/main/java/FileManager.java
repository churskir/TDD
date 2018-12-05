import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManager {

    private String dirPath;
    private String fileName;

    public FileManager(String dirPath) {
        this.dirPath = dirPath;
    }

    public BufferedImage readImage(String fileName) {
        this.fileName = fileName;
        try {
            return ImageIO.read(new File(dirPath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveImage(BufferedImage bufferedImage) {
        try {
            ImageIO.write(bufferedImage, getExtension(), new File(dirPath + "new_" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getExtension() {
        int i = fileName.lastIndexOf(".");
        if (i == 0) {
            return "";
        }
        return fileName.substring(i + 1);
    }
}
