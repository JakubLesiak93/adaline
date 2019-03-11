class RecordHolder {
    private double[] weight;
    private int lifeTime;
    private double ERR;

    RecordHolder(double[] weight, int lifeTime, int ERR){
        this.weight = weight;
        this.lifeTime = lifeTime;
        this.ERR = ERR;
    }

    RecordHolder(double[] weights){
        this.weight = weights;
    }

    void setERR(double ERR) {
        this.ERR = ERR;
    }

    double getERR() {
        return ERR;
    }

    void setWeight(double[] weight) {
        this.weight = weight;
    }

    double[] getWeight() {
        return weight;
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    public int getLifeTime() {
        return lifeTime;
    }
}
