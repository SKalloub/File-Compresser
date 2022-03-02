import Backend.Compress.Compression;

import Backend.Decompress.Decompression;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        try {
           //Compression.CompressFile((new File("C:\\Users\\joman\\OneDrive\\Desktop\\map.jpg")));
                 Decompression.Decompress(new File("C:\\Users\\joman\\OneDrive\\Desktop\\map.huf"));
        } catch (IOException e) {
          e.printStackTrace();
        }

    }
}