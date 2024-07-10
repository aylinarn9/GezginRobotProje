/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.maze11;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


/**
 *
 * @author Aylin
 */

   
   public class Izgara extends JFrame {
  
    final static int V = 9;//yol
    Graphics g;
    final static int X = 1;
    final static int Y = 2;
    final static int Z = 3;

    final static int S = 5; //baslangıc

    final static int E = 8; // bitis

    final static int ilki = 1, ilkj = 0;


    static int[][] matris = new int[][]{

    };
    static int[][] kmatris = new int[][]{

    };

  
   
    static boolean urlkontrol = false;
    JButton urldegis;
    JButton calıştır;
    JButton sonuc;
    
  JLabel gecensüre;
  JTextField textDfs;
  double dfsTime;
  
  JLabel adım;
  JTextField textadım;
  int adımsayac;

    boolean repaint = false;
    long baslangıczamanı;
    long bitiszamanı;
    long geçensüre;
   
    ///2.problem tanımlamaları
    private static final long serialVersionUID = 1;
    public LabirentPanel Panel;  // labirentpanelden panel alıyo
    JButton labirentdegistir;
    JButton OYNAT;


    private int[][] getMaze() {
        return this.matris;
    }

    private static void setMaze() throws IOException {
        URL url = null;
        if (!urlkontrol) {
            url = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url1.txt");
            urlkontrol = true;
        } else {
            url = new URL("http://bilgisayar.kocaeli.edu.tr/prolab2/url2.txt");
            urlkontrol = false;
        }

        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())));

        int satirsayısı= 0;
        int sütunsayısı = 0;
        boolean countControl = false;
        while (scanner.hasNextLine()) {
            if (!countControl) {
                sütunsayısı = scanner.nextLine().split("").length;
                countControl = true;
            } else {
                scanner.nextLine();
            }
            satirsayısı++;
        }
        Scanner newScanner = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())));

        int[][] mazeMatrix = new int[satirsayısı][sütunsayısı];
        while (newScanner.hasNextLine()) {
            for (int i = 0; i < mazeMatrix.length; i++) {
                String[] line = newScanner.nextLine().trim().split("");

                for (int j = 0; j < line.length; j++) {
                    mazeMatrix[i][j] = Integer.parseInt(line[j]);
                }
            }
        }
        for (int i = 0; i < mazeMatrix.length; i++) {
            for (int j = 0; j < mazeMatrix.length; j++) {
                if (mazeMatrix[i][j] == 3) {
                    Random rd = new Random();
                    if (rd.nextBoolean() == false) {
                        mazeMatrix[i][j] = 0;
                    }

                }

            }
        }
        for (int i = 0; i < mazeMatrix.length; i++) {
            for (int j = 0; j < mazeMatrix.length; j++) {
                if (mazeMatrix[i][j] == 2) {
                    Random rd = new Random();
                    if (rd.nextBoolean() == false) {
                        mazeMatrix[i][j] = 0;
                    }

                }

            }
        }
        
        mazeMatrix[0][0] = 5;
        mazeMatrix[satirsayısı - 1][satirsayısı - 1] = 8;


        matris = mazeMatrix;
    }
