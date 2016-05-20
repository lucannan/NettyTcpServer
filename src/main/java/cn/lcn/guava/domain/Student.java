package cn.lcn.guava.domain;

public class Student {
    private int id;
    private String nameString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", nameString=" + nameString + "]";
    }

}
