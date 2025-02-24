package test;
import tree.BalancedTree;

public class TestBalancedTree {
    public static void main(String[] args) {
        // Tạo cây BalancedTree với gốc là 1 và maxDepthDifference là 2
        BalancedTree tree = new BalancedTree(1, 2);

        // Thêm các nút vào cây
        tree.insert(1, 2);
        tree.insert(1, 3);
        tree.insert(2, 4);
        tree.insert(2, 5);
        tree.insert(3, 6);
        tree.insert(3, 7);
        tree.insert(4, 8);
        tree.insert(8, 9);
        tree.insert(9, 10);

        System.out.println("Cây ban đầu:");
        tree.printTree(tree.getRoot(), "", true);

        // Kiểm tra cân bằng
        System.out.println("Cây có cân bằng không? " + tree.isBalanced());

        // Cân bằng lại cây nếu cần
        if (!tree.isBalanced()) {
            System.out.println("Cây không cân bằng. Đang tiến hành cân bằng...");
            tree.balance();
            System.out.println("Cây sau khi cân bằng:");
            tree.printTree(tree.getRoot(), "", true);
        }

        // Kiểm tra lại cân bằng
        System.out.println("Cây có cân bằng không? " + tree.isBalanced());

        // Cập nhật giá trị của nút
        System.out.println("Cập nhật nút 4 thành 10:");
        tree.update(4, 10);
        tree.printTree(tree.getRoot(), "", true);

        // Xóa một nút
        System.out.println("Xóa nút 5:");
        tree.delete(5);
        tree.printTree(tree.getRoot(), "", true);

        // Xóa một nút
        System.out.println("Xóa nút 2:");
        tree.delete(2);
        tree.printTree(tree.getRoot(), "", true);
    }
}
