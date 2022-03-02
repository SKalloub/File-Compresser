package Backend.Compress;

import Backend.DataStructures.Node;
import Backend.Header.HeaderWriter;
import Backend.Huffman.Coder;
import Backend.Huffman.HuffmanBuilder;
import Backend.Huffman.HuffmanTable;
import Backend.Report;

import java.io.*;
import java.nio.channels.FileChannel;

public class Compression {

    protected static String EXTENSION = ".huf";
    private static int BUFFER_SIZE = 16;
    public static Report report = new Report();
private static String temp = "";
    public static Node[] CompressFile(File inputFile) throws IOException {
        report = new Report();
        HuffmanBuilder HuffBuilder = new HuffmanBuilder(inputFile);
        HuffmanTable HuffTable = new HuffmanTable(HuffBuilder.Tree());
        FileInputStream inputStream = new FileInputStream(inputFile);
        report.setOriginalSize(inputFile.length());
        FileOutputStream outputStream = new FileOutputStream(getNewFile(inputFile));
        FileChannel reader = inputStream.getChannel();
        FileChannel writer = outputStream.getChannel();
        HeaderWriter Writer = new HeaderWriter(writer, inputFile, Coder.getPostOrder(HuffBuilder.Tree()));
        report.setExtension(Writer.Header.getExtension());
        report.setPostOrderTreeBin(Writer.Header.getPostOrderTree());
        report.compute();
        Encoder.encode(reader, writer, HuffTable);
        reader.close();
        writer.close();
       return HuffTable.Codes;
    }

    private static File getNewFile(File inputFile) {
        String path = inputFile.getAbsolutePath().substring(0,inputFile.getAbsolutePath().lastIndexOf("."));
        return new File(path+EXTENSION);
    }

    private static String getOuputPath(File inputFile) {
        String path = inputFile.getAbsolutePath().substring(0,inputFile.getAbsolutePath().lastIndexOf("."));
        return path+EXTENSION;

    }
}
