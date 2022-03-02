package Backend.Huffman;
import Backend.Compress.Compression;
import Backend.DataStructures.Node;

import java.io.File;
import java.io.IOException;
import java.util.PriorityQueue;

public class HuffmanBuilder {
private Node Tree;
    public HuffmanBuilder(File inputFile) throws IOException {
        int[] frequencies = HuffmanReader.getFrequencies(inputFile);
        Tree = BuildTree(frequencies);
        Coder.code(Tree);
    }
public Node Tree() {
        return Tree;
}
    private Node BuildTree(int[] frequencies) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        InsertAll(queue,frequencies);
        int size = queue.size();
        Compression.report.setNumDisChars(size);
        for (int i = 0; i < size-1; i++)
            queue.add(new Node(queue.poll(),queue.poll()));
        return queue.poll();
    }

    private void InsertAll(PriorityQueue<Node> queue, int[] frequencies) {
        for (int i = 0; i < frequencies.length; i++) {
        if (frequencies[i]>0)
            queue.add(new Node((char) i,frequencies[i]));
        }
    }
}
