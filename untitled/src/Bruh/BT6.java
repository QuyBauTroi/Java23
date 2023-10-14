package Bruh;

import java.util.Scanner;

public class BT6 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Rectangle rectangle = new Rectangle();
        System.out.println("Nhap chieu dai:");
        rectangle.setLength(scanner.nextDouble());
        System.out.println("Nhap chieu rong:");
        rectangle.setWidth(scanner.nextDouble());
        System.out.println("Dien tich HCN :"+ rectangle.calculateArea());
        System.out.println("Chu vi HCN :"+ rectangle.calculatePerimeter());


        Sqe sqe = new Sqe();
        System.out.println("Nhap canh HV:");
        sqe.setSide(scanner.nextDouble());
        System.out.println("Dien tich HV:"+ sqe.calculateArea());
        System.out.println("Chu vi HV:"+ sqe.calculatePerimeter());

    }
}
