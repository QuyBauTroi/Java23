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
        Product product = new Product("iphone X",5000000,"dc",5, STATUS.OLD);
        productMap.put(product.getId(),product);




        ArrayList<Orders> orders = new ArrayList<>();
        Orders order = new Orders();
        orders.add(order);

        UserService userService=new UserService();
        Menu menu=new Menu();
        menu.optionMenu(scanner,users,userService,productMap,orders,product);
    }
}
