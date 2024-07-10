/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maze11;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Aylin
 */
public class Labirent {
  
    Random random;
    int[][] dizi;
    boolean[][] ziyaretedildi;
    boolean[][] gecilenyol;
    int yol = 0;
    int adım = 0;
    int boyut;
      public static void main(String[] args) {
      
        System.out.println("lütfen boyut giriniz:\n");
        Scanner sc=new Scanner(System.in);
        int boyut=sc.nextInt();
        
        Labirentkonum start = new Labirentkonum(1, 0);
        Labirentkonum end = new Labirentkonum(boyut, boyut );		

        Labirent maze = new Labirent(boyut);
        maze = new Labirent(boyut);
       


    }

  
    public int[][] getArray() {
        return this.dizi;
    }

    private boolean yukarı(Labirentkonum k) {
        return k.getX() - 2 > 0;
    }

    private boolean sağ(Labirentkonum k) {
        return k.getY() + 2 <= boyut;
    }

    private boolean aşağı(Labirentkonum k) {
        return k.getX() + 2 <= boyut;
    }

    private boolean sol(Labirentkonum k) {
        return k.getY() - 2 > 0;
    }

      public Labirent(int boyut) {
        if (boyut % 2 == 0) 
            boyut++;
        this.boyut = boyut;
         dizi = new int[boyut + 2][boyut + 2];
        random = new Random();
        gecilenyol = new boolean[boyut + 2][boyut + 2];
        ziyaretedildi = new boolean[boyut + 2][boyut + 2];
       
        labirentoluştur();
    }
       public void labirentoluştur() {
       
        int i=0;
        while(i<boyut+2){
             dizi[0][i] = 1;
            dizi[boyut + 1][i] = 1;
            dizi[i][0] = 1;
           dizi[i][boyut + 1] = 1;
           i++;
        }
     
   int satir=1;
        while (satir <= boyut) {
            for (int sütun = 1; sütun <= boyut; sütun++) {
                if (satir % 2 == 1 && sütun% 2 == 1) {
                    dizi[satir][sütun] = 0;
                    ziyaretedildi[satir][sütun] = false;
                   yol++;
                    gecilenyol[satir][sütun] = false;
                } else {
                    dizi[satir][sütun] = 1;
                }
            }
            satir++;
        }

        dizi[1][0] = 0;
        dizi[boyut][boyut] = 0;

       
        int xMevcutKonum = 1;
        int yMevcutKonum = 1;
        Labirentkonum güncel= new Labirentkonum(xMevcutKonum, yMevcutKonum);
        
        int ziyaretedilenhücre = 1;
        ziyaretedildi[xMevcutKonum][yMevcutKonum] = true;
        while (ziyaretedilenhücre < yol) {
            adım++;
            Labirentkonum next = randomSonraki(güncel);
            int xNextKonum = next.getX();
            int yNextKonum = next.getY();

            if (!ziyaretedildi[xNextKonum][yNextKonum] ) {
                
                ziyaretedildi[xNextKonum][yNextKonum] = true;
                ziyaretedilenhücre++;
                yolYarat(güncel, next);
            }
            
            güncel.setX(next.getX());
            güncel.setY(next.getY());
        }
    }


    private Labirentkonum randomSonraki(Labirentkonum güncel) {
        
        ArrayList<Integer> sonrakiList = new ArrayList<Integer>();
        
        if (yukarı(güncel))
            sonrakiList.add(0);
        
        if (sağ(güncel))
            sonrakiList.add(1);
        
        if (aşağı(güncel))
           sonrakiList.add(2);
        
        if (sol(güncel))
            sonrakiList.add(3);

        int deger = sonrakiList.get(random.nextInt(sonrakiList.size()));
        
        if(deger==0)
            return new Labirentkonum(güncel.getX() - 2, güncel.getY());
        if(deger==1)
            return new Labirentkonum(güncel.getX(), güncel.getY() + 2);
        if(deger==2)
             return new Labirentkonum(güncel.getX() + 2, güncel.getY());
        if(deger==3)
            return new Labirentkonum(güncel.getX(), güncel.getY() - 2);
        else
             throw new IllegalArgumentException("beklenmeyen deger: " + deger);
       
  
    }

