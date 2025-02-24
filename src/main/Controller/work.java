package main.Controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;
import tree.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class work {
    //data tree 
    private ArrayList<nodeInfo> listNodeInfosDFS = new ArrayList<nodeInfo>();
    private ArrayList<nodeInfo> listNodeInfosBFS = new ArrayList<nodeInfo>();
    //Data của cây
    public static int typeTree = 0; // generic = 0, binary = 1, balance = 2, balancebinary = 3;
    public static BalancedBinaryTree BBT;
    public static BalancedTree BalanceT;
    public static GenericTree GenericT;
    public static BinaryTree BinaryT;
    public static int maxDepthDiff;
    public static int searchData;
    public static boolean searchFlag;
    public static Stack<GenericTree> undoStackGT;
    public static Stack<GenericTree> redoStackGT;

    public static Stack<BinaryTree> undoStackBiT;
    public static Stack<BinaryTree> redoStackBiT;
    
    public static Stack<BalancedTree> undoStackBaT;
    public static Stack<BalancedTree> redoStackBaT;

    public static Stack<BalancedBinaryTree> undoStackBBT;
    public static Stack<BalancedBinaryTree> redoStackBBT;
 
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Timeline timeline;
    
    @FXML
    private Slider zoomSlider;

    private Scale scale = new Scale();
    @FXML
    private MenuItem New;

    @FXML
    private MenuItem iniroot;

    @FXML 
    private MenuItem menuButton;

    @FXML
    private Button redoButton;

    @FXML
    private Button undButton;

    @FXML
    private Pane treePane;

    @FXML
    private Button resumeButton; 

    @FXML
    private Button pauseButton;

    @FXML
    public void initialize() {
        // Áp dụng scale cho treePane
        treePane.getTransforms().add(scale);

        // Thiết lập sự kiện khi slider thay đổi
        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Nhân với một hệ số nhỏ hơn để làm chậm quá trình thu phóng
            double zoomFactor = newValue.doubleValue() * 0.01; // 0.1 là hệ số làm chậm (có thể điều chỉnh theo nhu cầu)

            // Đảm bảo zoom không bị quá nhỏ (tránh giá trị âm hoặc quá nhỏ)
            zoomFactor = Math.max(0.01, zoomFactor); // Đảm bảo zoomFactor luôn >= 0.1

            scale.setX(zoomFactor); // Điều chỉnh tỷ lệ theo chiều ngang
            scale.setY(zoomFactor); // Điều chỉnh tỷ lệ theo chiều dọc
        });
    }
    
    @FXML
    public void NewClick(ActionEvent event) throws Exception {

        System.out.println("Work...");
        stage = (Stage) redoButton.getScene().getWindow();

        // Tải file FXML cho giao diện Data Structure Selection
        root = FXMLLoader.load(getClass().getResource("/main/fxml/work.fxml"));
        // Thiết lập giao diện mới
        scene = new Scene(root);
        // Đặt Scene mới vào Stage
        stage.setScene(scene);
        stage.setTitle("Visualize Tree");
        stage.show();
    }

    @FXML
    public void inirootClick(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/init.fxml"));
        Parent root = loader.load();
        stage = new Stage();
        stage.setTitle("Init Node");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void menuClick(ActionEvent event) throws Exception {
        stage = (Stage) redoButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/fxml/menu.fxml"));
        Parent root = loader.load();
        stage.setTitle("Visualize Tree");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handlePlay(ActionEvent event) throws Exception {
        if(typeTree == 0){
            visualizeTree(GenericT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
        } else if(typeTree == 1) {
            visualizeTree(BinaryT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
        } else if(typeTree == 2) {
            visualizeTree(BalanceT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
        } else if(typeTree == 3) {
            visualizeTree(BBT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
        }
    }

    private void visualizeTree(tree.Node node, double x, double y, double xOffset, double yOffset, boolean search) {
        
        if (node == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cây chưa được khởi tạo");
            alert.showAndWait();
            return;
        }
        // Vẽ nút hiện tại (hình tròn + giá trị)
        Circle circle;
        if (search && node.getData() == searchData) {
            circle = new Circle(x, y, 15, Color.ORANGE); // Node đang được tìm kiếm
        } else {
            circle = new Circle(x, y, 15, Color.BLACK); // Các node khác
        }
    
            Text text = new Text(String.valueOf(node.getData()));
            text.setFill(search && node.getData() == searchData ? Color.BLACK : Color.WHITE);
            text.setStyle("-fx-font-weight: bold;");
            text.setX(x - 5);
            text.setY(y + 5);
    
            // Thêm các thành phần vào giao diện
            treePane.getChildren().addAll(circle, text);
    
        // Kiểm tra và vẽ các nút con
        int totalChildren = node.getChildren().size();
        if (totalChildren == 0) return; // Nếu không có con, kết thúc
    
        double startX = x - (xOffset * (totalChildren - 1) / 2);
    
        for (int i = 0; i < totalChildren; i++) {
            tree.Node child = node.getChildren().get(i);
            double childX = startX + (i * xOffset); // Tính toán tọa độ x cho từng nút con
            double childY = y + yOffset; // Tọa độ y của nút con
    
            // Vẽ đường nối giữa nút cha và nút con
            Line line = new Line(x, y, childX, childY);
            treePane.getChildren().add(line);
    
            // Đệ quy vẽ các nút con
            visualizeTree(child, childX, childY, xOffset / 1.5, yOffset, search);
        }
    }

    private void updateNodePositionDFS(tree.Node node, double x, double y, double xOffset, double yOffset) {
        
        if (node == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cây chưa được khởi tạo");
            alert.showAndWait();
            return;
        }
        //lưu thong tin theo DFS
        listNodeInfosDFS.add(new nodeInfo(node.getData(), x, y));
        // Kiểm tra và vẽ các nút con
        int totalChildren = node.getChildren().size();
        if (totalChildren == 0) return; 
    
        // Tính toán khoảng cách giữa các nút con
        double startX = x - (xOffset * (totalChildren - 1) / 2);
    
        for (int i = 0; i < totalChildren; i++) {
            tree.Node child = node.getChildren().get(i);
            double childX = startX + (i * xOffset); // Tính toán tọa độ x cho từng nút con
            double childY = y + yOffset; // Tọa độ y của nút con

            updateNodePositionDFS(child, childX, childY, xOffset / 1.5, yOffset);
        }
    }

    private void updateNodePositionBFS(Node root, double x, double y, double xOffset, double yOffset) {
        listNodeInfosBFS.clear();
        listNodeInfosDFS.clear();
        //Lưu tất cả các node vào 1 mảng bao gồm giá trị và x, y
        updateNodePositionDFS(root, x, y, xOffset, yOffset);

        Queue<Node> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            listNodeInfosBFS.add(findnodeInfo(currentNode.getData()));
            for (Node child : currentNode.getChildren()) {
                queue.add(child);
            }
        }
    }

    private nodeInfo findnodeInfo(int data) {
        for (nodeInfo info : listNodeInfosDFS) {
            if (info.getData() == data) return info;
        }
        return null;
    }

    public void visualizeDFS(tree.Node node) {
        listNodeInfosDFS.clear();
        updateNodePositionDFS(node, treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75);
    
        timeline = new Timeline();
    
        // Tạo các KeyFrame với delay cố định (1 giây)
        for (int i = 0; i < listNodeInfosDFS.size(); i++) {
            nodeInfo nodeInfo = listNodeInfosDFS.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i), event -> drawNode(nodeInfo));
            timeline.getKeyFrames().add(keyFrame);
        }
    
        // Khi hoàn thành DFS
        timeline.setOnFinished(event -> {
            System.out.println("Visualization completed.");
            resumeButton.setDisable(true);
            pauseButton.setDisable(true);
        });
    
        // Bắt đầu chạy Timeline
        timeline.play();
        pauseButton.setDisable(false);
    
        // Nút Resume
        resumeButton.setOnAction(event -> {
            if (timeline.getStatus() == Animation.Status.PAUSED) {
                timeline.play();
                resumeButton.setDisable(true);
                pauseButton.setDisable(false);
            }
        });
    
        // Nút Pause
        pauseButton.setOnAction(event -> {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.pause();
                resumeButton.setDisable(false);
                pauseButton.setDisable(true);
            }
        });
    }
    
    public void visualizeBFS(Node root) {
        listNodeInfosBFS.clear();
        updateNodePositionBFS(root, treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75);

        timeline = new Timeline();
    
        // Tạo các KeyFrame với delay cố định (1 giây)
        for (int i = 0; i < listNodeInfosBFS.size(); i++) {
            nodeInfo nodeInfo = listNodeInfosBFS.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i), event -> drawNode(nodeInfo));
            timeline.getKeyFrames().add(keyFrame);
        }
    
        // Khi hoàn thành DFS
        timeline.setOnFinished(event -> {
            System.out.println("Visualization completed.");
            resumeButton.setDisable(true);
            pauseButton.setDisable(true);
        });
    
        // Bắt đầu chạy Timeline
        timeline.play();
        pauseButton.setDisable(false);
    
        // Nút Resume
        resumeButton.setOnAction(event -> {
            if (timeline.getStatus() == Animation.Status.PAUSED) {
                timeline.play();
                resumeButton.setDisable(true);
                pauseButton.setDisable(false);
            }
        });
    
        // Nút Pause
        pauseButton.setOnAction(event -> {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.pause();
                resumeButton.setDisable(false);
                pauseButton.setDisable(true);
            }
        });
    }

    private void drawNode(nodeInfo nodeInfo) {
        Circle circle = new Circle(nodeInfo.getX(), nodeInfo.getY(), 15, Color.ORANGE);
    
            Text text = new Text(String.valueOf(nodeInfo.getData()));
            text.setFill(Color.BLACK);
            text.setStyle("-fx-font-weight: bold;");
            text.setX(nodeInfo.getX() - 5);
            text.setY(nodeInfo.getY() + 5);

            treePane.getChildren().addAll(circle, text);
    }

    @FXML
    public void handleInsert(ActionEvent event) {
        try {
            // Tạo cửa sổ nhập liệu
            TextInputDialog parentDialog = new TextInputDialog();
            parentDialog.setTitle("Insert Operation");
            parentDialog.setHeaderText("Insert a new node");
            parentDialog.setContentText("Enter Parent Value:");

            Optional<String> parentResult = parentDialog.showAndWait();
            if (!parentResult.isPresent()) return; // Thoát nếu không nhập

            TextInputDialog childDialog = new TextInputDialog();
            childDialog.setTitle("Insert Operation");
            childDialog.setHeaderText("Insert a new node");
            childDialog.setContentText("Enter Child Value:");

            Optional<String> childResult = childDialog.showAndWait();
            if (!childResult.isPresent()) return;

            // Chuyển đổi giá trị từ chuỗi sang số
            int parentVal = Integer.parseInt(parentResult.get());
            int childVal = Integer.parseInt(childResult.get());

            if(typeTree == 0 && !GenericT.isFound(childVal)){
                saveStateForUndo();
                treePane.getChildren().clear();
                GenericT.insert(parentVal, childVal);
                visualizeTree(GenericT.getRoot(),  treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            } else if(typeTree == 1 && !BinaryT.isFound(childVal)){
                saveStateForUndo();
                treePane.getChildren().clear();
                BinaryT.insert(parentVal, childVal);
                visualizeTree(BinaryT.getRoot(),  treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            } else if(typeTree == 2 && !BalanceT.isFound(childVal)){
                saveStateForUndo();
                treePane.getChildren().clear();
                BalanceT.insert(parentVal, childVal);
                if(!BalanceT.isBalanced()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Cây không cân bằng\n Tiến hành cân bằng cây");
                    alert.show();
                    BalanceT.balance();
                }
                visualizeTree(BalanceT.getRoot(),  treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            } else if(typeTree == 3 && !BBT.isFound(childVal)){
                saveStateForUndo();
                treePane.getChildren().clear();
                BBT.insert(parentVal, childVal);
                if(!BBT.isBalanced()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Cây không cân bằng\n Tiến hành cân bằng cây");
                    alert.show();
                    BBT.balance();
                }
                visualizeTree(BBT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            } else {
                //Thông báo node đã tồn tại
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Node đã tồn tại");
                alert.show();
            }

        } catch (NumberFormatException e) { // Thông báo input ko hợp lệ
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid input");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Node");
        dialog.setHeaderText("Nhập giá trị nút cần xóa:");
        dialog.setContentText("Value:");
        dialog.showAndWait().ifPresent(deleteStr -> {
            try {
                int value = Integer.parseInt(deleteStr);

                if(typeTree == 0){
                    saveStateForUndo();
                    GenericT.delete(value);
                    checkAndRepaint();
                } else if(typeTree == 1){
                    saveStateForUndo();
                    BinaryT.delete(value);
                    checkAndRepaint();
                } else if(typeTree == 2){
                    saveStateForUndo();
                    BalanceT.delete(value);
                    checkAndRepaint();
                } else if(typeTree == 3){
                    saveStateForUndo();
                    BBT.delete(value);
                    checkAndRepaint();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid input");
                alert.show();
            }
        });
    }

    @FXML
    private void handleSearch() {
        searchFlag = true;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Node");
        dialog.setHeaderText("Nhập giá trị nút cần tìm:");
        dialog.setContentText("Value:");
        dialog.showAndWait().ifPresent(searchStr -> {
            try {
                int searchValue = Integer.parseInt(searchStr);
                searchData = searchValue;

                if(typeTree == 0 && GenericT.isFound(searchValue)){
                    checkAndRepaint();
                } else if(typeTree == 1 && BinaryT.isFound(searchValue)){
                    checkAndRepaint();
                } else if(typeTree == 2 && BalanceT.isFound(searchValue)){
                    checkAndRepaint();
                } else if(typeTree == 3 && BBT.isFound(searchValue)){
                    checkAndRepaint();
                } else {
                    System.out.println("Nut " + searchValue + " da ton tai!\n");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Node không tồn tại");
                    alert.show();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Invalid input");
                alert.show();
            }
        });
        searchFlag = false;
    }

    @FXML
    private void handleUpdate() {
        TextInputDialog currentDialog = new TextInputDialog();
        currentDialog.setTitle("Update Node");
        currentDialog.setHeaderText("Nhập giá trị nút hiện tại:");
        currentDialog.setContentText("Current Value:");
        currentDialog.showAndWait().ifPresent(currentStr -> {
            TextInputDialog newDialog = new TextInputDialog();
            newDialog.setTitle("Update Node");
            newDialog.setHeaderText("Nhập giá trị mới:");
            newDialog.setContentText("New Value:");
            newDialog.showAndWait().ifPresent(newStr -> {
                try {
                    int current = Integer.parseInt(currentStr);
                    int newValue = Integer.parseInt(newStr);

                    if(typeTree == 0 && !GenericT.isFound(newValue)){
                        saveStateForUndo();
                        GenericT.update(current, newValue);
                        System.out.println("Đã cập nhật nút " + current + " thành " + newValue + "\n");
                        checkAndRepaint();
                        showSuccessUpdate();
                    } else if(typeTree == 1 && !BinaryT.isFound(newValue)){
                        saveStateForUndo();
                        BinaryT.update(current, newValue);
                        System.out.println("Đã cập nhật nút " + current + " thành " + newValue + "\n");
                        checkAndRepaint();
                        showSuccessUpdate();
                    } else if(typeTree == 2 && !BalanceT.isFound(newValue)){
                        saveStateForUndo();
                        BalanceT.update(current, newValue);
                        System.out.println("Đã cập nhật nút " + current + " thành " + newValue + "\n");
                        checkAndRepaint();
                        showSuccessUpdate();
                    } else if(typeTree == 3 && !BBT.isFound(newValue)){
                        saveStateForUndo();
                        BBT.update(current, newValue);
                        System.out.println("Đã cập nhật nút " + current + " thành " + newValue + "\n");
                        checkAndRepaint();
                        showSuccessUpdate();
                    } else {
                        System.out.println("Nut " + newValue + " da ton tai!\n");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Node đã tồn tại");
                        alert.show();
                    }
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid input");
                    alert.show();
                }
            });
        });
    }

    @FXML
    private void handleDFS() {
        if(typeTree == 0 && GenericT.getRoot()!= null){
            visualizeDFS(GenericT.getRoot());
        } else if(typeTree == 1 && BinaryT.getRoot()!= null){
            visualizeDFS(BinaryT.getRoot());
        } else if(typeTree == 2 && BalanceT.getRoot()!= null){
            visualizeDFS(BalanceT.getRoot());
        } else if(typeTree == 3 && BBT.getRoot()!= null){
            visualizeDFS(BBT.getRoot());
        } else {
            System.out.println("Cay rong!\n");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cây Rỗng");
            alert.show();
        }
        // In kết quả

        // Trả lại diện mạo cũ
        checkAndRepaint();
    }

    @FXML
    private void handleBFS() {
        if(typeTree == 0 && GenericT.getRoot()!= null){
            visualizeBFS(GenericT.getRoot());
        } else if(typeTree == 1 && BinaryT.getRoot()!= null){
            visualizeBFS(BinaryT.getRoot());
        } else if(typeTree == 2 && BalanceT.getRoot()!= null){
            visualizeBFS(BalanceT.getRoot());
        } else if(typeTree == 3 && BBT.getRoot()!= null){
            visualizeBFS(BBT.getRoot());
        } else {
            System.out.println("Cay rong!\n");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cây Rỗng");
            alert.show();
        }
        // In kết quả

        // Trả lại diện mạo cũ
        checkAndRepaint();
    }



    public void showSuccessUpdate() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Update thành công");
        alert.show();
    }

    private void saveStateForUndo() {
        if(typeTree == 0){
            undoStackGT.push(GenericT.copyTree());
            redoStackGT.clear();
        } else if(typeTree == 1) {
            undoStackBiT.push(BinaryT.copyTree());
            redoStackBiT.clear();
        } else if(typeTree == 2) {
            undoStackBaT.push(BalanceT.copyTree());
            redoStackBaT.clear();
        } else if(typeTree == 3) {
            undoStackBBT.push(BBT.copyTree());
            redoStackBBT.clear();
        }
    }

    @FXML
    public void handleUndo() {
        if(typeTree == 0){
            if (!undoStackGT.isEmpty()) {
                redoStackGT.push(GenericT.copyTree());
                GenericT = undoStackGT.pop();
                checkAndRepaint();
            } else {
                showCantUndoStack();
            }
        } else if(typeTree == 1) {
            if (!undoStackBiT.isEmpty()) {
                redoStackBiT.push(BinaryT.copyTree());
                BinaryT = undoStackBiT.pop();
                checkAndRepaint();
            } else {
                showCantUndoStack();
            }
        } else if(typeTree == 2) {
            if (!undoStackBaT.isEmpty()) {
                redoStackBaT.push(BalanceT.copyTree());
                BalanceT = undoStackBaT.pop();
                checkAndRepaint();
            } else {
                showCantUndoStack();
            }
        } else if(typeTree == 3) {
            if (!undoStackBBT.isEmpty()) {
                redoStackBBT.push(BBT.copyTree());
                BBT = undoStackBBT.pop();
                checkAndRepaint();
            } else {
                showCantUndoStack();
            }
        }
    }

    public void showCantUndoStack() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setContentText("Không thể undo thêm nữa.");
        alert.show();
    }

    @FXML
    public void handleRedo() {
        if(typeTree == 0){
            if (!redoStackGT.isEmpty()) {
                undoStackGT.push(GenericT.copyTree());
                GenericT = redoStackGT.pop();
                checkAndRepaint();
            } else {
                showCantRedoStack();
            }
        } else if(typeTree == 1) {
            if (!redoStackBiT.isEmpty()) {
                undoStackBiT.push(BinaryT.copyTree());
                BinaryT = redoStackBiT.pop();
                checkAndRepaint();
            } else {
                showCantRedoStack();
            }
        } else if(typeTree == 2) {
            if (!redoStackBaT.isEmpty()) {
                undoStackBaT.push(BalanceT.copyTree());
                BalanceT = redoStackBaT.pop();
                checkAndRepaint();
            } else {
                showCantRedoStack();
            }
        } else if(typeTree == 3) {
            if (!redoStackBBT.isEmpty()) {
                undoStackBBT.push(BBT.copyTree());
                BBT = redoStackBBT.pop();
                checkAndRepaint();
            } else {
                showCantRedoStack();
            }
        }
    }

    public void showCantRedoStack() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setContentText("Không thể redo thêm nữa.");
        alert.show();
    }
    private void checkAndRepaint() {
        treePane.getChildren().clear();
        if(typeTree == 0){
            if (GenericT != null && GenericT.getRoot() != null) {
                visualizeTree(GenericT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            }
        } else if(typeTree == 1) {
            if (BinaryT != null && BinaryT.getRoot() != null) {
                visualizeTree(BinaryT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            }
        } else if(typeTree == 2) {
            if (BalanceT != null && BalanceT.getRoot() != null) {
                visualizeTree(BalanceT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            }
        } else if(typeTree == 3) {
            if (BBT != null && BBT.getRoot() != null) {
                visualizeTree(BBT.getRoot(), treePane.getWidth() / 2, 50, treePane.getWidth() / 4, 75, searchFlag);
            }
        }
    }

    @FXML
    public void handleHelpClicked(ActionEvent event) throws Exception {
        System.out.println("Loading help...");
        // Tải file FXML cho giao diện help
        stage = (Stage) redoButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/main/fxml/help.fxml"));
        
        // Thiết lập giao diện Help
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.setTitle("Help");
        stage.show();
    }

    @FXML void handleQuit(ActionEvent event) {
        // Tạo hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác Nhận Thoát");
        alert.setHeaderText("Bạn có chắc chắn muốn thoát ứng dụng?");
        alert.setContentText("Nhấn \"OK\" để thoát, hoặc \"Cancel\" để quay lại.");

        // Hiển thị hộp thoại và chờ phản hồi từ người dùng
        Optional<ButtonType> result = alert.showAndWait();

        // Kiểm tra nếu người dùng chọn OK
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Quitting...");
            System.exit(0); // Thoát ứng dụng
        } else {
            System.out.println("Quitting canceled.");
        }
    }
}
