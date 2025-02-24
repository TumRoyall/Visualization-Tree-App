package test;

import tree.GenericTree;
import java.util.Scanner;

public class generictreetest {
    public static void main(String[] args) {
        // Create a generic tree
        GenericTree tree = new GenericTree(0);
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Chọn thao tác:");
            System.out.println("1. Insert");
            System.out.println("2. Delete");
            System.out.println("3. Update");
            System.out.println("4. Search");
            System.out.println("5. Thoát");
            int choice = input.nextInt();
            input.nextLine(); // Đọc dòng mới

            switch (choice) {
                case 1:
                    System.out.print("Nhập giá trị cần thêm: ");
                    int valueParent = input.nextInt();
                    int value = input.nextInt();
                    input.nextLine(); // Đọc dòng mới
                    tree.insert(valueParent, value);
                    break;
                case 2:
                    System.out.print("Nhập giá trị cần xóa: ");
                    int valueToDelete = input.nextInt();
                    input.nextLine(); // Đọc dòng mới
                    tree.delete(valueToDelete);
                    break;
                case 3:
                    System.out.print("Nhập giá trị cần cập nhật: ");
                    int valueToUpdate = input.nextInt();
                    input.nextLine(); // Đọc dòng mới
                    System.out.print("Nhập giá trị mới: ");
                    int newValue = input.nextInt();
                    input.nextLine(); // Đọc dòng mới
                    tree.update(valueToUpdate, newValue);
                    break;
                case 4:
                    System.out.print("Nhập giá trị cần tìm: ");
                    int valueToSearch = input.nextInt();
                    input.nextLine(); // Đọc dòng mới
                    if (tree.search(tree.getRoot(), valueToSearch) != null) {
                        System.out.println("Giá trị tìm thấy trong cây.");
                    } else {
                        System.out.println("Giá trị không tìm thấy trong cây.");
                    }
                    break;
                case 5:
                    System.out.println("Thoát khỏi chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }

            tree.printTree(tree.getRoot(), "", true);
        }
    }
}
