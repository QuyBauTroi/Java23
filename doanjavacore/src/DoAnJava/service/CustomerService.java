package DoAnJava.service;



import DoAnJava.Utils.Utils;
import DoAnJava.entities.Orders;
import DoAnJava.entities.Product;
import DoAnJava.entities.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class CustomerService {
    Utils utils = new Utils();
    ProductService productService = new ProductService();
    OrderService orderService = new OrderService();

    public void menuCustomer(Scanner scanner, ArrayList<User> users, User user, UserService userService, Map<Integer, Product> productMap, ArrayList<Orders> orders){
        int choose;
        do {
            System.out.println("Chào mừng "+user.getUsername()+", bạn có thể thực hiện các công việc sau:");
            System.out.println("1. Xem thong tin tat ca san pham");
            System.out.println("2. Order san pham");
            System.out.println("3. Xem thong tin don hang cua ban than");
            System.out.println("4. Xem thong tin ca nhan");
            System.out.println("5. Bao mat tai khoan");
            System.out.println("6. Dang xuat");
            choose = utils.inputInt(scanner);
            switch (choose){
                case 1:
                    productService.viewProduct(productMap);
                    break;
                case 2:
                    orderService.order(scanner, user, orders, productMap);
                    break;
                case 3:
                    orderService.viewUserOrders(orders,user,productMap);
                    break;
                case 4:
                    userService.information(scanner, user);
                    break;
                case 5:
                    informationCustomer(scanner, user, users, userService);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Lua chọn không hợp lệ , vui lòng chọn lại");
                    break;
            }
        }while (true);
    }

    public void informationCustomer(Scanner scanner, User user,ArrayList<User> users, UserService userService){
        boolean isOut=false;
        do {
            System.out.println("Thay doi thong tin tai khoan:");
            System.out.println("1 - Thay doi username");
            System.out.println("2 - Thay doi mat khau");
            System.out.println("3 - Thay doi email");
            System.out.println("4 - Thoat");
            System.out.print("Mời bạn chọn:");
            int select=utils.inputInt(scanner);
            switch (select) {
                case 1 -> userService.updateUsername(scanner, users, user);
                case 2 -> userService.updatePassword(scanner, user);
                case 3 -> userService.updateEmail(scanner, users, user);
                case 4 -> isOut = true;
            }
        }while (!isOut);
    }
}

