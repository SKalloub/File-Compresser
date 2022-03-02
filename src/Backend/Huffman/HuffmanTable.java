package Backend.Huffman;
import Backend.DataStructures.Node;

public class HuffmanTable {
    public Node[] Codes = new Node[256];
    public HuffmanTable(Node Tree) {
        TreeToTable(Tree);
    }

    private void TreeToTable(Node tree) {
        if (tree==null)
            return;
        if (tree.getLeft()==null && tree.getRight()==null)
            Codes[tree.getSymbol()] = tree;
        TreeToTable(tree.getLeft());
        TreeToTable(tree.getRight());

    }

    public String getCode(byte b) {
        return Codes[abs(b)].getCode();
    }

    private int abs(byte b) {
        int b2 = b;
        if (b2<0)
            b2 = b2+256;
        return b2;
    }
}
