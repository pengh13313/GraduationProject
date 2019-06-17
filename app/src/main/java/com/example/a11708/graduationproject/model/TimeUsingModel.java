package com.example.a11708.graduationproject.model;

public class TimeUsingModel {
    private static volatile TimeUsingModel instance;
    private static boolean isUpdate = false;
    public static TimeUsingModel getInstance(){
        if(instance == null){
            synchronized (TimeUsingModel.class) {
                if (instance == null) {
                    instance = new TimeUsingModel();
                }
            }
        }
        return instance;
    }

    public static boolean isIsUpdate() {
        return isUpdate;
    }

    public static void setIsUpdate(boolean isUpdate) {
        TimeUsingModel.isUpdate = isUpdate;
    }





}
