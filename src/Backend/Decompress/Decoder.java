package Backend.Decompress;

import Backend.DataStructures.Converter;
import Backend.DataStructures.Node;
import Backend.Header.HeaderStructure;
import Backend.Huffman.Coder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Decoder {
    public static void decode(FileChannel reader, FileChannel writer, HeaderStructure header) throws IOException {
        ByteBuffer bufferRead = ByteBuffer.allocate(8);
        ByteBuffer bufferWrite = ByteBuffer.allocate(8);
        read8Bytes(reader,bufferRead);
        long fileSize = header.getFileSize();
        Node Tree = header.getTree();
       Coder.getPostOrder(Tree);
        long bytesRead = 0;
        Node temp = null;
        int i = 0;
        int count = 0;
        String work = Converter.tobinaryString(getByte(reader, bufferRead));
        boolean flag = false;
        while (bytesRead < fileSize) {
            temp = Tree;
            while (temp.getRight() != null || temp.getLeft()!=null) {
                if (i >= work.length()) {
                    if (!bufferRead.hasRemaining())
                        if (!read8Bytes(reader,bufferRead)) {
                            flag = true;
                            break;
                        }
                    work = Converter.tobinaryString(getByte(reader, bufferRead));
                    i = 0;
                }
                if (flag)
                    break;
                if (work.charAt(i) == '0')
                    temp = temp.getLeft();
                else
                    temp = temp.getRight();
                i++;
            }
            bytesRead++;
            count++;
            putByte((byte) temp.getSymbol(),writer,bufferWrite,count);
        }
        if (bufferWrite.hasRemaining()) {
            bufferWrite.flip();
            writer.write(bufferWrite);
        }
    }
    private static boolean read8Bytes(FileChannel reader, ByteBuffer buffer) throws IOException {
        buffer.clear();
       int s = reader.read(buffer);
       buffer.flip();
    return  s>0;
    }
    private static void write8Bytes(FileChannel writer, ByteBuffer buffer) throws IOException {
        buffer.flip();
        writer.write(buffer);
        buffer.clear();
    }
    private static byte getByte(FileChannel reader, ByteBuffer buffer) throws IOException {
        return  buffer.get();
    }
    private static void putByte(byte b, FileChannel writer, ByteBuffer buffer, int count) throws IOException {
        if (count%8==0) {
            write8Bytes(writer, buffer);
        }
        buffer.put(b);
    }
}