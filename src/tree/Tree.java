package tree;

public abstract class Tree {
    public void insert(int parent, int child) {
    }

    public Node search(Node root, int child) {
        return null;
    }

    public void delete(int child) {
        
    }

    public void update(int currentValue, int newValue) {
    }
    public void BFS(Node root){}
    public void DFS(Node root){}
    // Phương thức in cây theo dạng bảng ngang
    public void printTree(Node node, String prefix, boolean isTail) {
        if (node == null) return;
        System.out.println(prefix + (isTail ? "└── " : "├── ") + node.getData());

        // In các nút con
        for (int i = 0; i < node.getChildren().size(); i++) {
            printTree(node.getChildren().get(i), prefix + (isTail ? "    " : "│   "), i == node.getChildren().size() - 1);
        }
    }

}
