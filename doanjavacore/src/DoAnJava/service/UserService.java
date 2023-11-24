package DoAnJava.service;


import DoAnJava.Utils.Utils;
import DoAnJava.entities.Orders;
import DoAnJava.entities.Product;
import DoAnJava.entities.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class UserService {
    Utils utils=new Utils();

    // Phần đăng ký tài khoản người dùng ( chỉ người dùng được đăng ký )
    public void inputRegister(Scanner scanner, ArrayList<User> users){
        System.out.println("ĐĂNG KÝ");
        System.out.print("Mời bạn nhập username: ");
        String username=scanner.nextLine();
        User user=new User();
        user.setUsername(username);
        user.setRole(2);
        if (checkUsername(username,users)){
            inputRegister(scanner,users);
        }else {
            System.out.print("Mời bạn nhập email: ");
            String email = scanner.nextLine();
            if (checkEmail(email,users)){
                inputRegister(scanner,users);
            }else {
                user.setEmail(email);
                System.out.print("Mời bạn nhập password: ");
                String password=scanner.nextLine();
                if(checkPassword(password)){
                    inputRegister(scanner,users);
                }else{
                    user.setPassword(password);
                    System.out.println("Xác thực thông tin tài khoản");
                    System.out.print("Mời bạn nhập tên: ");
                    user.setName(scanner.nextLine());
                    System.out.print("Mời bạn nhập SĐT: ");
                    user.setPhoneNumber(scanner.nextLine());
                    System.out.print("Mời bạn nhập địa chỉ: ");
                    user.setAddress(scanner.nextLine());
                    users.add(user);
                    System.out.println("Đăng ký thành công!!!");
                    }
                }
            }
        }






    //  - Phần đăng ký tài khoản nhân viên mới ( chỉ dược tạo bởi Quản lý)
    public void inputRegisterStaff(Scanner scanner, ArrayList<User> users){
        System.out.println("ĐĂNG KÝ TÀI KHOẢN NHÂN VIÊN");
        System.out.print("Mời bạn nhập username:");
        String username=scanner.nextLine();
        User user=new User();
        user.setUsername(username);
        user.setRole(1);
        if (checkUsername(username,users)){
            inputRegister(scanner,users);
        }else {
            System.out.print("Mời bạn nhập email:");
            String email = scanner.nextLine();
            if (checkEmail(email,users)){
                inputRegister(scanner,users);
            }else {
                user.setEmail(email);
                System.out.print("Mời bạn nhập password:");
                String password=scanner.nextLine();
                if(checkPassword(password)){
                    inputRegister(scanner,users);
                }else{
                    user.setPassword(password);
                    System.out.println("Xác thực thông tin tài khoản");
                    System.out.print("Mời bạn nhập tên của nhân viên:");
                    user.setName(scanner.nextLine());
                    System.out.print("Mời bạn nhập SĐT của nhân viên:");
                    user.setPhoneNumber(scanner.nextLine());
                    System.out.print("Mời bạn nhập địa chỉ của nhân viên:");
                    user.setAddress(scanner.nextLine());
                    users.add(user);
                    System.out.println("Đăng ký thành công!!!");
                }
            }
        }
    }




    // Phương thức in ra danh sách nhân viên
    public void displayStaffInformation(ArrayList<User> users) {
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






    // Phương thức đăng nhập tài khoản
    public boolean inputLogin(Scanner scanner, ArrayList<User> users, UserService userService, Map<Integer, Product> productMap, ArrayList<Orders> orders, Product product){
        StaffAccountService staffAccountService = new StaffAccountService();
        ManagerAccountService managerAccountService = new ManagerAccountService();
        CustomerService customerService = new CustomerService();
        String select="";
        boolean isUserNameRight = false;
        boolean isContinue = true;
        do {
            System.out.println("ĐĂNG NHẬP");
            System.out.print("Mời bạn nhập username:");
            String username = scanner.nextLine();
            for (User userValue : users) {
                if (username.equals(userValue.getUsername())) {
                    System.out.print("Mời bạn nhập password:");
                    String password = scanner.nextLine();
                    isUserNameRight = true;
                    if (password.equals(userValue.getPassword())) {
                        System.out.println("Đăng nhập thành công!!!");
                        if (userValue.getRole()==1){
                            staffAccountService.menuStaff(scanner,users,userValue,userService,productMap,orders,product);
                        }else if (userValue.getRole()==2){
                            customerService.menuCustomer(scanner,users,userValue,userService,productMap,orders,product);
                        }else {
                            managerAccountService.menuManager(scanner,users,userService,productMap);
                        }
                    } else {
                        System.out.println("Mật khẩu sai!");
                        System.out.println("Mời bạn chọn:");
                        System.out.println("1-Đăng nhập lại");
                        System.out.println("2-Quên mật khẩu");
                        System.out.print("Mời bạn chọn:");
                        int choice = utils.inputInt(scanner);
                        switch (choice) {
                            case 1 -> isContinue = inputLogin(scanner, users, userService, productMap, orders,product);
                            case 2 -> forgetPassword(scanner, users);
                        }
                    }
                    break;
                }
            }
            if(!isUserNameRight){
                System.out.println("Kiểm tra lại username");
                System.out.print("Bạn có muốn nhập lại không(Y/N): ");
                select = scanner.nextLine();
            }
        }while (select.equalsIgnoreCase("Y"));
        return isContinue;
    }







    // Phương thức lấy lại mật khẩu đã mất
    private void forgetPassword(Scanner scanner, ArrayList<User> users){
        boolean isEmailRight =false;
        System.out.print("Mời bạn nhập email:");
        String email= scanner.nextLine();
        for (User userValue : users){
            if (email.equals(userValue.getEmail())){
                System.out.print("Mời bạn nhập lại password:");
                String password= scanner.nextLine();
                userValue.setPassword(password);
                System.out.println("Đổi mật khẩu thành công!!!");
                isEmailRight = true;
                break;
            }
        }
        if (!isEmailRight){
            System.out.println("Kiểm tra lại email");
            System.out.print("Bạn có muốn nhập lại không (Y/N): ");
            String select = scanner.nextLine();
            if (select.equalsIgnoreCase("y")){
                forgetPassword(scanner,users);
            }
        }
    }






    // Phương thức hiển thị thông tin cá nhân và cập nhật thông tin cá nhân
    public void information(Scanner scanner, User user){
        boolean isOut=false;
        do {
            System.out.println("Hồ sơ cá nhân:");
            System.out.println("1 - Thông tin cá nhân");
            System.out.println("2 - Cập nhật thông tin cá nhân");
            System.out.println("3 - Thoát hồ sơ cá nhân");
            System.out.print("Mời bạn chọn:");
            int select=utils.inputInt(scanner);
            switch (select) {
                case 1 -> printInfo(user);
                case 2 -> updateInfoMenu(scanner, user);
                case 3 -> isOut = true;
            }
        }while (!isOut);
    }
    public void printInfo(User user){
        System.out.print("Tên của bạn là: ");
        System.out.println(user.getName());
        System.out.print("SDT của bạn là: ");
        System.out.println(user.getPhoneNumber());
        System.out.print("Địa chỉ của bạn là:");
        System.out.println(user.getAddress());
    }
    public void updateInfoMenu(Scanner scanner, User user){
        boolean isOut=false;
        do {
            System.out.println("Cập nhật thông tin cá nhân:");
            System.out.println("1 - Cập nhật tên");
            System.out.println("2 - Cập nhật SĐT");
            System.out.println("3 - Cập nhật địa chỉ");
            System.out.println("4 - Thoát hồ sơ cá nhân");
            System.out.print("Mời bạn chọn:");
            int select=utils.inputInt(scanner);
            switch (select) {
                case 1 -> updateName(scanner, user);
                case 2 -> updatePhoneNumber(scanner, user);
                case 3 -> updateAddress(scanner, user);
                case 4 -> isOut = true;
            }
        }while (!isOut);
    }









    // ----Phương thức thay đổi Username , Email , Password------ //
    public void updateUsername(Scanner scanner,ArrayList<User> users, User user){
        System.out.print("Mời bạn nhập username mới:");
        String username = scanner.nextLine();
        boolean isError = checkUsername(username, users);
        if (!isError){
            user.setUsername(username);
            System.out.println("Cap nhat username thanh cong");
        }
    }
    public void updateEmail(Scanner scanner, ArrayList<User> users, User user) {
        boolean validEmail = false;
        do {
            try {
                System.out.print("Mời bạn nhập email mới:");
                String email = scanner.nextLine();
                // Kiểm tra định dạng email và xem email đã tồn tại trong danh sách người dùng chưa
                boolean isError = checkEmail(email, users);
                if (!isError) {
                    user.setEmail(email);
                    validEmail = true;
                    System.out.println("Cap nhat email thanh cong");
                }
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi khi nhập email. Vui lòng nhập lại.");
                scanner.nextLine();
            }
        } while (!validEmail);
    }
    public void updatePassword(Scanner scanner, User user) {
        boolean validPassword = false;
        do {
            try {
                System.out.print("Mời bạn nhập password mới:");
                String password = scanner.nextLine();
                boolean isError = checkPassword(password);// Kiểm tra định dạng password
                if (!isError) {
                    user.setPassword(password);
                    validPassword = true;
                    System.out.println("Cap nhat password thanh cong");
                }
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi khi nhập password. Vui lòng nhập lại.");
                scanner.nextLine();
            }
        } while (!validPassword);
    }









    //  -----Phương thức thay đổi  " Thông Tin Tài Khoản "------ //
    public void updateName(Scanner scanner, User user){
        System.out.print("Mời bạn nhập tên mới:");
        user.setName(scanner.nextLine());
    }
    public void updatePhoneNumber(Scanner scanner, User user){
        System.out.print("Mời bạn cập nhật tuổi: ");
        user.setPhoneNumber(scanner.nextLine());
    }
    public void updateAddress(Scanner scanner, User user){
        System.out.print("Mời bạn cập nhật địa chỉ:");
        user.setAddress(scanner.nextLine());
    }










    //  ------ Phương thức kiểm tra Username , Email và Password  -------- //
    private boolean checkUsername(String userName, ArrayList<User> users){
        boolean isError =false;
           for (User userValue : users){
              if (userValue.getUsername().equals(userName)){
                  isError=true;
                  System.out.println("Username đã tồn tại");
                  break;
              }
           }
    return isError;
    }
    public boolean checkEmail(String email, ArrayList<User> users){
        boolean isError =false;
        String regex = "^(.+)@(.+)$";
        if(email.matches(regex)){
            for (User userValue : users){
                if (userValue.getEmail().equals(email)){
                    isError = true;
                    System.out.println("Email này ton tai, vui long nhap lai");
                    break;
                }
            }
        }else{
            System.out.println("Sai định dạng Email, vui long nhập lại ");
            isError = true;
        }
        return isError;
    }
    private boolean checkPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[.,-_;])[A-Za-z.,-_;]{7,15}$";
        boolean isError = false;
        if (!password.matches(regex)) {
            System.out.println("Mật khẩu sai định dạng , vui lòng nhập lại");
            isError = true;
        }
        return isError;
    }

}
