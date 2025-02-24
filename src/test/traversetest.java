package test;

import tree.GenericTree;
import java.util.Scanner;

public class traversetest {
    public static void main(String[] args) {
        // Create a generic tree
        Scanner input = new Scanner(System.in);
        GenericTree tree = new GenericTree(0);
        tree.insert(0, 1);
        tree.insert(0, 2);
        tree.insert(0, 3);
        tree.insert(1, 4);
        tree.insert(1, 5);
        tree.insert(2, 6);
        tree.insert(2, 7);
        tree.insert(3, 8);
        tree.printTree(tree.getRoot(), "", true);
        while (true) {
            System.out.println("Chọn thao tác:");
            System.out.println("1. DFS");
            System.out.println("2. BFS");
            System.out.println("3. Thoát");
            int choice = input.nextInt();
            input.nextLine(); // Đọc dòng mới
            switch (choice) {
                case 1:
                    tree.DFS(tree.getRoot());
                    System.out.println();
                    break;
                case 2:
                    tree.BFS(tree.getRoot());
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Thoát khỏi chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }

        }
    }
}
