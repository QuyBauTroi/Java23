package DoAnJava.service;




import DoAnJava.Utils.Utils;
import DoAnJava.entities.Product;
import DoAnJava.entities.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ManagerAccountService {
    Utils utils = new Utils();
    ProductService productService = new ProductService();

    public void menuManager(Scanner scanner, ArrayList<User> users, UserService userService, Map<Integer, Product> productMap) {
        int choice;
        do {
        System.out.println("1 - Tạo tài khoản cho nhân viên");
        System.out.println("2 - Đăng bài bán sản phẩm");
        System.out.println("3. Xem tất cả sản phẩm");
        System.out.println("4. Xoá sản phẩm");
        System.out.println("5. Xem thong tin cua tat ca nhan vien");
        System.out.println("6. Cập nhật sản phẩm");
        System.out.println("7. Đăng xuất");
        System.out.println("---------- Enter your choice -----------");
        choice= utils.inputInt(scanner);
        switch (choice) {
                case 1:
                    userService.inputRegisterStaff(scanner, users);
                    break;
                case 2:
                    productService.addProduct(scanner,productMap);
                    break;
                case 3:
                    productService.viewProduct(productMap);
                    break;
                case 4:
                    productService.deleteProduct(scanner,productMap);
                    break;
                case 5:
                    userService.displayStaffInformation(users);
                    break;
                case 6:
                    productService.updateProductByFindId(scanner,productMap);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }





}