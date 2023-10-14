package BTVN7;

public  class BizStudent extends TechMasterStudent {
    private double markettingScore;
    private double salesScore;

    public BizStudent(String name, String object, double markettingScore, double salesScore) {
        super(name, object);
        this.markettingScore = markettingScore;
        this.salesScore = salesScore;
    }

    @Override
    public double getScore() {
        return (2*markettingScore + salesScore)/3 ;
    }
}
