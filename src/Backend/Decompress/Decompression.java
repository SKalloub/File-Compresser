package Backend.Decompress;
import Backend.Header.HeaderReader;

import java.io.*;
import java.nio.channels.FileChannel;

public class Decompression {
    public Decompression(){

    }
public static void Decompress(File inputFile) throws IOException {
    FileInputStream inputStream = new FileInputStream(inputFile);
    FileChannel reader = inputStream.getChannel();
    HeaderReader HeaderReader = new HeaderReader(reader);
    File outFile = new File(getOutputPath(inputFile, HeaderReader.Header.getExtension()));
    outFile.createNewFile();
    FileOutputStream outputStream = new FileOutputStream(outFile);
    FileChannel writer = outputStream.getChannel();
    Decoder.decode(reader, writer, HeaderReader.Header);
    writer.close();
    reader.close();
}

    private static String getOutputPath(File inputFile, String EXTENSION) {
        String path = inputFile.getAbsolutePath().substring(0,inputFile.getAbsolutePath().lastIndexOf("."));
        return path+"1."+EXTENSION;

    }
}