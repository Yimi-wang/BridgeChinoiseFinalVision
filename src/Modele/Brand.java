package Modele;

public class Brand implements Cloneable, java.io.Serializable {
    // 扑克牌的类型 ：
    private Type type;
    private final Integer inttype;
    // 扑克牌的大小
    private Integer num;
    public Integer id;
    public Integer place;

    // 直接创建一个构造器用来 创建每一张扑克牌
    public Brand(Type type, Integer num, Integer inttype, Integer place) {
        super();
        this.type = type;
        this.num = num;
        this.inttype = inttype;
        this.id=inttype*13+num;
        this.place=place;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getInttype() {
        return inttype;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
    public Integer getPlace(){return place;}
    public void setPlace(Integer place){this.place=place;}
    @Override
    public String toString() {
        return "Brand [type=" + type + ", num=" + num + "]";
    }

    public Object clone() {
        Brand temp = null;
        try {
            temp = (Brand) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
