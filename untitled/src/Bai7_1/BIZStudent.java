package Bai7_1;

import BTVN7.TechMasterStudent;

public  class BIZStudent extends TechMasterStudent {
    private double markettingScore;
    private double salesScore;

    public BIZStudent(String name, String object, double markettingScore, double salesScore) {
        super(name, object);
        this.markettingScore = markettingScore;
        this.salesScore = salesScore;
    }

    @Override
    public double getScore() {
        return (2*markettingScore + salesScore)/3 ;
    }
}
