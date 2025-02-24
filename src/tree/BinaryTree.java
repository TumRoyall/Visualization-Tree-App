package tree;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BinaryTree extends GenericTree{
    //constructor
    public BinaryTree(int value){
        super(value);
    }
    
    public void insert(int parent, int value){
        Node parentNode = search(getRoot(), parent);
        if (parentNode == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Node cha không tồn tại.");
            alert.setContentText("Vui lòng kiểm tra lại thông tin nút cha.");
            alert.showAndWait();
            return;
        }
        
        if(parentNode.getChildren().size() < 2) {
            parentNode.getChildren().add(new Node(value));
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Node cha đã đầy.");
            alert.setContentText("Không thể thêm nút con vào nút cha đã đầy.");
            alert.showAndWait();
        }
    }

    public BinaryTree copyTree() {
        BinaryTree newTree = new BinaryTree(0);
        newTree.setRoot(copyNode(this.root));
        return newTree;
    }
}
