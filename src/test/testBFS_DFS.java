package test;

import tree.*;
public class testBFS_DFS {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(1);
        tree.insert(1, 2);
        tree.insert(1, 3);
        tree.insert(2, 4);
        tree.insert(2, 5);
        tree.insert(3, 6);
        tree.insert(3, 7);
        tree.insert(4, 8);
        tree.insert(8, 9);
        tree.insert(9, 10);

        tree.printTree(tree.getRoot(), "", true);
        // Test BFS
        System.out.println("Breadth-first Search:");
        tree.BFS(tree.getRoot());
        
        // Test DFS
        System.out.println("Depth-first Search:");
        tree.DFS(tree.getRoot());
    }
}
