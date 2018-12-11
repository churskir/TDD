import java.awt.*;
import java.awt.image.BufferedImage;

public class Filters {
    public static BufferedImage GreyScaleFilter(BufferedImage oldImage){
        BufferedImage newBufferedImage = new BufferedImage(oldImage.getWidth(),oldImage.getHeight(),oldImage.getType());
        for(int i = 0; i < newBufferedImage.getWidth();i++) {
            for(int j = 0; j < newBufferedImage.getHeight();j++) {
                Color oldColor = new Color(oldImage.getRGB(i,j));
                int newColorInt = (oldColor.getGreen()+oldColor.getBlue()+oldColor.getRed())/3;
                Color newColor = new Color(newColorInt,newColorInt,newColorInt);
                newBufferedImage.setRGB(i,j,newColor.getRGB());
            }
        }

        return newBufferedImage;
    }
}
