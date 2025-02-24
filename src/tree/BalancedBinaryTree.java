package tree;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BalancedBinaryTree extends BalancedTree {
    public BalancedBinaryTree(int rootData, int maxDepthDifference) {
        super(rootData,maxDepthDifference);
    }

    // Override phương thức insert để đảm bảo mỗi nút chỉ có tối đa 2 con
    @Override
    public void insert(int parent, int child) {
        Node parentNode = search(getRoot(), parent);
        if (parentNode == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Node cha không tồn tại.");
            alert.setContentText("Vui lòng kiểm tra lại dữ liệu.");
            alert.showAndWait();
            return;
        }

        // Kiểm tra nếu nút cha đã có 2 con
        if (parentNode.getChildren().size() >= 2) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Node cha đã đầy.");
            alert.setContentText("Vui lòng thêm vào nút khác.");
            alert.showAndWait();
            return;
        }

        Node childNode = new Node(child);
        childNode.setParent(parentNode);
        parentNode.getChildren().add(childNode);
    }

    public BalancedBinaryTree copyTree() {
        BalancedBinaryTree newTree = new BalancedBinaryTree(0, 0);
        newTree.setRoot(copyNode(this.root));
        return newTree;
    }
}

