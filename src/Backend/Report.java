package Backend;

import Backend.DataStructures.Converter;
import sun.reflect.generics.tree.Tree;

public class Report {
    private long OriginalSize;
    private long newSize;
    private int extensionSize;
    private String extension;
    private String PostOrderTree;
    private String PostOrderTreeBin;
    private int TreeSize;
    private int TotalHeader;
    private int numDisChars;

    public long getOriginalSize() {
        return OriginalSize;
    }

    public void setOriginalSize(long originalSize) {
        OriginalSize = originalSize;
    }

    public long getNewSize() {
        return newSize;
    }

    public void setNewSize(long newSize) {
        this.newSize = newSize;
    }

    public int getExtensionSize() {
        return extensionSize;
    }

    public void setExtensionSize(int extensionSize) {
        this.extensionSize = extensionSize;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
        this.extensionSize = extension.length();
    }

    public String getPostOrderTree() {
        return PostOrderTree;
    }

    public void setPostOrderTree(String postOrderTree) {
        PostOrderTree = postOrderTree;
    }

    public String getPostOrderTreeBin() {
        return PostOrderTreeBin;
    }

    public void setPostOrderTreeBin(String postOrderTreeBin) {
        PostOrderTreeBin = postOrderTreeBin;
        TreeSize = PostOrderTreeBin.length()/8;
        PostOrderTree = "";
        for (int i = 0; i < PostOrderTreeBin.length(); i++) {
            if (PostOrderTreeBin.charAt(i)=='0')
                PostOrderTree+='0';
            else
            {
                PostOrderTree +='1';
                PostOrderTree += (char)Converter.strtoint(PostOrderTreeBin.substring(i+1,i+9));
                i  = i+8;
            }
        }
    }

    public int getTreeSize() {
        return TreeSize;
    }

    public void setTreeSize(int treeSize) {
        TreeSize = treeSize;
    }

    public int getTotalHeader() {
        return TotalHeader;
    }

    public void setTotalHeader(int totalHeader) {
        TotalHeader = totalHeader;
    }

    public int getNumDisChars() {
        return numDisChars;
    }

    public void setNumDisChars(int numDisChars) {
        this.numDisChars = numDisChars;
    }

    public double getCompressionRate() {
        long s = this.OriginalSize-this.newSize;
        double s2 = (double) s / this.OriginalSize;
        int s3 = (int) (s2*10000);
        compressionRate = s3/100.0;
        return compressionRate;
    }

    public void setCompressionRate(double compressionRate) {
        this.compressionRate = compressionRate;
    }

    private double compressionRate;

    public Report(long originalSize, long newSize, String extension, String postOrderTree, String postOrderTreeBin, int numDisChars) {
        OriginalSize = originalSize;
        this.newSize = newSize;
        this.extension = extension;
        PostOrderTree = postOrderTree;
        PostOrderTreeBin = postOrderTreeBin;
        this.numDisChars = numDisChars;
    }
    public void compute() {
        TotalHeader = 1+extensionSize+8+TreeSize;
    }
public Report() {

}


}
