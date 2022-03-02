package Backend.Header;

import Backend.DataStructures.Converter;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class HeaderWriter {
    public HeaderStructure Header;

    public HeaderWriter(FileChannel writer, File InputFile, String PostOrder) throws IOException {
        String extension = getExtension(InputFile);
        long FileSize = InputFile.length();
        Header = new HeaderStructure(extension, FileSize, PostOrder);
        Write(Header, writer);
    }

    private void Write(HeaderStructure header, FileChannel writer) throws IOException {
        writeExtension(header.getExtension(), writer);
        writer.write((ByteBuffer) ByteBuffer.allocate(8).putLong(header.getFileSize()).flip());
        writeTree(header.getPostOrderTree(), writer);
    }

    private void writeExtension(String extension, FileChannel writer) throws IOException {
        int EX_LENGTH = extension.length();
        writer.write((ByteBuffer) ByteBuffer.allocate(1).put((byte) EX_LENGTH).flip());
        writer.write((ByteBuffer) ByteBuffer.allocate(EX_LENGTH).put(extension.getBytes()).flip());
    }

    private void writeTree(String postOrderTree, FileChannel writer) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        int i = 0;
        for (i = 0; i < postOrderTree.length(); i += 8) {

            byteBuffer.put((byte) Converter.strtoint(postOrderTree.substring(i, i + 8)));
            if ((i + 8) % 64 == 0) {
                byteBuffer.flip();
                writer.write(byteBuffer);
                byteBuffer.clear();

            }

        }
        if (i != postOrderTree.length()) {
            byteBuffer.flip();
            writer.write(byteBuffer);
            byteBuffer.clear();
        }
    }

    private String getExtension(File inputFile) {
        return inputFile.getName().substring(inputFile.getName().lastIndexOf(".") + 1);
    }
}