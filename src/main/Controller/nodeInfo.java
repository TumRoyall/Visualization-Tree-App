package main.Controller;

public class nodeInfo {
    private int data;
    private double x;
    private double y;

    public nodeInfo(int data, double x, double y) {
        this.data = data;
        this.x = x;
        this.y = y;
    }

    //gett setter
    public int getData() {
        return data;
    }
    public void setData(int data) {
        this.data = data;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
}