    private void yolYarat(Labirentkonum current, Labirentkonum next) {
        int x = (current.getX() + next.getX()) / 2;
        int y = (current.getY() + next.getY()) / 2;
        dizi[x][y] = 0;
    }

     //labirentin tum yönlerinin kontrolu sağlanıyo

    public boolean sağHareket(Labirentkonum a) {
        return a.getY() + 1 <= boyut + 1 && dizi[a.getX()][a.getY() + 1] == 0;
    }

    public boolean aşağıHareket(Labirentkonum a) {
        return a.getX() + 1 <= boyut && dizi[a.getX() + 1][a.getY()] == 0;
    }

    public boolean solHareket(Labirentkonum a) {
        return a.getY() - 1 > 0 && dizi[a.getX()][a.getY() - 1] == 0;
    }

     public boolean yukarıHareket(Labirentkonum a) {
        return a.getX() - 1 > 0 && dizi[a.getX() - 1][a.getY()] == 0;
    }

 
  
    private void ziyaretEdilenduvarKaldır() {
        int i=0;
        while ( i < boyut + 2) {
            for (int j = 0; j < boyut + 2; j++) {
                gecilenyol[i][j] = false;
            }
            i++;
        }
    }


     public Stack<Labirentkonum> getWay(Labirentkonum start, Labirentkonum end) {
        
        Stack<Labirentkonum> visittedKonum = new Stack<Labirentkonum>();
        Stack<Labirentkonum> gecerlikonum = new Stack<Labirentkonum>();

        Labirentkonum gecerli = start;
        gecilenyol[1][0] = true;
        visittedKonum.push(new Labirentkonum(start.getX(), start.getY()));
        while (!gecerli.equals(end)) {
            int x = gecerli.getX();
            int y = gecerli.getY();
            if (yukarıHareket(gecerli) && gecilenyol[x - 1][y] == false)
                gecerlikonum.push(new Labirentkonum(x - 1, y));
            
            if (solHareket(gecerli) && gecilenyol[x][y - 1] == false)
                gecerlikonum.push(new Labirentkonum(x, y - 1));
            
            if (sağHareket(gecerli) && gecilenyol[x][y + 1] == false)
                gecerlikonum.push(new Labirentkonum(x, y + 1));
            
            if (aşağıHareket(gecerli) && gecilenyol[x + 1][y] == false)
                gecerlikonum.push(new Labirentkonum(x + 1, y));

            gecerli = gecerlikonum.pop();
            
            visittedKonum.push(new Labirentkonum(gecerli.getX(), gecerli.getY()));
            x = gecerli.getX();
            y = gecerli.getY();

            gecilenyol[x][y] = true;
        }
        Stack<Labirentkonum> way = new Stack<Labirentkonum>();
        while (!visittedKonum.empty()) {
            way.push(visittedKonum.pop());
        }
        ziyaretEdilenduvarKaldır();
        return way;

    }
       private boolean Hareket(Labirentkonum current, Labirentkonum next) {
        
        ArrayList<Labirentkonum> nexts = new ArrayList<Labirentkonum>();
         if (sağHareket(current))
            nexts.add(new Labirentkonum(current.getX(), current.getY() + 1));
        if (yukarıHareket(current))
            nexts.add(new Labirentkonum(current.getX() - 1, current.getY()));
      if (solHareket(current))
            nexts.add(new Labirentkonum(current.getX(), current.getY() - 1));
        if (aşağıHareket(current))
            nexts.add(new Labirentkonum(current.getX() + 1, current.getY()));
       
        return nexts.contains(next);
    }
      
}
