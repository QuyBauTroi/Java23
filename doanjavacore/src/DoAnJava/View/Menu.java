package DoAnJava.View;


import DoAnJava.Utils.Utils;
import DoAnJava.entities.Orders;
import DoAnJava.entities.Product;
import DoAnJava.entities.User;
import DoAnJava.service.ProductService;
import DoAnJava.service.UserService;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    ProductService productService = new ProductService();
    Utils utils = new Utils();
    public void optionMenu(Scanner scanner, ArrayList<User> users, UserService userService, Map<Integer, Product> productMap, ArrayList<Orders> orders){
        boolean isContinue = true;
        do {
            System.out.println("==== Chao mung ban den QuyStore ====");
            System.out.println("1-Xem tat ca san pham");
            System.out.println("2-Dang nhap");
            System.out.println("3-Dang ky");
            System.out.println("Enter your choice: ");
            int select= utils.inputInt(scanner);
            switch (select) {
                case 1 -> productService.viewProduct(productMap);
                case 2 -> isContinue = userService.inputLogin(scanner, users, userService, productMap, orders);
                case 3 -> userService.inputRegister(scanner, users);
            }
        }while (isContinue);
    }
}