//bu kısım en kısa yolu bulmak ve çizdirmek için kullandım
    private static void setMaze2() throws IOException {
        kmatris = new int[matris.length][matris.length];
        for (int i = 0; i < matris.length; i++) {

            for (int j = 0; j < matris.length; j++) {

                if (matris[i][j] == 1 || matris[i][j] == 2 || matris[i][j] == 3) {

                    kmatris[i][j] = 0;
                } else if (matris[i][j] == 5 || matris[i][j] == 8 || matris[i][j] == 0) {
                    kmatris[i][j] = 1;
                }

            }

        }
    }

    public Izgara() throws IOException {

        JFrame f = new JFrame();
        JButton problem1buton;
        problem1buton = new JButton("problem1");
        JButton problem2buton;
        problem2buton = new JButton("problem2");


        problem1buton.setBounds(50, 50, 100, 30);
        problem2buton.setBounds(200, 50, 100, 30);

        f.add(problem1buton);
        f.add(problem2buton);
        f.setSize(400, 200);
        f.setLayout(null);
        f.setVisible(true);

        int test[][] = getMaze();
        setTitle("Maze");
        setSize(900, 600);
        setLocationRelativeTo(null);                                                
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);


        gecensüre = new JLabel("GEÇEN SÜRE:");
        textDfs = new JTextField();
        adım= new JLabel("ATILAN ADIM:");
        textadım = new JTextField();
        
        calıştır = new JButton("ÇALIŞTIR");
        urldegis = new JButton("URL DEGİS");
        sonuc = new JButton("SONUC GOSTER");
        add(sonuc);
        add(calıştır);
        add(urldegis);
        add(gecensüre);
        add(textDfs);
        add(adım);
        add(textadım);


        calıştır.setBounds(500, 50, 100, 40);
        urldegis.setBounds(630, 50, 100, 40);
        sonuc.setBounds(750, 50, 100, 40);

        gecensüre.setBounds(500, 100, 100, 40);
        textDfs.setBounds(500, 130, 100, 25);
        
       adım.setBounds(650, 100, 100, 40);
        textadım.setBounds(650, 130, 100, 25);
       


        problem1buton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(true);
                f.setVisible(false);
            }
        });

        problem2buton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame l = new JFrame();
                l.setTitle("Labirent Game ");
                l.setSize(1000, 900);
                l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                l.setLayout(null);
                labirentdegistir = new JButton("LABİRENTDEĞİŞ");
                
                OYNAT = new JButton("OYNAT");
                
           //sürelabirent= new JLabel("GEÇEN SÜRE:");
          //textlabirent = new JTextField();
                labirentdegistir.setBounds(700, 50, 200, 100);
                
                //sürelabirent.setBounds(700, 250, 200, 100);
                //textlabirent.setBounds(700, 280, 200, 85);
                
                OYNAT.setBounds(700, 450, 200, 100);
                
                l.add(labirentdegistir);
 
                l.add(OYNAT);
               
                System.out.println("lütfen boyut giriniz:\n");
                Scanner sc = new Scanner(System.in);
                int boyut = sc.nextInt();

                Panel = new LabirentPanel(boyut);
                l.add(Panel);

                f.setVisible(false);
                setVisible(false);
                l.setVisible(true);
              
                labirentdegistir.addActionListener(new ActionListener() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //repaint();
                        Panel.yenileme(Panel.getlabirenthücre());


                    }

                });
               

                OYNAT.addActionListener(new ActionListener() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Panel.gezilenyol((Graphics2D) Panel.getGraphics());
                        repaint();
                    }

                });

            }


        });


        urldegis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    repaint();
                    repaint = true;
                    setMaze();
                    setMaze2();
                    // repaint();

                } catch (IOException ex) {
                  
                }


            }


        });


        calıştır.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                repaint = false;
                try {

                    solveStack(false);


                } catch (InterruptedException ex) {
                
                }
                repaint();


            }
        });

        sonuc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                // repaint = true;

                int n = kmatris.length;
                findPath(kmatris, n);
              
                //repaint = false;
                // repaint();

            }
        });


    }


    public int MatrisBoyut() {
        return matris.length;
    }


    public void Print() {
        int sayac = 0;
        for (int i = 0; i < MatrisBoyut(); i++) {      
            for (int J = 0; J < MatrisBoyut(); J++) {
                if (matris[i][J] == 9) {
                    sayac++;
                }
                System.out.print(matris[i][J]);

                System.out.print(' ');         
            }
            System.out.println();
        }
        System.out.println(" ADIM SAYISI:" + sayac);
        textadım.setText(String.format("%d", sayac));
    }


    public boolean IzgaraSınırKontrol(int i, int j) {

        if (i >= 0 && i < MatrisBoyut() && j >= 0 && j < MatrisBoyut()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean IzgaraSınırKontrol(Robot pos) {
        return IzgaraSınırKontrol(pos.i(), pos.j());
    }


    public int isaretle(int i, int j, int value) {

        assert (IzgaraSınırKontrol(i, j));
        int temp = matris[i][j];
        matris[i][j] = value;
        return temp;
    }

    public int isaretle(Robot pos, int value) {
        return isaretle(pos.i(), pos.j(), value);
    }


    public boolean yolBoşMu(int i, int j) {
        assert (IzgaraSınırKontrol(i, j));
        return (matris[i][j] != X && matris[i][j] != Y && matris[i][j] != Z && matris[i][j] != V);

    }

    public boolean yolBoşMu(Robot pos) {
        return yolBoşMu(pos.i(), pos.j());
    }


    public boolean sonMu(int i, int j) {

        return (i == Izgara.matris.length - 1 && j == Izgara.matris.length - 1);
    }

    public boolean sonMu(Robot pos) {
        return sonMu(pos.i(), pos.j());
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.translate(70, 70);

        // matrisi engellere göre çiziyor
        if (repaint == true) {
            for (int satir = 0; satir < matris.length; satir++) {
                for (int sütun = 0; sütun < matris[0].length; sütun++) {
                    Color color;
                    switch (matris[satir][sütun]) {
                        case 1:
                            color = Color.DARK_GRAY;
                            break;
                        case 3:
                            color = Color.DARK_GRAY;
                            break;
                        case 8:
                            color = Color.RED;          // bitis
                            break;
                        case 5:
                            color = Color.BLUE;      // baslangıc
                            break;
                        case 2:
                            color = Color.DARK_GRAY;
                            break;
                        default:
                            color = Color.DARK_GRAY;
                    }
                    g.setColor(color);
                    g.fillRect(20 * sütun, 20 * satir, 20, 20);
                    g.setColor(Color.GREEN);
                    g.drawRect(20 * sütun, 20 * satir, 20, 20);

                }
            }
        }

        if (repaint == false) {
            for (int satir = 0; satir < matris.length; satir++) {
                for (int sütun = 0; sütun < matris[0].length; sütun++) {
                    Color color;
                    switch (matris[satir][sütun]) {
                        case 1:
                            color = Color.DARK_GRAY;
                            break;
                        case 3:
                            color = Color.DARK_GRAY;
                            break;
                        case 8:
                            color = Color.RED;          // bitis
                            break;
                        case 5:
                            color = Color.BLUE;        //baslangıc
                            break;//baslangıc

                        case 2:
                            color = Color.DARK_GRAY;
                            break;

                        case 9:
                            color = Color.GREEN;
                            break;
                        default:
                            color = Color.DARK_GRAY;
                    }
                    g.setColor(color);
                    g.fillRect(20 * sütun, 20 * satir, 20, 20);
                    g.setColor(Color.GREEN);
                    g.drawRect(20 * sütun, 20 * satir, 20, 20);

                }

            }

        }
        //repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override

            public void run() {
                try {
                    Izgara ızgara = new Izgara();
                    setMaze();
                    setMaze2();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

     
       
         
        
    

    public void solveStack(boolean yeni) throws InterruptedException {


        baslangıczamanı = System.nanoTime();


        Stack<Robot> stack = new Stack<Robot>();
        stack.push(new Robot(ilki, ilkj));

        Robot crt;
        Robot next;
        while (!stack.empty()) {

            crt = stack.pop();
            if (sonMu(crt)) {

                break;
            }

//System.out.println(crt.getI()+" "+crt.getJ()); gezilen yerlerin konumunu tutuyor

            g = this.getGraphics();

            g.translate(70, 70);

           
            isaretle(crt, V);
            long delay = 0;

            if (!yeni) {
                delay = 350;
            }

            Thread.sleep(delay);

            Color color;
            switch (matris[crt.getI()][crt.getJ()]) {
                case 9:
                    color = Color.GREEN;
                    break;
                default:
                    color = Color.DARK_GRAY;
            }

            g.setColor(color);
            g.fillRect(20 * crt.getJ(), 20 * crt.getI(), 20, 20);
            g.setColor(Color.GREEN);
            g.drawRect(20 * crt.getJ(), 20 * crt.getI(), 20, 20);

            if (IzgaraSınırKontrol(crt.kuzey())) {
                if (!yolBoşMu(crt.kuzey())) {
                    switch (matris[crt.kuzey().getI()][crt.kuzey().getJ()]) {
                        case 1:
                            color = Color.CYAN;
                            break;
                        case 3:
                            color = Color.PINK;
                            break;
                        case 2:
                            color = Color.YELLOW;
                            break;
                        default:
                            color = Color.GREEN;
                    }
                    g.setColor(color);
                    g.fillRect(20 * crt.kuzey().getJ(), 20 * crt.kuzey().getI(), 20, 20);
                    g.setColor(Color.GREEN);
                    g.drawRect(20 * crt.kuzey().getJ(), 20 * crt.kuzey().getI(), 20, 20);
                }
            }
            if (IzgaraSınırKontrol(crt.dogu())) {
                if (!yolBoşMu(crt.dogu())) {
                    System.out.println("i : " + crt.dogu().getI() + " j : " + crt.dogu().getJ());
                    switch (matris[crt.dogu().getI()][crt.dogu().getJ()]) {
                        case 1:
                            color = Color.CYAN;
                            break;
                        case 3:
                            color = Color.PINK;
                            break;
                        case 2:
                            color = Color.YELLOW;
                            break;
                        default:
                            color = Color.GREEN;
                    }
                    g.setColor(color);
                    g.fillRect(20 * crt.dogu().getJ(), 20 * crt.dogu().getI(), 20, 20);
                    g.setColor(Color.GREEN);
                    g.drawRect(20 * crt.dogu().getJ(), 20 * crt.dogu().getI(), 20, 20);
                }
            }
            if (IzgaraSınırKontrol(crt.batı())) {
                if (!yolBoşMu(crt.batı())) {
                    System.out.println("i : " + crt.batı().getI() + " j : " + crt.batı().getJ());
                    switch (matris[crt.batı().getI()][crt.batı().getJ()]) {
                        case 1:
                            color = Color.CYAN;
                            break;
                        case 3:
                            color = Color.PINK;
                            break;
                        case 2:
                            color = Color.YELLOW;
                            break;
                        default:
                            color = Color.GREEN;
                    }
                    g.setColor(color);
                    g.fillRect(20 * crt.batı().getJ(), 20 * crt.batı().getI(), 20, 20);
                    g.setColor(Color.GREEN);
                    g.drawRect(20 * crt.batı().getJ(), 20 * crt.batı().getI(), 20, 20);
                }
            }
            if (IzgaraSınırKontrol(crt.güney())) {
                if (!yolBoşMu(crt.güney())) {
                    System.out.println("i : " + crt.güney().getI() + " j : " + crt.güney().getJ());
                    switch (matris[crt.güney().getI()][crt.güney().getJ()]) {
                        case 1:
                            color = Color.CYAN;
                            break;
                        case 3:
                            color = Color.PINK;
                            break;
                        case 2:
                            color = Color.YELLOW;
                            break;
                        default:
                            color = Color.GREEN;
                    }
                    g.setColor(color);
                    g.fillRect(20 * crt.güney().getJ(), 20 * crt.güney().getI(), 20, 20);
                    g.setColor(Color.GREEN);
                    g.drawRect(20 * crt.güney().getJ(), 20 * crt.güney().getI(), 20, 20);
                }
            }

            next = crt.kuzey();
            if (IzgaraSınırKontrol(next) && yolBoşMu(next)) {
                stack.push(next);

            }
            next = crt.dogu();
            if (IzgaraSınırKontrol(next) && yolBoşMu(next)) {
                stack.push(next);
            }

            next = crt.batı();
            if (IzgaraSınırKontrol(next) && yolBoşMu(next)) {
                stack.push(next);
            }

            next = crt.güney();
            if (IzgaraSınırKontrol(next) && yolBoşMu(next)) {
                stack.push(next);
            }

        }

        if (!stack.empty()) {
            bitiszamanı = System.nanoTime();

        }
         else {

        }

        Print();

        geçensüre = bitiszamanı - baslangıczamanı;

        dfsTime = (double) geçensüre / 1000000;
        System.out.println(String.format("Time %1.2f s", dfsTime/1000));
        textDfs.setText(String.format("%1.2f s", dfsTime/1000));


    }

    boolean GüvenliMi(int[][] arr, int row, int col) {
        return (row < arr.length && col < arr.length && col >= 0 && row >= 0 && arr[row][col] == 1);
    }

    boolean kısaYolBul(int[][] arr, int[][] sol, int row, int col) {
        if (row == arr.length - 1 && col == arr.length - 1 && arr[row][col] == 1) {
            sol[row][col] = 7;
            return true;

        }
        if (GüvenliMi(arr, row, col)) {
            sol[row][col] = 7;
            if (kısaYolBul(arr, sol, row + 1, col)) {
                return true;
            }

            if (kısaYolBul(arr, sol, row, col + 1)) {
                return true;
            }
            sol[row][col] = 0;

            return false;


        }

        return false;
    }

    /*void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[i][j] + " ");
            }

            System.out.println();
        }
    }*/

    void findPath(int[][] arr, int n) {

        int[][] kısayolMatris = new int[n][n];
        if (kısaYolBul(arr, kısayolMatris, 0, 0)) {
            //printArray(kısayolMatris);
        } else {
            System.out.println("No solution");
        }
        g = this.getGraphics();

        g.translate(70, 70);
        for (int i = 0; i < kısayolMatris.length; i++) {
            for (int j = 0; j < kısayolMatris.length; j++) {

                Color color;
                switch (kısayolMatris[i][j]) {
                    case 7:
                        color = Color.RED;
                        break;
                    default:
                        color = Color.WHITE;
                }

                g.setColor(color);
                g.fillRect(20 * j, 20 * i, 20, 20);
                g.setColor(Color.RED);
                g.drawRect(20 * j, 20 * i, 20, 20);
            }

        }

    }
}

 

