import static java.lang.System.exit;

public class Main {

    private final static String dirPath = "src/main/java/resources/";
    private static String fileName;
    private static Filter filter;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Provide file name and filter");
            exit(1);
        }
        fileName = args[0];
        args[1] = args[1].toLowerCase();
        switch (args[1]) {
            case "antialiasing": {
                filter = Filter.ANTIALIASING;
                break;
            }
            default: {
                System.out.println("Unknown filter");
                exit(2);
            }
            FileManager fileManager = new FileManager(dirPath);
            fileManager.readImage(fileName);

            //TODO: Apply filter

            fileManager.saveImage(null);
        }

    }
}
