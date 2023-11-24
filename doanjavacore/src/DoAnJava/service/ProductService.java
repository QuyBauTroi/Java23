package DoAnJava.service;

import DoAnJava.Utils.Utils;
import DoAnJava.entities.Product;
import DoAnJava.entities.STATUS;

import java.util.Map;
import java.util.Scanner;

public class ProductService {
    Utils utils = new Utils();
    //  - Thêm mới sản phẩm //
    public void addProduct(Scanner scanner, Map<Integer, Product> productMap ){
        System.out.println("Them san pham");
        System.out.print("Nhap so luong san pham ban muon them : ");
        int n = utils.inputInt(scanner);
        for (int i = 0; i < n; i++) {
            System.out.print("Nhap ten san pham thu " +(i+1)+":");
            String name = utils.inputString(scanner);
            boolean isValidStatus = false;
            STATUS status = null;
            while (!isValidStatus) {
                System.out.print("Nhap tinh trang san pham: (NEW/OLD)");
                String typeString = scanner.nextLine().toUpperCase();
                try {
                    status = STATUS.valueOf(typeString);
                    isValidStatus = true;
                } catch (Exception e) {
                    System.out.println("Tinh trang nhap vao khong hop le. Vui long nhap lai.");
                }
            }
            System.out.print("Nhap gia tien :");
            double price = utils.inputDouble(scanner);
            System.out.print("Su mieu ta :");
            String description = utils.inputString(scanner);
            System.out.print("Nhap so luong san pham thu" + (i+1) +":");
            int quantity = utils.inputInt(scanner);
            Product product = new Product(name,price,description,quantity,status);
            productMap.put(product.getId(),product);
        }
    }



    //  - Hiển thị tất cả sản phẩm //
    public void viewProduct(Map<Integer,Product> productMap){
        for (Product product : productMap.values()) {
            printProductInfo(product);
        }
    }


    // Hàm in thông tin sản phẩm
    private static void printProductInfo(Product product) {
        System.out.print("ID: " + product.getId());
        System.out.print(" || Tên: " + product.getName());
        System.out.print(" || Giá: " + product.getPrice());
        System.out.print(" || Mô tả: " + product.getDescription());
        System.out.print(" || Số lượng: " + product.getQuantity());
        System.out.println(" || Tình trạng: " + product.getStatus());

    }




    //  - Xoá sản phấm //
    public void deleteProduct(Scanner scanner,Map<Integer,Product> productMap){
        System.out.println("Xóa sản phẩm");
        System.out.print("Nhập ID sản phẩm cần xóa: ");
        int productId = utils.inputInt(scanner);
        Product product = productMap.get(productId);
        if (product != null) {
            System.out.println("ID:" + product.getId() + " || Tên sp:" + product.getName() + " || Tình trạng:" + product.getStatus() + " || Giá:" + product.getPrice() + " || Mô tả:" + product.getDescription() + " || Số lượng:" + product.getQuantity());
            productMap.remove(productId);
            System.out.println("Sản phẩm đã được xóa khỏi danh sách.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID đã nhập.");
        }
    }




    // - Cập nhập , Thay đổi thông tin của sản phẩm
    public void updateProductByFindId(Scanner scanner,Map<Integer,Product> productMap){
        System.out.println("Cập nhật thông tin sản phẩm");
        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
        int productId = utils.inputInt(scanner);
        Product product = productMap.get(productId);
        if (product != null) {
            do {
                System.out.println("chọn thông tin bạn muốn thay đổi");
                System.out.println("1. Thay đổi tên sản phẩm");
                System.out.println("2. Thay đổi trạng thái sản phẩm");
                System.out.println("3. Thay đổi giá của sản phẩm");
                System.out.println("4. Thay đổi phần mô tả về sản phẩm");
                System.out.println("5. Thay đổi số lượng của sản phẩm");
                System.out.println("0. Thoát");
                int choose = utils.inputInt(scanner);
                switch (choose) {
                    case 1:
                        System.out.print("Nhập tên mới: ");
                        String newName = utils.inputString(scanner);
                        product.setName(newName);
                        break;
                    case 2:
                        System.out.print("Nhập tình trạng mới (NEW/OLD): ");
                        STATUS newStatus = null;
                        boolean isValidStatus = false;
                        while (!isValidStatus) {
                            String typeString = scanner.nextLine().toUpperCase();
                            try {
                                newStatus = STATUS.valueOf(typeString);
                                isValidStatus = true;
                            } catch (Exception e) {
                                System.out.println("Tình trạng nhập vào không hợp lệ. Vui lòng nhập lại.");
                            }
                        }
                        product.setStatus(newStatus);
                        break;
                    case 3:
                        System.out.print("Nhập giá mới: ");
                        double newPrice = utils.inputDouble(scanner);
                        product.setPrice(newPrice);
                        break;
                    case 4:
                        System.out.print("Nhập mô tả mới: ");
                        String newDescription = utils.inputString(scanner);
                        product.setDescription(newDescription);
                        break;
                    case 5:
                        System.out.print("Nhập số lượng mới: ");
                        int newQuantity = utils.inputInt(scanner);
                        product.setQuantity(newQuantity);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại:");
                }

                System.out.println("Thông tin sản phẩm sau khi cập nhật:");
                System.out.print("ID: " + product.getId());
                System.out.print(" || Tên: " + product.getName());
                System.out.print(" || Tình trạng: " + product.getStatus());
                System.out.print(" || Giá: " + product.getPrice());
                System.out.print(" || Mô tả: " + product.getDescription());
                System.out.println(" || Số lượng: " + product.getQuantity());
            }while (true);
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID đã nhập.");
        }
    }
}
