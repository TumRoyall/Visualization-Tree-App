package tree;

import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BalancedTree extends GenericTree {
    private int maxDepthDifference;

    public BalancedTree(int rootData, int maxDepthDifference) {
        super(rootData);
        this.maxDepthDifference = maxDepthDifference;
    }

    // Phương thức kiểm tra cân bằng cây
    public boolean isBalanced() {
        int maxDepth = getMaxDepth(getRoot());
        int minDepth = getMinDepth(getRoot());
        return (maxDepth - minDepth) <= maxDepthDifference;
    }
    private int getMaxDepth(Node node) {
        if (node == null) return 0;
        int maxDepth = 0;
        for (Node child : node.getChildren()) {
            maxDepth = Math.max(maxDepth, getMaxDepth(child));
        }
        return maxDepth + 1;
    }
    private int getMinDepth(Node node) {
        if (node == null) return 0;
        if (node.getChildren().isEmpty()) return 1;
        int minDepth = Integer.MAX_VALUE;
        for (Node child : node.getChildren()) {
            minDepth = Math.min(minDepth, getMinDepth(child));
        }
        return minDepth + 1;
    }
    
    // Phương thức cân bằng lại cây nếu cần thiết
    public void balance() {
        while (!isBalanced()) {
            Node deepestNode = findDeepestNode(getRoot());
            Node shallowestNode = findShallowestNode(getRoot());
    
            if (deepestNode != null && shallowestNode != null && deepestNode != shallowestNode) {
                // Di chuyển nút từ cây sâu nhất sang cây nông nhất
                Node parentNode = deepestNode.getParent();
                if (parentNode != null) {
                    parentNode.getChildren().remove(deepestNode);
                    shallowestNode.getChildren().add(deepestNode);
                    deepestNode.setParent(shallowestNode);
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Không thể cân bằng cây");
                alert.showAndWait();
                break;
            }
        }
    }


    // Tìm nút có chiều sâu lớn nhất
    private Node findDeepestNode(Node root) {
    if (root == null) return null;
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    Node current = null;
    while (!queue.isEmpty()) {
        current = queue.poll();
        queue.addAll(current.getChildren());
    }
    return current;
}


    // Tìm nút có chiều sâu nhỏ nhất
    private Node findShallowestNode(Node root) {
        if (root == null) return null;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.getChildren().isEmpty()) return current;
            queue.addAll(current.getChildren());
        }
        return null;
    }

    public BalancedTree copyTree() {
        BalancedTree newTree = new BalancedTree(0, 0);
        newTree.setRoot(copyNode(this.root));
        return newTree;
    }

}
