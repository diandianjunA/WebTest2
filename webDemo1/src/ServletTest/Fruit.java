package ServletTest;

import java.math.BigDecimal;

class Fruit{
    private String fruitName;
    private BigDecimal price;
    private BigDecimal repertory;
    private String remark;

    public Fruit() {
    }

    public Fruit(String fruitName, BigDecimal price, BigDecimal repertory, String remark) {
        this.fruitName = fruitName;
        this.price = price;
        this.repertory = repertory;
        this.remark = remark;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRepertory() {
        return repertory;
    }

    public void setRepertory(BigDecimal repertory) {
        this.repertory = repertory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "fruitName='" + fruitName + '\'' +
                ", price=" + price +
                ", repertory=" + repertory +
                ", remark='" + remark + '\'' +
                '}';
    }
}
