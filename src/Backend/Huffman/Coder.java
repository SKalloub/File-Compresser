package Backend.Huffman;

import Backend.DataStructures.Converter;
import Backend.DataStructures.Node;
public class Coder {
private static String PostOrder = "";
public Coder() {
    PostOrder = "";
}
    public static void code(Node tree) {
        Code(tree,"");
    }

    private static void Code(Node tree, String s) {
        if (tree==null)
            return;

        if (tree.getLeft()==null && tree.getRight()==null)
            tree.setCode(s);

        else {
            Code(tree.getLeft(),s+'0');
            Code(tree.getRight(),s+'1');
        }

    }
    public static String getPostOrder(Node Tree) {
        PostOrder = "";
        PostOrder(Tree);
        while (PostOrder.length()%8!=0)
            PostOrder+='0';

    return PostOrder;
    }

    private static void PostOrder(Node tree) {
        if (tree==null)
            return;
        if (tree.getLeft()==null && tree.getRight()==null)
            PostOrder += '1'+ Converter.tobinaryString(tree.getSymbol());
else {
            PostOrder(tree.getLeft());
            PostOrder(tree.getRight());
            PostOrder += '0';
        }
}
}
