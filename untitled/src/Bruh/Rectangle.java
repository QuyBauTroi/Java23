package Bruh;

public class Rectangle extends Geometry{
    private double length;
    private double width;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
    public double calculateArea(){
        return   width * length;
    }
    public double calculatePerimeter(){
        return (width+length)*2;
    }
}
