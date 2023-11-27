import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIntake {

    public static List<Digit> loadData(String set, double threshold) {
        String imageFile = set + "-images.idx3-ubyte";
        String labelFile = set + "-labels.idx1-ubyte";
        List<Digit> digits = new ArrayList<>();
        ClassLoader loader = FileIntake.class.getClassLoader();

        try (
                DataInputStream imageInputStream = new DataInputStream(loader.getResourceAsStream(imageFile));
                DataInputStream labelInputStream = new DataInputStream(loader.getResourceAsStream(labelFile))
        ){

            // Magic Numbers (Start)
            if(imageInputStream.readInt() != 2051) throw new IOException("File Format Error For: " + imageFile);
            if(labelInputStream.readInt() != 2049) throw new IOException("File Format Error For: " + labelFile);

            // Quantities
            int numImages = imageInputStream.readInt();
            int numLabels = labelInputStream.readInt();

            if(numImages != numLabels) throw new IOException("Images and Labels Do Not Match\nNumber of Images: " + numImages + "\n Number of Labels: " + numLabels);

            // Image Size
            int rows = imageInputStream.readInt();
            int cols = imageInputStream.readInt();

            byte[] data = new byte[rows*cols];
            for(int i = 0 ; i < numImages ; i++) {
                double[] image = new double[rows*cols];
                imageInputStream.read(data,0,data.length);
                for (int j = 0; j < image.length; j++) image[j] = (data[j] & (int)threshold)  / threshold;
                digits.add(new Digit(image,labelInputStream.readByte(),digits.size()));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return digits;

    }
}
