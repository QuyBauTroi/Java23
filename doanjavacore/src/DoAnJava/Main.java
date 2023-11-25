package DoAnJava;

import DoAnJava.View.Menu;
import DoAnJava.entities.Orders;
import DoAnJava.entities.Product;
import DoAnJava.entities.STATUS;
import DoAnJava.entities.User;
import DoAnJava.service.UserService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user=new User("admin","admin@gmail.com","admin123");
        User user1=new User("quy1","quy1@gmail.com","Quy1234","quy1","012345678","Hà Nội",2);
        User user2=new User("quy2","quy2@gmail.com","Quy1234","quy2","012345678","Hà Nội",2);
        User user3=new User("quy3","quy3@gmail.com","Quy1234","quy3","012345678","Hà Nội",2);
        User user4=new User("staff1","staff1@gmail.com","Quy1234","staff1","012345678","Hà Nội",1);
        User user5=new User("staff2","staff2@gmail.com","Quy1234","staff2","012345678","Hà Nội",1);
        ArrayList<User> users=new ArrayList<>(Arrays.asList(user,user1,user2,user3,user4,user5));


        Map<Integer, Product> productMap = new HashMap<>();
        Product product = new Product("iphone X",5000000,"Màn hình Super Retina OLED 5.8 inch, vi xử lý A11 Bionic , camera kép 12MP ",5, STATUS.OLD);
        Product product1 = new Product("iphone Xs",6000000,"Màn hình Super Retina OLED 5.8 inch, vi xử lý A12 Bionic , camera kép 12MP ",5, STATUS.OLD);
        Product product2 = new Product("iphone Xs Max",7000000,"Màn hình Super Retina OLED 6.5 inch, vi xử lý A12 Bionic , camera kép 12MP ",5, STATUS.OLD);
        Product product3 = new Product("iphone 11",8000000,"Màn hình Super Retina OLED 5.8 inch, vi xử lý A12 Bionic , camera kép 12MP ",5, STATUS.OLD);
        Product product4 = new Product("iphone 11 Pro",9000000,"Màn hình Super Retina OLED 5.8 inch, vi xử lý A13 Bionic , camera kép 12MP ",5, STATUS.OLD);
        Product product5 = new Product("iphone 11 Pro Max",10000000,"Màn hình Super Retina OLED 6.5 inch, vi xử lý A13 Bionic , camera kép 12MP ",5, STATUS.OLD);
        Product product6 = new Product("iphone 12 Pro",14000000,"Màn hình Super Retina OLED 6.1 inch, vi xử lý A14 Bionic , camera kép 12MP ",5, STATUS.OLD);
        Product product7 = new Product("iphone 12 Pro Max",16000000,"Màn hình Super Retina OLED 6,7 inch, vi xử lý A14 Bionic , camera kép 12MP ",5, STATUS.NEW);
        Product product8 = new Product("iphone 13 Pro Max",20000000,"Màn hình Super Retina OLED 6.7 inch, vi xử lý A15 Bionic , camera kép 12MP ",5, STATUS.NEW);
        Product product9 = new Product("iphone 14 Pro Max",22000000,"Màn hình Super Retina OLED 6.7 inch, vi xử lý A16 Bionic , camera kép 48MP ",5, STATUS.NEW);
        productMap.put(product.getId(),product);
        productMap.put(product1.getId(),product1);
        productMap.put(product2.getId(),product2);
        productMap.put(product3.getId(),product3);
        productMap.put(product4.getId(),product4);
        productMap.put(product5.getId(),product5);
        productMap.put(product6.getId(),product6);
        productMap.put(product7.getId(),product7);
        productMap.put(product8.getId(),product8);
        productMap.put(product9.getId(),product9);




        ArrayList<Orders> orders = new ArrayList<>();
        Orders order = new Orders();
        orders.add(order);

        UserService userService=new UserService();
        Menu menu=new Menu();
        menu.optionMenu(scanner,users,userService,productMap,orders);
    }
}
