package com.example.sensormenuapplication;



public class Gyroscope {
    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String value1 = "";
    String value2 = "";
    String value3 = "";
    int id ;
    public Gyroscope(){}
    public Gyroscope(String value1,String value2,String value3){
        this.value1=value1;
        this.value2=value2;
        this.value3=value3;


    }
    public Gyroscope(int id,String value1,String value2,String value3){
        this.value1=value1;
        this.value2=value2;
        this.value3=value3;


    }

}


