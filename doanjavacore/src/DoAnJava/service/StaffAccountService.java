package DoAnJava.service;


import DoAnJava.Utils.Utils;
import DoAnJava.entities.Orders;
import DoAnJava.entities.Product;
import DoAnJava.entities.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class StaffAccountService {
    Utils utils = new Utils();
    ProductService productService = new ProductService();
    OrderService orderService = new OrderService();

    public void menuStaff(Scanner scanner, ArrayList<User> users, User user, UserService userService, Map<Integer, Product> productMap, ArrayList<Orders> orders){
        int choice;
        do {
            System.out.println("1. Xem thong tin tat ca san pham");
            System.out.println("2. Xem thong tin tat ca don hang");
            System.out.println("3. Xem thong tin ca nhan");
            System.out.println("4. Thay doi username");
            System.out.println("5. Thay doi email");
            System.out.println("6. Thay doi password");
            System.out.println("7. Dang xuat");
            System.out.println("---------- Enter your choice -----------");
            choice = utils.inputInt(scanner);
            switch (choice){
                case 1:
                    productService.viewProduct(productMap);
                    break;
                case 2:
                    orderService.viewOrders(orders);
                    break;
                case 3:
                    userService.infoMenu(scanner, user);
                    break;
                case 4:
                    userService.updateUsername(scanner, users, user);
                    break;
                case 5:
                    userService.updateEmail(scanner, users, user);
                    break;
                case 6:
                    userService.updatePassword(scanner, user);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lua chon khong hop le, vui long chon lai:");
                    break;
            }
        }while (true);
    }


}
