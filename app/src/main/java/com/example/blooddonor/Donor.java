package com.example.blooddonor;

public class Donor {

    private int id;
    private String name;
    private String phone;
    private String email;
    private String bloodGroup;
    private String age;
    private String gender;
    private String address;

    public Donor(int id, String name, String phone, String email,
                 String bloodGroup, String age, String gender, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.gender = gender;
        this.address = address;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getBloodGroup() { return bloodGroup; }
    public String getAge() { return age; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
}
