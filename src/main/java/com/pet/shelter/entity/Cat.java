package com.pet.shelter.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "Cat_table ")

public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name= "name_cat", nullable = false)
    private final String name;

    @Column( nullable = false)
    private final int age;

    @Column(nullable = false)
    private final String color;

    public Cat(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getColor() {
        return color;
    }

    // этот класс нужно сделать сущностью животного, добавить поля, аннотации и сеттеры и геттеры
}
