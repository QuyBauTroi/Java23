package Bai6_3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bus bus = new Bus();
        System.out.println("Nhap quang duong xe Bus di:");
        bus.setDistance(scanner.nextDouble());
        System.out.println("Thoi gian xe Bus di duoc la:"+bus.claculateTimeArrive());



        Train train = new Train();
        System.out.println("Nhap quang duong Tau hoa di:");
        train.setDistance(scanner.nextDouble());
        System.out.println("Thoi gian Tau di duoc la:"+train.claculateTimeArrive());



        Plane plane = new Plane();
        System.out.println("Nhap quang duong may bay hoa di:");
        plane.setDistance(scanner.nextDouble());
        System.out.println("Thoi gian May Bay di duoc la:"+plane.claculateTimeArrive());
    }
}
