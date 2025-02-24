package main.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tree.BalancedBinaryTree;
import tree.BalancedTree;
import tree.BinaryTree;
import tree.GenericTree;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class init implements Initializable{
    private Stage stage;

    @FXML
    private ChoiceBox<String> TreeType;

    private String[] choices = {"Generic Tree", "Binary Tree", "Balanced Tree", "Binary Balanced Tree"};

    @FXML
    private TextField maxDepthDiff;

    @FXML
    private TextField rootValue;

    @FXML
    private Button Create;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
            TreeType.getItems().addAll(choices);
            TreeType.setValue("Generic Tree");
    }

    // Xử lý sự kiện khi nhấn nút "Create"
    @FXML
    public void handleCreate(ActionEvent event) {
        try {
            // Lấy giá trị từ các trường nhập liệu
            String selectedTreeType = TreeType.getValue();
            int rootVal = Integer.parseInt(rootValue.getText());

            // Kiểm tra điều kiện đầu vào
            if (selectedTreeType == null || rootValue.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select a tree type and enter a root value");
                alert.show();
                return;
            }

            int depthDiff = 0; // Default cho các loại cây không cần
            if ((selectedTreeType.equals("Balanced Tree") || selectedTreeType.equals("Binary Balanced Tree"))
                    && maxDepthDiff.getText().isEmpty()) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Please enter a Max Depth Difference for Balanced Tree");
                        alert.show();
                        return;
            } else if (!maxDepthDiff.getText().isEmpty()) {
                depthDiff = Integer.parseInt(maxDepthDiff.getText());
            }

            // Lưu thông tin vào class work
            if (selectedTreeType.equals("Generic Tree")) {
                work.typeTree = 0;
                work.GenericT = new GenericTree(rootVal); // Tạo GenericTree
                work.undoStackGT = new Stack<>();
                work.redoStackGT = new Stack<>();
                System.out.println("Generic Tree created with root: " + rootVal);
            } else if (selectedTreeType.equals("Balanced Tree")) {
                work.typeTree = 2;
                work.BalanceT = new BalancedTree(rootVal, depthDiff);
                work.undoStackBaT = new Stack<>();
                work.redoStackBaT = new Stack<>();
                work.maxDepthDiff = depthDiff; // Lưu độ sâu khác biệt
                System.out.println("Balanced Tree created with root: " + rootVal + ", Max Depth Diff: " + depthDiff);
            } else if (selectedTreeType.equals("Binary Balanced Tree")) {
                work.typeTree = 3;
                work.BBT = new BalancedBinaryTree(rootVal, depthDiff);
                work.undoStackBBT = new Stack<>();
                work.redoStackBBT = new Stack<>();
                work.maxDepthDiff = depthDiff;
                System.out.println("Binary Balanced Tree created with root: " + rootVal + ", Max Depth Diff: " + depthDiff);
            } else if (selectedTreeType.equals("Binary Tree")) {
                work.typeTree = 1;
                work.BinaryT = new BinaryTree(rootVal);
                work.undoStackBiT = new Stack<>();
                work.redoStackBiT = new Stack<>();
                System.out.println("Binary Tree created with root: " + rootVal);
            }
            showinputOk();
            // Đóng màn hình hiện tại và mở Visualize
            stage = (Stage) Create.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showinputError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showinputError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setContentText("Vui lòng nhập giá trị cho tất cả các trường!");
        alert.show();
    }

    public void showinputOk() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setContentText("Thành công tạo cây! \n Vui lòng nhấn Play để bắt đầu");
        alert.show();
    }
}
