package Backend.Header;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Stack;

import Backend.DataStructures.Converter;
import Backend.DataStructures.Node;

public class HeaderReader {
    public HeaderStructure Header;
    public HeaderReader(FileChannel reader) throws IOException {
        int extensionSize = readExtensionSize(reader);
        String extension = readExtension(reader,extensionSize);
        long FileSize = readFileSize(reader);
        Node Tree = readTree(reader);

        Header = new HeaderStructure(extension,FileSize,Tree);
    }

    private Node readTree(FileChannel reader) throws IOException {
        Stack<Node> stack = new Stack<>();

        String work = getByte(reader);
        while (true) {
            if (work.isEmpty())
                work = getByte(reader);
            if (work.charAt(0) == '1') {
                if (work.length() < 9)
                    work += getByte(reader);

                byte b = (byte) Converter.strtoint(work.substring(1, 9));
                work = work.substring(9);
                stack.push(new Node((char) b));

            } else {
                if (stack.size() == 1) {
                    return stack.pop();
                }

                Node newNode = new Node();
                newNode.setRight(stack.pop());
                newNode.setLeft(stack.pop());
                stack.push(newNode);
                work = work.substring(1);
            }
        }

    }
    private String getByte(FileChannel reader) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        reader.read(buffer);
        buffer.flip();
        byte b = buffer.get();
        String str = Converter.tobinaryString(b);
        return str;
    }

    private long readFileSize(FileChannel reader) throws IOException {
        ByteBuffer fsize = ByteBuffer.allocate(8);
        reader.read(fsize);
        fsize.flip();
        long FileSize = fsize.getLong();
        return FileSize;
    }

    private String readExtension(FileChannel reader, int extensionSize) throws IOException {
       ByteBuffer buffer = ByteBuffer.allocate(extensionSize);
        reader.read(buffer);
        buffer.flip();
        String extnsion ="";
        for (int i = 0; i < extensionSize; i++)
            extnsion+=(char)buffer.get();
        return extnsion;
    }

    private int readExtensionSize(FileChannel reader) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        reader.read(buffer);
        buffer.flip();

        return buffer.get();
    }
}