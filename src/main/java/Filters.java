import org.openjdk.jmh.annotations.Benchmark;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;


public class Filters {
    @Benchmark
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
    @Benchmark
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
    @Benchmark
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
    @Benchmark
    private static Color getAverageColor(Color a, Color b, Color c, Color d) {
        return new Color(
                (a.getRed() + b.getRed() + c.getRed() + d.getRed()) / 4,
                (a.getGreen() + b.getGreen() + c.getGreen() + d.getGreen()) / 4,
                (a.getBlue() + b.getBlue() + c.getBlue() + d.getBlue()) / 4
        );
    }
    @Benchmark
    public static BufferedImage ResizeFast(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_FAST);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    @Benchmark
    public static BufferedImage ResizeAreaAveraging(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    @Benchmark
    public static BufferedImage ResizeSmooth(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    @Benchmark
    public static BufferedImage MojeFast(BufferedImage image, int newHeight, int newWidth) {

        int origWidth = image.getWidth();
        int origHeight = image.getHeight();
        float imageAspect = (float)origWidth / (float)origHeight;
        float canvasAspect = (float) newWidth /(float) newHeight;

        int imgWidth = newWidth;
        int imgHeight = newHeight;
        if (imageAspect < canvasAspect) {
            float w = (float) newHeight * imageAspect;
            imgWidth = (int) w;
        } else {
            float h = (float) newWidth / imageAspect;
            imgHeight = (int) h;
        }

        BufferedImage newImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        WritableRaster newImageRaster = newImage.getRaster();
        int[] newImagePixels = ( (DataBufferInt) newImageRaster.getDataBuffer()).getData();

        int count = 0;
        float xr = (float)image.getWidth() / (float)imgWidth;
        float yr = (float)image.getHeight() / (float)imgHeight;
        float r = xr;
        if (yr < xr) r = yr;
        int row = 0;
        int col = 0;
        float x = 0; float y = 0;
        for (row = 0; row < imgHeight; row++) {
            x = 0;
            for (col = 0; col < imgWidth; col++) {
                int rgb = image.getRGB((int)x,(int)y);
                x += r;
                newImagePixels[count]=rgb;
                count++;
            }
            y += r;
        }
        return newImage;
    }
    @Benchmark
    public static BufferedImage MojeAvgScale(BufferedImage image, int newHeight, int newWidth) {
        final int SHARPEN_LEVEL = 10;
        int origWidth = image.getWidth();
        int origHeight = image.getHeight();

        float imageAspect = (float)origWidth / (float)origHeight;
        float canvasAspect = (float) newWidth /(float) newHeight;
        int imgWidth = newWidth;
        int imgHeight = newHeight;
        if (imageAspect < canvasAspect) {
            float w = (float) newHeight * imageAspect;
            imgWidth = (int) w;
        } else {
            float h = (float) newWidth / imageAspect;
            imgHeight = (int) h;
        }
        BufferedImage newImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        WritableRaster newImageRaster = newImage.getRaster();
        int[] newImagePixels = ( (DataBufferInt) newImageRaster.getDataBuffer()).getData();
        int count = 0;

        float xr =  (float)imgWidth / (float)image.getWidth();
        float rx =  (float)image.getWidth() / (float)imgWidth;
        int rxi =  ((int)rx) * SHARPEN_LEVEL / 10;
        float yr = (float)imgHeight / (float)image.getHeight();
        int sb = (int)rxi / 2;

        // Red, Green, Blue arrays
        long[] ra = new long[origWidth];
        int[] ga = new int[origWidth];
        int[] ba = new int[origWidth];

        int row = 0;
        int col = 0;
        float posy = 0;
        int colCount = 0;
        int owm1 = origWidth - 1;
        for (row = 0; row < origHeight; row++) {
            colCount++;
            posy += yr;
            float posx = 0;
            for (col = 0; col < origWidth; col++) {
                int ir = image.getRGB(col,row);
                int r = ir & 0x00FF0000;
                int g = ir & 0x0000FF00;
                int b = ir & 0x000000FF;
                int ro = 0;
                int go = 0;
                int bo = 0;
                if (row >= rxi) {
                    int or = image.getRGB(col,row-rxi);
                    ro = or & 0x00FF0000;
                    go = or & 0x0000FF00;
                    bo = or & 0x000000FF;
                }
                // dodanie do tablic
                ra[col] += r;
                ga[col] += g;
                ba[col] += b;
                // odjęcie starych wartości
                ra[col] -= ro;
                ga[col] -= go;
                ba[col] -= bo;

                posx += xr;
                if ((posx > 1f || (col == owm1 && colCount < imgWidth)) && (posy > 1f)) {
                    long rt = 0;
                    int gt = 0;
                    int bt = 0;
                    // jeśli sb 0 to nie robić uśrednienia
                    if (sb == 0) {
                        rt = ra[col];
                        gt = ga[col];
                        bt = ba[col];
                    } else {
                        int ct = 0;
                        for (int k = (col - sb); k < (col + sb); k++) {
                            if (k >= 0 && k < origWidth) {
                                ct++;
                                rt += ra[k];
                                gt += ga[k];
                                bt += ba[k];
                            }
                        }
                        if (ct == 0) ct = 1;
                        rt = (rt / ct / rxi) & 0x00FF0000;
                        gt = (gt / ct / rxi) & 0x0000FF00;
                        bt = (bt / ct / rxi) & 0x000000FF;
                    }
                    int rgb = (int)rt | gt | bt;
                    newImagePixels[count]=rgb;
                    count++;
                }//nie może być większe niż jeden do uśredniania
                if (posx > 1f) posx -= 1f;
            }
            //nie może być większe niż jeden do uśredniania
            if (posy > 1f) posy -= 1f;
            colCount = 0;
        }
        return newImage;
    }
}