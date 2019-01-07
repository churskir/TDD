import org.openjdk.jmh.runner.RunnerException;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.System.exit;

public class Main {

    private final static String dirPath = "src/test/resources/";
    private static String fileName;
    private static String filterName;
    private static FilterEnum filterEnum;

    public static void main(String[] args) throws Exception {
        //org.openjdk.jmh.Main.main(args);
        if (args.length < 2) {
            System.out.println("Provide file name and filter");
            exit(1);
        }
        fileName = args[0];
        filterName = args[1].toLowerCase();
        switch (filterName) {
            case "greyscale": {
                filterEnum = FilterEnum.GREYSCALE;
                break;
            }
            case "negative": {
                filterEnum = FilterEnum.NEGATIVE;
                break;
            }
            case "antialiasing": {
                filterEnum = FilterEnum.ANTIALIASING;
                break;
            }
            case "resizefast": {
                filterEnum = FilterEnum.RESIZEFAST;
                break;
            }
            case "resizeavg": {
                filterEnum = FilterEnum.RESIZEAREAAVERAGING;
                break;
            }
            case "resizesmooth": {
                filterEnum = FilterEnum.RESIZESMOOTH;
                break;
            }
            case "fastscale": {
                filterEnum = FilterEnum.MOJEFAST;
                break;
            }
            case "avgscale": {
                filterEnum = FilterEnum.MOJEAVG;
                break;
            }
            default: {
                System.out.println("Unknown filter");
                exit(2);
            }
        }
        FileManager fileManager = new FileManager(dirPath);
        BufferedImage image = fileManager.readImage(fileName);
        BufferedImage result = null;

        if (filterEnum.equals(FilterEnum.GREYSCALE)) {
            result = Filters.GreyScaleFilter(image);
        }
        else if (filterEnum.equals((FilterEnum.NEGATIVE))){
            result = Filters.NegativeFilter(image);
        }
        else if (filterEnum.equals(FilterEnum.ANTIALIASING)) {
            result = Filters.AntiAliasingFilter(image);
        }
        else if (filterEnum.equals(FilterEnum.RESIZEFAST)) {
            result = Filters.ResizeFast(image, 300, 300);
        }
        else if (filterEnum.equals(FilterEnum.RESIZEAREAAVERAGING)) {
            result = Filters.ResizeAreaAveraging(image, 300, 300);
        }
        else if (filterEnum.equals(FilterEnum.RESIZESMOOTH)) {
            result = Filters.ResizeSmooth(image, 300, 300);
        }
        else if (filterEnum.equals(FilterEnum.MOJEFAST)) {
            result = Filters.MojeFast(image, 500, 500);
        }
        else if (filterEnum.equals(FilterEnum.MOJEAVG)) {
            result = Filters.MojeAvgScale(image, 800, 600);
        }

        fileManager.saveImage(result);


    }
}
