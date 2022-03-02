package Backend.Compress;

import Backend.DataStructures.Converter;
import Backend.Huffman.HuffmanTable;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Encoder {
     final static int BUFFER_SIZE = 8;
     private static String work = "";
     private static HuffmanTable CodesTable;
     private static int countBytesInBuffer =0;
    public static void encode(FileChannel reader, FileChannel writer, HuffmanTable Table) throws IOException {
        CodesTable = Table;
        ByteBuffer bufferRead = ByteBuffer.allocate(8);
        ByteBuffer bufferWrite = ByteBuffer.allocate(8);

        while (reader.read(bufferRead)>0) {
            bufferRead.flip();
            while (bufferRead.hasRemaining())
                putCode(bufferRead.get(),writer,bufferWrite);
            bufferRead.clear();
        }
        if (bufferWrite.hasRemaining()) {
            int s = bufferWrite.remaining();
            bufferWrite.flip();
            writer.write(bufferWrite);
            Compression.report.setNewSize(Compression.report.getNewSize()+s);
        }
        if (work.length()!=0) {
            while (work.length()!=8)
                work+='0';
            writer.write((ByteBuffer) ByteBuffer.allocate(1).put((byte) Converter.strtoint(work)).flip());
        Compression.report.setNewSize(Compression.report.getNewSize()+1);
        }

    }
    private static void read8Bytes(FileChannel reader, ByteBuffer buffer) throws IOException {
        buffer.clear();
        reader.read(buffer);
        buffer.flip();
    }

    private static void write8Bytes(FileChannel writer, ByteBuffer buffer) throws IOException {
        buffer.flip();

        writer.write(buffer);
        Compression.report.setNewSize(Compression.report.getNewSize()+8);
        buffer.clear();
    }

    private static byte getByte(FileChannel reader, ByteBuffer buffer) throws IOException {
        if (!buffer.hasRemaining())
            read8Bytes(reader, buffer);

        return buffer.get();
    }

    private static void putCode(byte b, FileChannel writer, ByteBuffer buffer) throws IOException {
        work+= CodesTable.getCode(b);

        while (work.length()>=8) {
            byte temp = (byte) Converter.strtoint(work.substring(0,8));
            buffer.put(temp);
            countBytesInBuffer++;
            if (countBytesInBuffer==8) {
                write8Bytes(writer, buffer);
            countBytesInBuffer = 0;
            }
            work = work.substring(8);
        }

    }

}
