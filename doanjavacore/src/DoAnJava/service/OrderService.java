package DoAnJava.service;


import DoAnJava.Utils.Utils;
import DoAnJava.entities.Orders;
import DoAnJava.entities.Product;
import DoAnJava.entities.STATUS;
import DoAnJava.entities.User;

import java.time.LocalDate;
import java.util.*;

public class OrderService {
    Utils utils = new Utils();
    ProductService productService = new ProductService();
    // Phương thức order sản phẩm //
    public void order(Scanner scanner , User user, ArrayList<Orders> orders, Map<Integer, Product> productMap) {
        // Hiển thị danh sách sản phẩm
        productService.viewProduct(productMap);
        // Nhập sản phẩm muốn mua bằng ID
        System.out.print("Nhap id  san pham ban muon mua:");
        int productId = utils.inputInt(scanner);
        Product product = productMap.get(productId);
        if (product != null) {
            System.out.print("Nhap so luong san pham muon order:");
            int numberOfProducts = utils.inputInt(scanner);
            // Kiểm tra số lượng đặt hàng có đủ hay không
            if (numberOfProducts <= product.getQuantity()) {
                // Giảm số lượng trong danh sách sản phẩm
                product.setQuantity(product.getQuantity() - numberOfProducts);
            }
            // Phuong thuc kiem tra san pham ( neu ko con hang se bao Het hang / neu dat qua so luong trong kho se order lai )
            else if (product.getQuantity() == 0) {
                System.out.println("San pham da het hang, vui long order sản phẩm khác");
                return;
            } else if (product.getQuantity() < numberOfProducts) {
                System.out.println("Ban da order qua so luong san pham trong kho , vui long order lai:");
                return;
            }


            //  Nhap Status và kiểm tra status //
            boolean isValidStatus = false;
            STATUS status = null;
            while (!isValidStatus) {
                System.out.print("Nhap tinh trang san pham: (NEW/OLD)");
                String typeString = scanner.nextLine().toUpperCase();
                try {
                    status = STATUS.valueOf(typeString);
                    isValidStatus = true;
                    boolean foundMatchingStatus = false;
                    for (Product product1 : productMap.values()) {
                        if (product1.getStatus() == status) {
                            foundMatchingStatus = true;
                            break;
                        }
                    }
                    if (!foundMatchingStatus) {
                        System.out.println("Không có sản phẩm có tình trạng này. Vui lòng nhập lại.");
                        isValidStatus = false;
                    }
                } catch (Exception e) {
                    System.out.println("Tinh trang nhap vao khong hop le. Vui long nhap lai.");
                }
            }


            // Nhập mã giảm giá (nếu có)
            System.out.print("Bạn có mã giảm giá không? (yes/no): ");
            String discountChoice = utils.inputString(scanner);

            String discountCode = null;
            double discountPercentage = 0;

            if ("yes".equalsIgnoreCase(discountChoice)) {
                System.out.print("Nhập mã giảm giá: ");
                discountCode = utils.inputString(scanner);

                // Kiểm tra mã giảm giá có hợp lệ và áp dụng giảm giá
                if (isValidDiscountCode(discountCode)) {
                    discountPercentage = getDiscountPercentage(discountCode);
                } else {
                    System.out.println("Mã giảm giá không hợp lệ. Đã bỏ qua.");
                }
            }

            // Ngày dặt hàng (ngày hiện tại)
            LocalDate orderDate = LocalDate.now();


            //  Tính tiền và in ra đơn hàng
            double total = numberOfProducts * product.getPrice() * (1 - discountPercentage);


            Orders order = new Orders(productId, orderDate, status, numberOfProducts, total, user);
            orders.add(order);
            System.out.println("Thông tin đơn hàng:");
            System.out.print("ID sản phẩm: " + product.getId());
            System.out.print("|| Tên: " + product.getName());
            System.out.print("|| Tình trạng: " + product.getStatus());
            System.out.print("|| Giá: " + product.getPrice());
            System.out.print("|| Mô tả: " + product.getDescription());
            System.out.print("|| Số lượng sản phẩm order: " + numberOfProducts);
            System.out.println("|| Tổng tiền: $" + total);
            System.out.println("Đơn hàng của bạn đã được thêm vào giỏ hàng.");

        } else {
            System.out.println("Không tìm thấy sản phẩm với ID đã nhập.");
        }
    }

    // Hiển thị đơn hàng cá nhân
    public void viewUserOrders(ArrayList<Orders> orders, Product product) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG CỦA BẠN=======");
        for (Orders order : orders) {
            System.out.print("ID Sản phẩm: " + product.getId());
            System.out.print("|| Tên sản phẩm: " + product.getName());
            System.out.print("|| Tình trạng: " + product.getStatus());
            System.out.print("|| Giá: " + product.getPrice());
            System.out.print("|| Mô tả: " + product.getDescription());
            System.out.print("|| Số lượng sản phẩm order: " + order.getNumberOfProducts());
            System.out.println("|| Tổng tiền: $" + order.getTotal());
        }
    }






        // Xem tất cả đơn hàng đã được order
    public void viewOrders (ArrayList <Orders> orders) {
        for (Orders order : orders) {
            System.out.println(order);
        }
    }

    // Logic để kiểm tra xem mã giảm giá có hợp lệ hay không
    private boolean isValidDiscountCode (String code){
        List<String> validDiscountCodes = Arrays.asList("DISCOUNT10", "DISCOUNT20", "DISCOUNT30");
        return validDiscountCodes.contains(code.toUpperCase());
    }

    // Logic để lấy tỷ lệ giảm giá tương ứng với mã giảm giá
    private double getDiscountPercentage (String discountCode){
        switch (discountCode.toUpperCase()) {
            case "DISCOUNT10":
                return 0.1;
            case "DISCOUNT20":
                return 0.2;
            case "DISCOUNT30":
                return 0.3;
            default:
                return 0; // Mặc định = 0 nếu không có mã
        }
    }
}