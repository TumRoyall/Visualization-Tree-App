
package tree;

import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GenericTree extends Tree{
    protected Node root;

    public GenericTree(int rootData) {
        this.root = new Node(rootData);
    }

    //getter setter 
    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    @Override
    public void insert(int parent, int child) {
        Node parentNode = search(root, parent);
        if(parentNode == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Parent node not found");
            return;
        }
        Node chilNode = new Node(child);
        chilNode.setParent(parentNode);
        parentNode.getChildren().add(chilNode);
    }

    @Override
    public Node search(Node root, int value) {
        if(root == null) return null; 

        if(root.getData() == value) return root;

        for(Node child : root.getChildren()) {
            Node found = search(child, value);
            if(found!= null) return found;
        }

        return null;
    }

    @Override
    public void delete(int value) {
        // Nếu root là null, không làm gì cả
        if (root == null) {
            System.out.println("Cây rỗng, không thể xóa");
            return;
        }

        // Nếu root là nút cần xóa
        if (root.getData() == value) {
            root = null;  // Xóa cây (root = null)
            System.out.println("Cây đã bị xóa.");
            return;
        }

        // Nếu root không phải là nút cần xóa, tìm và xóa trong cây con
        if (deleteNode(root, value)) {
            System.out.println("Nút " + value + " đã bị xóa.");
        } else {
            System.out.println("Không tìm thấy nút cần xóa.");
        }
    }

    private boolean deleteNode(Node current, int value) {
        for (Node child : current.getChildren()) {
            if (child.getData() == value) {
                current.getChildren().remove(child); // Phát hiện nút cần xóa, tiến hành xóa
                return true; // Xóa thành công
            }
    
            // Gọi đệ quy để tìm nút cần xóa trong cây con
            if (deleteNode(child, value)) {
                return true; // Xóa thành công ở cây con
            }
        }
        return false; // Node not found
    }

    @Override
    public void update(int currentV, int newV) {
        Node Node = search(root, currentV);
        if(Node == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setContentText("Không tìm thấy nút cần cập nhật");
            alert.show();
            return;
        }
        Node.setData(newV);
    }
    @Override
    public void DFS(Node root) {
        if(root == null)
            return;
        System.out.print(root.getData()+ " ");
        for(Node child : root.getChildren()) {
            DFS(child);
        }
    }

    @Override
    public void BFS(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            System.out.print(current.getData() + " ");
            queue.addAll(current.getChildren());
        }
    }
    public GenericTree copyTree() {
        GenericTree newTree = new GenericTree(0);
        if (this.root != null) {
            newTree.setRoot(copyNode(this.root)); // Deep copy the root and its subtree
        }
        return newTree;
    }

    protected Node copyNode(Node node) {
        if (node == null) return null;

        Node newNode = new Node(node.getData());
        for (Node child : node.getChildren()) {
            newNode.getChildren().add(copyNode(child));
        }
        return newNode;
    }

    public int countNodes() {
        return countNodesRecursive(root);
    }

    private int countNodesRecursive(Node node) {
        if (node == null) return 0;
        int count = 1; // Count the current node
        for (Node child : node.getChildren()) {
            count += countNodesRecursive(child);
        }
        return count;
    }

    public boolean isFound(int value) {
        return search(root, value) != null;
    }
    

}
