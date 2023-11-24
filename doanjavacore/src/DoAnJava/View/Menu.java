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
    public void optionMenu(Scanner scanner, ArrayList<User> users, UserService userService, Map<Integer, Product> productMap, ArrayList<Orders> orders, Product product){
        boolean isContinue = true;
        do {
            System.out.println("1-Xem tất cả sản phẩm");
            System.out.println("2-Đăng nhập");
            System.out.println("3-Dăng ký");
            System.out.println("Mời bạn lựa chọn: ");
            int select= utils.inputInt(scanner);
            switch (select) {
                case 1 -> productService.viewProduct(productMap);
                case 2 -> isContinue = userService.inputLogin(scanner, users, userService, productMap, orders, product);
                case 3 -> userService.inputRegister(scanner, users);
            }
        }while (isContinue);
    }
}
