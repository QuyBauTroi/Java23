package Bai7_1;




public class Main {
    public static void main(String[] args){
        BIZStudent[] students = new BIZStudent[3];
        students[0] = new BIZStudent("Nguyen","Cong an phuong",1,4);
        students[1] = new BIZStudent("Yen","Ki su",6,6);
        students[2] = new BIZStudent("Bong","Giao vien",4,1);


        ITStudent[] students1 = new ITStudent[3];
        students1[0] = new ITStudent("Quy","Superman",10,10,10);
        students1[1] = new ITStudent("Quynh","IOT",4,7,8);
        students1[2] = new ITStudent("Quyet","FontEnd",1,6,6);


        for (BIZStudent sv : students) {
            sv.printInfo();
        }
        for (ITStudent sv1 : students1){
            sv1.printInfo();
        }
    }
}
