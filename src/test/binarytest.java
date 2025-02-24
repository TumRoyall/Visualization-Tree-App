package test;

import tree.BinaryTree;

public class binarytest {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(0);
        tree.insert(0, 1);
        tree.insert(0, 2);
        tree.printTree(tree.getRoot(), "", true);
        tree.delete(0);
        tree.printTree(tree.getRoot(), "", true);
    }
}
