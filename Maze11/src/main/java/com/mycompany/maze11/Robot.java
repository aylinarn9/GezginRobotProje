/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maze11;

/**
 *
 * @author Aylin
 */
public class Robot {
     int i,j;                  
    
    public Robot(int i, int j){
     this.i=i;
     this.j=j;
    };
    public int i() { return i;}   

    public int j() { return j;}   

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
    
    public void Print(){
       System.out.println("(" + i + "," + j + ")");  
    }
    
   
    public Robot kuzey(){
      return new Robot(i-1,j);
    }
    
   
    public Robot güney(){
        return new Robot(i+1 , j);
    }
    
   
    public Robot dogu(){
        return new Robot(i,j+1);
    }
    
   
    public Robot batı(){
      return new Robot(i,j-1);
    }
}
