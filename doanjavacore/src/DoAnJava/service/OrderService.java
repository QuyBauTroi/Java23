package DoAnJava.service;


import DoAnJava.Utils.Utils;
import DoAnJava.entities.*;

import java.time.LocalDate;
import java.util.*;

public class OrderService {
    Utils utils = new Utils();
    ProductService productService = new ProductService();
    // Phương thức order sản phẩm //
    public void order(Scanner scanner , User user, ArrayList<Orders> orders, Map<Integer, Product> productMap) {
        // Hiển thị danh sách sản phẩm
        productService.viewProduct(productMap);
        String choice;
        do {
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
                System.out.print("Bạn có mã giảm giá không? (y/n): ");
                String discountChoice = utils.inputString(scanner);

                String discountCode;
                double discountPercentage = 0;

                if ("y".equalsIgnoreCase(discountChoice)) {
                    boolean isValidDiscount = false;

                    do {
                        try {
                            System.out.print("Nhập mã giảm giá: ");
                            discountCode = utils.inputString(scanner);

                            // Kiểm tra mã giảm giá có hợp lệ và áp dụng giảm giá
                            if (isValidDiscountCode(discountCode)) {
                                discountPercentage = getDiscountPercentage(discountCode);
                                isValidDiscount = true;
                            } else {
                                System.out.println("Mã giảm giá không hợp lệ. Vui lòng nhập lại.");
                            }
                        } catch (Exception e) {
                            System.out.println("Lỗi khi nhập mã giảm giá. Vui lòng nhập lại.");
                            scanner.nextLine();
                        }
                    } while (!isValidDiscount);
                }


                // Ngày dặt hàng (ngày hiện tại)
                LocalDate orderDate = LocalDate.now();


                //  Tính tiền và in ra đơn hàng
                double total = numberOfProducts * product.getPrice() * (1 - discountPercentage);


                Orders order = new Orders(productId, orderDate, status, numberOfProducts, total,Approve.PENDING_APPROVAL, user);
                orders.add(order);
                viewUserOrders(orders,product);
                System.out.println("Đơn hàng của bạn đã được thêm vào giỏ hàng.");
            } else {
                System.out.println("Không tìm thấy sản phẩm với ID đã nhập.");
            }
            System.out.print("Ban co muon tiep tuc order san pham khong ? (Y/N)");
             choice= scanner.nextLine();
        }while (choice.equalsIgnoreCase("y"));

    }




    // Phương thức tin ra đơn hàng của bản thân
    public void viewUserOrders(ArrayList<Orders> orders, Product product) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG CỦA BẠN=======");
        if (orders.isEmpty()) {
            System.out.println("Bạn chưa có đơn hàng nào.");
        } else {
            for (Orders order : orders) {
                System.out.print("ID Sản phẩm: " + product.getId());
                System.out.print("|| Tên sản phẩm: " + product.getName());
                System.out.print("|| Tình trạng: " + product.getStatus());
                System.out.print("|| Giá: " + product.getPrice());
                System.out.print("|| Mô tả: " + product.getDescription());
                System.out.print("|| Số lượng sản phẩm order: " + order.getNumberOfProducts());
                System.out.print("|| Trang thai: " + order.getApproval());
                System.out.println("|| Tổng tiền: $" + order.getTotal());
            }
        }
    }





    // Phương thức xác nhận đơn hàng
    public void confirmOrder(Scanner scanner, ArrayList<Orders> orders,Product product) {
        // Hiển thị danh sách đơn hàng chờ xác nhận
        viewPendingConfirmationOrders(orders,product);
        System.out.print("Nhập ID đơn hàng bạn muốn xác nhận: ");
        int orderId = utils.inputInt(scanner);
        Orders orderToConfirm = findOrderById(orderId, orders);
        if (orderToConfirm != null && orderToConfirm.getApproval() == Approve.PENDING_APPROVAL) {
            // Cập nhật trạng thái đơn hàng thành APPROVED
            orderToConfirm.setApproval(Approve.APPROVED);
            System.out.println("Đơn hàng đã được xác nhận!");
        } else {
            System.out.println("Không tìm thấy đơn hàng chờ xác nhận hoặc đơn hàng đã được xác nhận trước đó.");
        }
    }




    // Phương thức hiển thị đơn hàng chờ xác nhận
    public void viewPendingConfirmationOrders(ArrayList<Orders> orders,Product product) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG CHỜ XÁC NHẬN=======");
        for (Orders order : orders) {
            if (order.getApproval() == Approve.PENDING_APPROVAL && order.getApproval() == Approve.PENDING_APPROVAL) {
                System.out.println("Thông tin đơn hàng:");
                System.out.print("ID: " + order.getId());
                System.out.print("|| Tên sản phẩm: " + product.getName());
                System.out.print("|| Tình trạng: " + product.getStatus());
                System.out.print("|| Giá: " + product.getPrice());
                System.out.print("|| Mô tả: " + product.getDescription());
                System.out.print("|| Số lượng sản phẩm order: " + order.getNumberOfProducts());
                System.out.print("|| Trang thai: " + order.getApproval());
                System.out.println("Tổng tiền: $" + order.getTotal());
            }
        }
    }




    // Phương thức in ra thông tin đơn hàng đã được duyệt đơn
    public void viewApprovedOrders(ArrayList<Orders> orders,Product product) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG ĐÃ ĐƯỢC XÁC NHẬN=======");
        for (Orders order : orders) {
            if (order.getApproval() == Approve.APPROVED) {
                System.out.println("Thông tin đơn hàng:");
                System.out.println("ID: " + order.getId());
                System.out.print("|| Tên sản phẩm: " + product.getName());
                System.out.print("|| Tình trạng: " + product.getStatus());
                System.out.print("|| Giá: " + product.getPrice());
                System.out.print("|| Mô tả: " + product.getDescription());
                System.out.print("|| Số lượng sản phẩm order: " + order.getNumberOfProducts());
                System.out.print("|| Trang thai: " + order.getApproval());
                System.out.println("Tổng tiền: $" + order.getTotal());
            }
        }
    }





    // Phương thức tìm kiếm đơn hàng theo ID
    public Orders findOrderById(int orderId, ArrayList<Orders> orders) {
        for (Orders order : orders) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        return null;
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
        return switch (discountCode.toUpperCase()) {
            case "DISCOUNT10" -> 0.1;
            case "DISCOUNT20" -> 0.2;
            case "DISCOUNT30" -> 0.3;
            default -> 0; // Mặc định = 0 nếu không có mã
        };
    }
}