package Backend.Huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class HuffmanReader {
    int s;

    public static int[] getFrequencies(File InputFile) throws IOException {

        FileInputStream inputStream = new FileInputStream(InputFile);
        FileChannel Reader = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        int[] frequencies = new int[256];
        while (Reader.read(buffer)>0) {
            buffer.flip();
            while (buffer.hasRemaining())
                frequencies[abs(buffer.get())]++;
            buffer.clear();
        }
        return frequencies;
    }

    private static int abs(byte b) {
        int b2 = b;
        if (b2<0)
            b2 = b2+256;
        return b2;
    }
}
