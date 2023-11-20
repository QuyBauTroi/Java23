package DoAnJava.Utils;

import java.util.Scanner;

public class Utils {
        public Double inputDouble(Scanner scanner){
            while (true){
                try {
                    return Double.parseDouble(scanner.nextLine());
                }catch (Exception e){
                    System.out.println("Nhap sai cu phap, vui long nhap lai:");
                }
            }
        }
        public int inputInt(Scanner scanner) {
            while (true) {
                try {
                    return Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("Nhap sai cu phap, vui long nhap lai:");
                }
            }
        }
    public String inputString(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Nhap sai cu phap, vui long nhap lai:");
            }
        }
    }
}
