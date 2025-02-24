package tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int data;
    private Node parent;
    private List<Node> children;

    // Constructor
    public Node(int value) {
        data = value;
        parent = null;  // Không có nút cha trước đây
        children = new ArrayList<>();  // Khởi tạo danh sách con rỗng
    }

    // Getter và Setter cho dữ liệu
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }
}
