package Backend.DataStructures;
public class Node implements Comparable<Node> {
    private char symbol;
    private int frequency;
    private Node left;
    private Node right;
    private String Code;
    private int Ascii;
    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    /** Constructers **/
    public Node(){};
    public Node(char symbol){this.symbol=symbol;
    Ascii = symbol;
    }
    public Node(int frequency){this.frequency=frequency;}
    public Node(char symbol, int frequency){this.symbol = symbol; this.frequency = frequency;
        Ascii = symbol;
    }
    public Node(Node left, Node right, int frequency){this.left = left; this.right = right; this.frequency = frequency;}
    public Node(Node left, Node right){this.left = left; this.right = right; this.frequency = left.frequency + right.frequency;}
    /** Getters **/
    public char getSymbol(){return symbol;}
    public int getFrequency(){return frequency;}
    public Node getLeft(){return left;}

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getRight(){return right;}

    @Override
    public int compareTo(Node o) {
        return this.frequency-o.frequency;
    }

    public int getAscii() {
        return Ascii;
    }
}
