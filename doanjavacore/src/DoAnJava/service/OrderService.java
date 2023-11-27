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
                            System.out.println("Khong co san pham nao co Status nay , vui long nhap lai");
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
                double total = numberOfProducts * product.getPrice()*(1-discountPercentage);


                Orders order = new Orders(productId, orderDate, status, numberOfProducts, total, OrderStatus.PENDING_APPROVAL, user);
                orders.add(order);

                System.out.println("Don hang cua ban da duoc them vao gio hang.");
            } else {
                System.out.println("Khong tim thay ID da nhap.");
            }
            System.out.print("Ban co muon tiep tuc order san pham khong ? (Y/N)");
             choice= scanner.nextLine();
        }while (choice.equalsIgnoreCase("y"));
        viewUserOrders(orders,user,productMap);
    }



    public void viewAllOrderUser(ArrayList<Orders> orders, User user, Map<Integer, Product> productMap){
        System.out.println("=======DANH SACH TAT CA DON HANG CUA BAN =======");
        boolean hasOrders = false;  // Kiểm tra xem người dùng có đơn hàng không
        for (Orders order : orders) {
            if (Objects.equals(order.getUser(), user)) {
                    Product product = productMap.get(order.getProductId());
                    if (product != null) {
                        System.out.print("ID : " + order.getId());
                        System.out.print(" || Ten SP: " + product.getName());
                        System.out.print(" || STATUS: " + product.getStatus());
                        System.out.print(" || Gia: " + product.getPrice());
                        System.out.print(" || Mo ta: " + product.getDescription());
                        System.out.print(" || So luong sp order: " + order.getNumberOfProducts());
                        System.out.print(" || Trang thai don hang: " + order.getApproval());
                        System.out.println(" || Thanh tien: " +order.getTotal());

                        hasOrders = true;// Đã tìm thấy ít nhất một đơn hàng
                    }
            }
    }
    }

    // Phương thức in ra đơn hàng của người dùng
    public void viewUserOrders(ArrayList<Orders> orders, User user, Map<Integer, Product> productMap) {
        System.out.println("=======DANH SACH DON HANG CUA BAN DA ORDER=======");
        boolean hasOrders = false;  // Kiểm tra xem người dùng có đơn hàng không
        double totalOrderAmount = 0;  // Tham số để lưu tổng tiền của đơn hàng
        for (Orders order : orders) {
            if (Objects.equals(order.getUser(), user)) {
                if (order.getApproval() == OrderStatus.PENDING_APPROVAL || order.getApproval() == OrderStatus.APPROVED){
                Product product = productMap.get(order.getProductId());
                if (product != null) {
                    System.out.print("ID : " + order.getId());
                    System.out.print(" || Ten SP: " + product.getName());
                    System.out.print(" || STATUS: " + product.getStatus());
                    System.out.print(" || Gia: " + product.getPrice());
                    System.out.print(" || Mo ta: " + product.getDescription());
                    System.out.print(" || So luong sp order: " + order.getNumberOfProducts());
                    System.out.print(" || Trang thai don hang: " + order.getApproval());
                    System.out.println(" || Thanh tien: " +order.getTotal());
                    totalOrderAmount += order.getTotal();// Cộng dồn số tiền của từng đơn hàng
                    hasOrders = true;// Đã tìm thấy ít nhất một đơn hàng
                }
                }
            }
        }
        System.out.println("Tong tien tat ca cac don hang: $" + totalOrderAmount);  // In tổng tiền của đơn hàng
        if (!hasOrders) {
            System.out.println("Bạn hiện chưa có đơn hàng nào.");  // Nếu không có đơn hàng, thông báo cho người dùng
        }
    }

    public void viewOrdered(ArrayList<Orders> orders, User user, Map<Integer, Product> productMap) {
        System.out.println("=======DANH SACH DON HANG CUA BAN DA ORDER=======");
        boolean hasOrders = false;  // Kiểm tra xem người dùng có đơn hàng không
        for (Orders order : orders) {
            if (Objects.equals(order.getUser(), user)) {
                if (order.getApproval() == OrderStatus.PENDING_APPROVAL || order.getApproval() == OrderStatus.APPROVED){
                    Product product = productMap.get(order.getProductId());
                    if (product != null) {
                        System.out.print("ID : " + order.getId());
                        System.out.print(" || Ten SP: " + product.getName());
                        System.out.print(" || STATUS: " + product.getStatus());
                        System.out.print(" || Gia: " + product.getPrice());
                        System.out.print(" || Mo ta: " + product.getDescription());
                        System.out.print(" || So luong sp order: " + order.getNumberOfProducts());
                        System.out.print(" || Trang thai don hang: " + order.getApproval());
                        System.out.println(" || Thanh tien: " +order.getTotal());
                        hasOrders = true;// Đã tìm thấy ít nhất một đơn hàng
                    }
                }
            }
        }
        if (!hasOrders) {
            System.out.println("Bạn hiện chưa có đơn hàng nào.");  // Nếu không có đơn hàng, thông báo cho người dùng
        }
    }

    public void viewCancelOrdersUser(ArrayList<Orders> orders, User user, Map<Integer, Product> productMap) {
        System.out.println("=======DANH SACH DON HANG DA HUY CUA " + user.getName() + "=======");
        boolean hasCancel = false;  // Kiểm tra xem người dùng có đơn hàng không
        for (Orders order : orders) {
            if (Objects.equals(order.getUser(), user)) {
                if (order.getApproval() == OrderStatus.CANCELED){
                    Product product = productMap.get(order.getProductId());
                    if (product != null) {
                        System.out.print("ID : " + order.getId());
                        System.out.print(" || Ten SP: " + product.getName());
                        System.out.print(" || STATUS: " + product.getStatus());
                        System.out.print(" || Gia: " + product.getPrice());
                        System.out.print(" || Mo ta: " + product.getDescription());
                        System.out.print(" || So luong sp order: " + order.getNumberOfProducts());
                        System.out.print(" || Trang thai don hang: " + order.getApproval());
                        System.out.println(" || Thanh tien: " +order.getTotal());
                        hasCancel = true;// Đã tìm thấy ít nhất một đơn hàng
                    }
                }
            }
        }
        if (!hasCancel) {
            System.out.println("Bạn hiện chưa có đơn hàng nào.");  // Nếu không có đơn hàng, thông báo cho người dùng
        }
    }

    public void viewSuccessfulOrdersUser(ArrayList<Orders> orders, User user, Map<Integer, Product> productMap) {
        System.out.println("=======DANH SACH DON HANG GIAO THANH CONG CUA " + user.getName() + "=======");
        boolean hasSuccess = false;  // Kiểm tra xem người dùng có đơn hàng không
        for (Orders order : orders) {
            if (Objects.equals(order.getUser(), user)) {
                if (order.getApproval() == OrderStatus.SUCCESSFUL_DELIVERY){
                    Product product = productMap.get(order.getProductId());
                    if (product != null) {
                        System.out.print("ID : " + order.getId());
                        System.out.print(" || Ten SP: " + product.getName());
                        System.out.print(" || STATUS: " + product.getStatus());
                        System.out.print(" || Gia: " + product.getPrice());
                        System.out.print(" || Mo ta: " + product.getDescription());
                        System.out.print(" || So luong sp order: " + order.getNumberOfProducts());
                        System.out.print(" || Trang thai don hang: " + order.getApproval());
                        System.out.println(" || Thanh tien: " +order.getTotal());
                        hasSuccess = true;// Đã tìm thấy ít nhất một đơn hàng
                    }
                }
            }
        }
        if (!hasSuccess) {
            System.out.println("Bạn hiện chưa có đơn hàng nào.");  // Nếu không có đơn hàng, thông báo cho người dùng
        }
    }




    // Phương thức huy đơn hàng của người dùng
    public void canceledOrder(Scanner scanner, ArrayList<Orders> orders, Map<Integer, Product> productMap, User user) {
        // Hiển thị danh sách đơn hàng của bản thân
        viewOrdered(orders, user, productMap);
        String choice;
        do {
            System.out.print("Nhập ID đơn hàng bạn muốn hủy: ");
            int orderId = utils.inputInt(scanner);
            Orders orderToCancel = findOrderById(orderId, orders);
            if (orderToCancel != null) {
                if (orderToCancel.getApproval() == OrderStatus.DELIVERING || orderToCancel.getApproval() == OrderStatus.SUCCESSFUL_DELIVERY){
                    System.out.println("Don hang dang duoc giao , khong the huy don.");
                } else if (orderToCancel.getApproval() == OrderStatus.PENDING_APPROVAL || orderToCancel.getApproval() == OrderStatus.APPROVED ) {
                    // Hỏi người dùng có muốn hủy không
                    System.out.println("Bạn có muốn hủy đơn hàng này không ?");
                    System.out.println("1- Yes              2- No");
                    System.out.print("Enter your choice: ");
                    int userChoice = utils.inputInt(scanner);
                    // Xử lý lựa chọn của người dùng
                    switch (userChoice) {
                        case 1 -> {orderToCancel.setApproval(OrderStatus.CANCELED);System.out.println("Đơn hàng đã hủy thành công!");} // Hủy đơn
                        case 2 -> {return;}
                        default -> System.out.println("Lựa chọn không hợp lệ. Đơn hàng không được hủy.");
                    }
                }
            } else {System.out.println("Không tìm thấy đơn hàng.");}
            System.out.print("Bạn có muốn tiếp tục hủy đơn không ? (Y/N)");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("y"));
    }





    // Phương thức xác nhận đơn hàng
    public void confirmOrder(Scanner scanner, ArrayList<Orders> orders,Map<Integer, Product> productMap) {
        // Hiển thị danh sách đơn hàng chờ xác nhận
        viewPendingConfirmationOrders(orders,productMap);
        String choice;
        do{
        System.out.print("Nhập ID đơn hàng bạn muốn xác nhận: ");
        int orderId = utils.inputInt(scanner);
        Orders orderToConfirm = findOrderById(orderId, orders);
        if (orderToConfirm != null && orderToConfirm.getApproval() == OrderStatus.PENDING_APPROVAL) {
            // Hỏi người dùng có muốn xác nhận đơn hàng hay hủy không
            System.out.println("1- Xác nhận đơn hàng");
            System.out.println("2- Hủy đơn hàng");
            System.out.print("Enter your choice: ");
            int userChoice = utils.inputInt(scanner);

            // Xử lý lựa chọn của người dùng
            switch (userChoice) {
                case 1 -> {orderToConfirm.setApproval(OrderStatus.APPROVED);System.out.println("Đơn hàng đã được xác nhận!");}// Xác nhận đơn hàng
                case 2 -> {orderToConfirm.setApproval(OrderStatus.CANCELED);System.out.println("Đơn hàng đã huỷ!");}// Huỷ đơn hàng
                default -> System.out.println("Lựa chọn không hợp lệ. Đơn hàng không được xác nhận.");
            }
        } else {
            System.out.println("Không tìm thấy đơn hàng chờ xác nhận hoặc đơn hàng đã được xác nhận trước đó.");
        }
            System.out.println("Ban co muon tiep tuc duyet don khong ? (Y/N)");
        choice = scanner.nextLine();
        }while (choice.equalsIgnoreCase("y"));
    }






    // Phương thức hiển thị đơn hàng chờ xác nhận
    public void viewPendingConfirmationOrders(ArrayList<Orders> orders, Map<Integer, Product> productMap) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG CHO XAC NHAN=======");
        boolean hasPendingConfirmationOrders = false;

        for (Orders order : orders) {
            if (order.getApproval() == OrderStatus.PENDING_APPROVAL) {
                System.out.println("Thông tin đơn hàng:");
                Product product = productMap.get(order.getProductId());
                if (product != null) {
                    System.out.print("ID : " + order.getId());
                    System.out.print(" || Tên sản phẩm: " + product.getName());
                    System.out.print(" || Tình trạng: " + product.getStatus());
                    System.out.print(" || Giá: " + product.getPrice());
                    System.out.print(" || Mô tả: " + product.getDescription());
                    System.out.print(" || Số lượng sản phẩm order: " + order.getNumberOfProducts());
                    System.out.print(" || Trạng thái: " + order.getApproval());
                    System.out.println(" || Tổng tiền: $" + order.getTotal());

                    // Đã tìm thấy ít nhất một đơn hàng cho xac nhan
                    hasPendingConfirmationOrders = true;
                }
            }
        }

        // Nếu không có đơn hàng đã hủy, thông báo cho người dùng
        if (!hasPendingConfirmationOrders) {
            System.out.println("Không có đơn hàng đã cho xac nhan.");
        }
    }



    // Phương thức in ra thông tin đơn hàng đã được duyệt đơn
    public void viewApprovedOrders(ArrayList<Orders> orders,Map<Integer, Product> productMap) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG ĐÃ ĐƯỢC XÁC NHẬN=======");
        for (Orders order : orders) {
            if (order.getApproval() == OrderStatus.APPROVED) {
                Product product = productMap.get(order.getProductId());
                if (product != null) {
                    System.out.print("ID : " + order.getId());
                    System.out.print(" || Tên sản phẩm: " + product.getName());
                    System.out.print(" || Tình trạng: " + product.getStatus());
                    System.out.print(" || Giá: " + product.getPrice());
                    System.out.print(" || Mô tả: " + product.getDescription());
                    System.out.print(" || Số lượng sản phẩm order: " + order.getNumberOfProducts());
                    System.out.print(" || Trạng thái: " + order.getApproval());
                    System.out.println(" || Tổng tiền: $" + order.getTotal());
                }
            }
        }
    }
    // Phương thức in ra thông tin đơn hàng đã được duyệt đơn
    public void viewDeliverOrders(ArrayList<Orders> orders,Map<Integer, Product> productMap) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG ĐANG GIAO=======");
        for (Orders order : orders) {
            if (order.getApproval() == OrderStatus.APPROVED) {
                Product product = productMap.get(order.getProductId());
                if (product != null) {
                    System.out.print("ID : " + order.getId());
                    System.out.print(" || Tên sản phẩm: " + product.getName());
                    System.out.print(" || Tình trạng: " + product.getStatus());
                    System.out.print(" || Giá: " + product.getPrice());
                    System.out.print(" || Mô tả: " + product.getDescription());
                    System.out.print(" || Số lượng sản phẩm order: " + order.getNumberOfProducts());
                    System.out.print(" || Trạng thái: " + order.getApproval());
                    System.out.println(" || Tổng tiền: $" + order.getTotal());
                }
            }
        }
    }




    // Phương thức in ra thông tin đơn hàng đã hủy
    public void viewCanceledOrders(ArrayList<Orders> orders, Map<Integer, Product> productMap) {
        System.out.println("=======DANH SÁCH ĐƠN HÀNG ĐÃ HỦY=======");
        boolean hasCancelledOrders = false;

        for (Orders order : orders) {
            if (order.getApproval() == OrderStatus.CANCELED) {
                Product product = productMap.get(order.getProductId());
                if (product != null) {
                    System.out.print("ID : " + order.getId());
                    System.out.print(" || Tên sản phẩm: " + product.getName());
                    System.out.print(" || Tình trạng: " + product.getStatus());
                    System.out.print(" || Giá: " + product.getPrice());
                    System.out.print(" || Mô tả: " + product.getDescription());
                    System.out.print(" || Số lượng sản phẩm order: " + order.getNumberOfProducts());
                    System.out.print(" || Trạng thái: " + order.getApproval());
                    System.out.println(" || Tổng tiền: $" + order.getTotal());

                    // Đã tìm thấy ít nhất một đơn hàng đã hủy
                    hasCancelledOrders = true;
                }
            }
        }

        // Nếu không có đơn hàng đã hủy, thông báo cho người dùng
        if (!hasCancelledOrders) {
            System.out.println("Không có đơn hàng đã hủy.");
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





    // Phuong thuc cap nhat trang thai don hang ( dang giao hang va giao hang thanh cong )
    public void updateOrderStatus(ArrayList<Orders> orders, Map<Integer, Product> productMap,Scanner scanner){
        viewApprovedOrders(orders,productMap);
        System.out.println("Cap nhat trang thai don hang");
        do {
            System.out.println("-------------------------------------------------------------------------------------------------------------");
            System.out.println("1. DON HANG DANG DUOC GIAO                      2. DON HANG DA GIAO THANH CONG                       3. THOAT");
            System.out.print("Enter your choice");
            int choice = utils.inputInt(scanner);
            switch (choice){
                case 1:
                    delivering(scanner, orders, productMap);
                    break;
                case 2:
                    successfulDeli(scanner, orders, productMap);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Lua chon khong hop le , vui long chon lai");
                    break;
            }
        }while (true);
    }
    public void delivering(Scanner scanner, ArrayList<Orders> orders, Map<Integer, Product> productMap) {
        // Hiển thị danh sách đơn hàng của bản thân
        viewApprovedOrders(orders, productMap);
        String choice;
        do{
            System.out.print("Nhập ID đơn hàng bạn muốn cap nhat trang thai don hang: ");
            int orderId = utils.inputInt(scanner);
            Orders orderToDelivering = findOrderById(orderId, orders);
            if (orderToDelivering != null && orderToDelivering.getApproval() == OrderStatus.APPROVED) {
                // Hỏi người dùng có muốn xác nhận đơn hàng hay hủy không
                System.out.println("Ban co muon cap nhat trang thai 'DANG GIAO HANG' khong ?");
                System.out.println("1- YES              2- NO");
                System.out.print("Enter your choice: ");
                int userChoice = utils.inputInt(scanner);

                // Xử lý lựa chọn của người dùng
                switch (userChoice) {
                    case 1 -> {orderToDelivering.setApproval(OrderStatus.DELIVERING);System.out.println("Đơn hàng đã được cap nhat thanh 'DANG GIAO' !");}
                    case 2 -> {return;}
                    default -> System.out.println("Lựa chọn không hợp lệ. Đơn hàng không được xác nhận.");
                }
            } else {
                System.out.println("Không tìm thấy đơn hàng xác nhận.");
            }
            System.out.println("Ban co muon tiep tuc cap nhat don khong ? (Y/N)");
            choice = scanner.nextLine();
        }while (choice.equalsIgnoreCase("y"));
    }
    public void successfulDeli(Scanner scanner, ArrayList<Orders> orders, Map<Integer, Product> productMap) {
        // Hiển thị danh sách đơn hàng của bản thân
        viewDeliverOrders(orders,productMap);
        String choice;
        do{
            System.out.print("Nhập ID đơn hàng bạn muốn cap nhat trang thai don hang: ");
            int orderId = utils.inputInt(scanner);
            Orders orderToDelivering = findOrderById(orderId, orders);
            if (orderToDelivering != null && orderToDelivering.getApproval() == OrderStatus.DELIVERING) {
                // Hỏi người dùng có muốn xác nhận đơn hàng hay hủy không
                System.out.println("Ban co muon cap nhat trang thai 'DANG GIAO HANG THANH CONG' khong ?");
                System.out.println("1- YES              2- NO");
                System.out.print("Enter your choice: ");
                int userChoice = utils.inputInt(scanner);

                // Xử lý lựa chọn của người dùng
                switch (userChoice) {
                    case 1 -> {orderToDelivering.setApproval(OrderStatus.SUCCESSFUL_DELIVERY);System.out.println("Đơn hàng đã được cap nhat thanh 'DANG GIAO THANH CONG!!' !");}
                    case 2 -> {return;}
                    default -> System.out.println("Lựa chọn không hợp lệ. Đơn hàng không được xác nhận.");
                }
            } else {
                System.out.println("Không tìm thấy đơn hàng DANG GIAO");
            }
            System.out.println("Ban co muon tiep tuc cap nhat don khong ? (Y/N)");
            choice = scanner.nextLine();
        }while (choice.equalsIgnoreCase("y"));
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