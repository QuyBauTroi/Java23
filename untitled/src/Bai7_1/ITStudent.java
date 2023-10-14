package Bai7_1;

import BTVN7.TechMasterStudent;

public  class ITStudent extends TechMasterStudent {
    private double javaScore;
    private double htmlScore;
    private double cssScore;

    public ITStudent(String name, String object, double javaScore, double htmlScore, double cssScore) {
        super(name, object);
        this.javaScore = javaScore;
        this.htmlScore = htmlScore;
        this.cssScore = cssScore;
    }




    @Override
    public double getScore() {
        return (2*javaScore + htmlScore + cssScore)/4;
    }
}
