package com.example.attendence_manager.Model_classes;

public class student_model {
    String name,roll_no,gender,status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //parameterised constructor
    public student_model(String name, String roll_no, String gender) {
        this.name = name;
        this.roll_no = roll_no;
        this.gender = gender;
        this.status=status;
    }
//default constructor
    public student_model() {
    }
//getter setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
