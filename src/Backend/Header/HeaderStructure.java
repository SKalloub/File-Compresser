package Backend.Header;

import Backend.DataStructures.Node;

public class HeaderStructure {
    private int extensionSize;
    private String extension;
    private long FileSize;
    private String PostOrderTree;

    public Node getTree() {
        return Tree;
    }

    private Node Tree;
    HeaderStructure(String extension,long FileSize, String PostOrderTree) {
        this.extensionSize = extension.length();
        this.extension = extension;
        this.FileSize = FileSize;
        this.PostOrderTree = PostOrderTree;
    }
    HeaderStructure(String extension,long FileSize, Node Tree) {
        this.extensionSize = extension.length();
        this.extension = extension;
        this.FileSize = FileSize;
        this.Tree = Tree;
    }

    public int getExtensionSize() {
        return extensionSize;
    }

    public String getExtension() {
        return extension;
    }

    public long getFileSize() {
        return FileSize;
    }

    public String getPostOrderTree() {
        return PostOrderTree;
    }
}
