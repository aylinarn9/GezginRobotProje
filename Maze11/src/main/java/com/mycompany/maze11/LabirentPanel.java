/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.maze11;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Aylin
 */
public class LabirentPanel extends JPanel {
    
    
    private Labirentkonum baslangıc;
    private Labirentkonum son;
    private Labirentkonum current;
    private int delay = 60;
    public Labirent maze;
    long baslangıczamanı;
    long bitiszamanı;
    long geçensüre;
    
  
    double labirentTime;
    
  
  
 private static final long serialVersionUID = 1L;
    private Rectangle2D[][] hücre;
    private static final int boyut = 600;
    public int[][] dizi;
    public int labirenthücre;
    public int hücresize; 
    
    public LabirentPanel(int hücreMaze) {
        
        yenileme(hücreMaze);
    }

    public int getlabirenthücre() {
        return this.labirenthücre;
    }

    public void yenileme(int labirenthücre) {
        this.labirenthücre = labirenthücre;
        setSize(boyut, boyut);
        hücresize =  ( boyut / labirenthücre);
        repaint();
     
        maze = new Labirent(labirenthücre - 2);
        dizi = maze.getArray();
        baslangıc = current = new Labirentkonum(1, 0);
        son = new Labirentkonum(labirenthücre - 2, labirenthücre - 1);

      
        hücre = new Rectangle2D[labirenthücre][labirenthücre];
        
        for (int i = 0; i < labirenthücre; i++) {
            for (int j = 0; j < labirenthücre; j++) {
                hücre[i][j] = new Rectangle2D.Double(j * hücresize, i * hücresize, hücresize, hücresize);
            }
        }
    }

    /**
     * Fill maze on panel by Graphics2D.
     *
     * @param Graphics2D
     * @return void
     * @author Hulk
     */
    private void labirentçizme(Graphics2D g2d) {
        try{
        for (int i = 0; i < labirenthücre; i++) {
            for (int j = 0; j < labirenthücre; j++) {
                if (dizi[i][j] == 0) {
                    g2d.setColor(Color.WHITE);
                } else {
                    g2d.setColor(Color.BLACK);
                }
                g2d.fill(hücre[i][j]);
            }
        }

      
        int x = baslangıc.getX();
        int y = baslangıc.getY();

        g2d.setColor(Color.GREEN);
        g2d.fill(hücre[x][y]);
        }
        catch(Exception e){
            System.out.println("drawMaze "+e.getMessage());
            }
    }


    public void gezilenyol(Graphics2D g2d) {
         baslangıczamanı = System.nanoTime();
         int adımsayısı=0;
        Stack<Labirentkonum> way = maze.getWay(baslangıc, son);
        g2d.setColor(Color.PINK);
        while (!way.empty()) {
             bitiszamanı=System.nanoTime();
            Labirentkonum next = way.pop();
            int x = next.getX();
            int y = next.getY();
            g2d.fill(hücre[x][y]);
            try {
                Thread.sleep(delay);
                adımsayısı++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         geçensüre = bitiszamanı - baslangıczamanı;    
     System.out.println("atılan adım sayısı:"+adımsayısı);
        double labirentTime = (double) geçensüre / 1000000;   
        System.out.println(String.format("Time %1.2f s", labirentTime/1000));
       
         
        
    }

    private boolean passed() {
        return current.equals(son);
    }

    private void hareket(Graphics2D g2d, Labirentkonum current) {
        int x = current.getX();
        int y = current.getY();
       
        g2d.setColor(Color.GREEN);
        g2d.fill(hücre[x][y]);

        
        if (passed()) {
            yenileme(this.labirenthücre);
            repaint();
        }
    }

 
    @Override
    protected void paintComponent(Graphics g) {
       labirentçizme((Graphics2D) g);
    }

   
    
    
    
    
    
}
