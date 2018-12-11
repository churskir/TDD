import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.Assert.*;

public class FiltersTest {

    @Test
    public void greyScaleFilter() throws Exception{
        File file = new File("src/test/resources/obrazek.png");
        BufferedImage bufferedImage = ImageIO.read(file);
        BufferedImage result = Filters.GreyScaleFilter(bufferedImage);
        for(int i = 0; i < result.getWidth();i++) {
            for(int j = 0; j < result.getHeight();j++) {
                Color color = new Color(result.getRGB(i,j));
                assertEquals(color.getRed(), color.getBlue());
                assertEquals(color.getBlue(), color.getGreen());
            }
        }

    }
}