package BTVN7;



public class Main {
    public static void main(String[] args){
        TechMasterStudent svIt = new ItStudent("Quy","Superman",10,10,10);
        TechMasterStudent svBiz = new BizStudent("Nguyen","Cong an phuong",1,1);
        svIt.printInfo();
        svBiz.printInfo();
    }

}
