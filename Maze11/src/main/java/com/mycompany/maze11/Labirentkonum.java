/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maze11;

/**
 *
 * @author Aylin
 */
public class Labirentkonum {
     private int x;
    private int y;
   
public static void main(String[] args) {
        Labirentkonum güncel = new Labirentkonum(0, 1);
        Labirentkonum next = new Labirentkonum(0, 1);
        System.out.println(güncel.equals(next));
    }
    public Labirentkonum(int x, int y){
        this.x = x;
        this.y = y;
    }
    

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        Labirentkonum other = (Labirentkonum) obj;
        return x == other.getX() && y == other.getY();
    }

    @Override
    public String toString() {
        return "Konum(" + x + ", " + y + ")";
    }

    
}
