package DoAnJava.service;



import DoAnJava.Utils.Utils;
import DoAnJava.entities.OrderStatus;
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
        System.out.println("Chào mừng "+user.getName()+", bạn có thể thực hiện các công việc sau:");
        do {
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            System.out.println("1. Xem thong tin tat ca san pham                2. Order san pham               3. Don hang");
            System.out.println("4. Xem thong tin ca nhan                        5. Bao mat tai khoan            6. Dang xuat");
            System.out.print("Enter your choice: ");
            choose = utils.inputInt(scanner);
            switch (choose){
                case 1:
                    productService.viewProduct(productMap);
                    break;
                case 2:
                    orderService.order(scanner, user, orders, productMap);
                    break;
                case 3:
                    myOrder(scanner, orders, user, productMap);
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
            System.out.println("1 - Thay doi username            2 - Thay doi mat khau           3 - Thay doi email              4 - Thoat");
            System.out.print("Enter your choice :");
            int select=utils.inputInt(scanner);
            switch (select) {
                case 1 -> userService.updateUsername(scanner, users, user);
                case 2 -> userService.updatePassword(scanner, user);
                case 3 -> userService.updateEmail(scanner, users, user);
                case 4 -> isOut = true;
            }
        }while (!isOut);
    }

    public void myOrder(Scanner scanner,ArrayList<Orders> orders, User user, Map<Integer, Product> productMap){

        System.out.println("==== DON HANG CUA BAN ====");
        do {
            System.out.println("1. Xem tat ca don hang cua ban da order        2. Xem thong tin tat ca don hang cua ban              3. Xem cac don hang da huy");
            System.out.println("4. Huy don hang                                5. Xem cac don hang da giao thanh cong                6. Thoat");
            System.out.print("Enter your choie:");
            int choice = utils.inputInt(scanner);
            switch (choice){
                case 1:
                    orderService.viewUserOrders(orders, user, productMap);
                    break;
                case 2:
                    orderService.viewAllOrderUser(orders, user, productMap);
                    break;
                case 3:
                    orderService.viewCancelOrdersUser(orders, user, productMap);
                    break;
                case 4:
                    orderService.canceledOrder(scanner, orders, productMap, user);
                    break;
                case 5:
                    orderService.viewSuccessfulOrdersUser(orders, user, productMap);
                case 6:
                    return;
                default:
                    System.out.println("Lua chon khong hop le , vui long chon lai");
                    break;
            }
        }while (true);

    }
}

