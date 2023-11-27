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
        System.out.println("Chào mừng Admin bạn có thể thực hiện các công việc sau:");
        do {
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        System.out.println("1. Tao tai khoan nhan vien                  2. Dang bai ban san pham                3. Xem tat ca san pham");
        System.out.println("4. Cap nhat san pham                        5. Xoa san pham                         6. Quan ly nhan vien");
        System.out.println("7. Dang xuat");
        System.out.print("Enter your choice:");
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
                    productService.updateProductByFindId(scanner,productMap);
                    break;
                case 5:
                    productService.deleteProduct(scanner,productMap);
                    break;
                case 6:
                    employeeManager(users, scanner);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (true);
    }

    // Phương thức in ra danh sách nhân viên
    public void employeeManager(ArrayList<User> users,Scanner scanner) {
        do {
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("1.Danh sach tat ca nhan vien            2. Tim kiem nhan vien               3. Xoa tai khoan nhan vien                 4. Thoat");
            System.out.print("Enter your choice");
            int choice = utils.inputInt(scanner);
            switch (choice){
                case 1:
                    displayStaffInformation(users);
                case 2:
                    searchUserByName(users, scanner);
                    break;
                case 3:
                    deleteStaffAccount(users, scanner);
                    break;
                case 4:
                    return;
            }
        }while (true);
    }


    public void displayStaffInformation(ArrayList<User> users){
        System.out.println("=======DANH SÁCH NHÂN VIÊN=======");
        for (User user : users) {
            if (user.getRole() == 1) {
                System.out.print("Username : " + user.getUsername()  );
                System.out.print(" || Email  : " + user.getEmail()  );
                System.out.print(" || ID nhân viên :" + user.getId());
                System.out.print(" || Tên nhân viên : " + user.getName());
                System.out.print(" || Số Điện Thoại: " + user.getPhoneNumber());
                System.out.println(" || Địa Chỉ : " + user.getAddress());

            }
        }
    }



    public void deleteStaffAccount(ArrayList<User> users, Scanner scanner) {
        System.out.print("Nhập username của nhân viên cần xóa: ");
        String usernameToDelete = scanner.nextLine();
        // Tìm và xóa người dùng theo username
        boolean userFound = false;
        for (User user : users) {
            if (user.getUsername().equals(usernameToDelete) && user.getRole() == 1) {
                users.remove(user);
                userFound = true;
                System.out.println("Đã xóa tài khoản của nhân viên " + usernameToDelete);
                break;
            }
        }
        // Thông báo nếu không tìm thấy hoặc xóa không thành công
        if (!userFound) {
            System.out.println("Không tìm thấy hoặc không thể xóa tài khoản của nhân viên " + usernameToDelete);
        }
    }





    // Ví dụ cách sử dụng phương thức tìm kiếm theo tên
    public void searchUserByName(ArrayList<User> users, Scanner scanner) {
        System.out.print("Nhập tên người dùng cần tìm: ");
        String nameToSearch = scanner.nextLine();

        User foundUser = findUserByName(nameToSearch, users);

        if (foundUser != null) {
            System.out.println("Người dùng có tên " + nameToSearch + " được tìm thấy:");
            System.out.print(" || Username: " + foundUser.getUsername());
            System.out.print(" || Email: " + foundUser.getEmail());
            System.out.print(" || ID: " + foundUser.getId());
            System.out.print(" || Số Điện Thoại: " + foundUser.getPhoneNumber());
            System.out.println(" || Địa Chỉ: " + foundUser.getAddress());
        } else {
            System.out.println("Không tìm thấy người dùng có tên " + nameToSearch);
        }
    }


    // Phương thức tìm người dùng theo tên
    public User findUserByName(String name, ArrayList<User> users) {
        for (User user : users) {
            if (!name.equalsIgnoreCase(user.getName())) {
                continue;
            }
            return user; // Trả về người dùng nếu tìm thấy
        }
        return null; // Trả về null nếu không tìm thấy
    }
}