import java.awt.*;
import java.awt.image.BufferedImage;

public class Filters {
    public static BufferedImage GreyScaleFilter(BufferedImage oldImage) {
        BufferedImage newBufferedImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), oldImage.getType());
        for (int i = 0; i < newBufferedImage.getWidth(); i++) {
            for (int j = 0; j < newBufferedImage.getHeight(); j++) {
                Color oldColor = new Color(oldImage.getRGB(i, j));
                int newColorInt = (oldColor.getGreen() + oldColor.getBlue() + oldColor.getRed()) / 3;
                Color newColor = new Color(newColorInt, newColorInt, newColorInt);
                newBufferedImage.setRGB(i, j, newColor.getRGB());
            }
        }

        return newBufferedImage;
    }

    public static BufferedImage NegativeFilter(BufferedImage oldImage) {
        BufferedImage newBufferedImage = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), oldImage.getType());
        for (int i = 0; i < newBufferedImage.getWidth(); i++) {
            for (int j = 0; j < newBufferedImage.getHeight(); j++) {
                Color oldColor = new Color(oldImage.getRGB(i, j));
                int r = oldColor.getRed();
                int g = oldColor.getGreen();
                int b = oldColor.getBlue();
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                Color newColor = new Color(r, g, b);
                newBufferedImage.setRGB(i, j, newColor.getRGB());
            }
        }
        return newBufferedImage;
    }

    public static BufferedImage AntiAliasingFilter(BufferedImage oldImage) {
        BufferedImage result = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), oldImage.getType());
        for (int i = 0; i < result.getWidth(); i ++) {
            for (int j = 0; j < result.getHeight(); j++) {
                if (i == 0 || j == 0 || i == result.getWidth() - 1 || j == result.getHeight() - 1) {
                    result.setRGB(i, j, oldImage.getRGB(i, j));
                } else {
                    result.setRGB(
                            i,
                            j,
                            getAverageColor(
                                    new Color(oldImage.getRGB(i - 1, j)),
                                    new Color(oldImage.getRGB(i, j - 1)),
                                    new Color(oldImage.getRGB(i + 1, j)),
                                    new Color(oldImage.getRGB(i, j + 1))
                            ).getRGB());
                }
            }
        }
        return result;
    }

    private static Color getAverageColor(Color a, Color b, Color c, Color d) {
        return new Color(
                (a.getRed() + b.getRed() + c.getRed() + d.getRed()) / 4,
                (a.getGreen() + b.getGreen() + c.getGreen() + d.getGreen()) / 4,
                (a.getBlue() + b.getBlue() + c.getBlue() + d.getBlue()) / 4
        );
    }
}