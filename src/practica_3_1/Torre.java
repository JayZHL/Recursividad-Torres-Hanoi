/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica_3_1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *  Programa que resuelve el problema de las torres de Hanoi utilizando 
 *  recursividad junto con graphics
 * @author Juan Carlos Huerta Llamas
 * version Lab Algoritmos, Fecha de inicio: 2019.09.16
 * Fecha de finalizacion: 2019.10.01
 */
public class Torre extends JFrame implements ActionListener,Runnable{
    int n;
    int num;
    int anchura;
    int altura;
    int [][]soportetam=new int[3][6];
    int []torre=new int[3];
    Graphics2D graficos;
    Thread t=new Thread(this);
    
    Rectangle []soporte=new Rectangle[3];
    Rectangle []disco;
    ArrayList <Color>lista= new ArrayList();
    JButton inicia= new JButton("Iniciar");
    JButton fin= new JButton("Fin");
    
    /**
     * Constructor del metodo pide el numero de rectangulos hasta maximo seis
     * @param n 
     */
    public Torre(int n) {
        this.n = n;
        anchura=1800;
        altura=10000;
        disco= new Rectangle[n];
        torre[0]=n;
        torre[1]=0;
        torre[2]=0;
        setTitle("Torre de Hanoi");
        setLayout(null);
        setSize(anchura, altura);
        inicia.setBounds(300, 520,100,25);
        fin.setBounds(600, 520,100,25);
        add(inicia);
        add(fin);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        for(int i=0;i<n;i++){
            disco[i]= new Rectangle(150+i*12,475-i*25,200-i*25, 25);//Ponemos los primeros discos
            soportetam[0][i]=i;
        }
        
        soporte[0]=new Rectangle(250,200,15,300);
        soporte[1]=new Rectangle(510,200,15,300);
        soporte[2]=new Rectangle(760,200,15,300);
        
        inicia.addActionListener(this);
        fin.addActionListener(this);
    }
    /**
     * Aqui usaremos recursividad para resolver el problema y mover los rectangulos
     * tomando encuentas sus posiciones, mantiene el numero de disco en el soporte
     * @param ndiscos
     * @param soporteorigen
     * @param soportedest
     * @param soporteaux 
     */
    public void Resuelve(int ndiscos, int soporteorigen, int soportedest, int soporteaux){
        if (ndiscos==1){
            int npos=260;
            try{
                t.sleep(1000);
                
                
                soportetam[soportedest-1][torre[soportedest-1]]=
                        soportetam[soporteorigen-1][--torre[soporteorigen-1]];
                if(soporteorigen==1 &&soportedest==2 || soporteorigen==3 && soportedest==2){
                    npos=npos;
                }else{
                    if(soporteorigen==1 && soportedest==3 || 
                             soporteorigen==2 &&soportedest==3){
                        npos=npos*2;
                    }else{
                        if(soporteorigen==3 && soportedest==1||soporteorigen==2 && soportedest==1){
                            npos=0;
                        }
                    }
                }
                num= soportetam[soportedest-1][torre[soportedest-1]++];
                disco[num].setLocation(150+num*12+npos,475-(torre[soportedest-1]-1)*25);
                repaint();
            }catch(Exception e){
                System.out.println("Error");
            }
        }else{
            Resuelve(ndiscos -1, soporteorigen, soporteaux, soportedest);
            Resuelve(1, soporteorigen, soportedest, soporteaux);
            Resuelve(ndiscos -1, soporteaux, soportedest, soporteorigen);
        }
    }
    
    /**
     * En este metodo pintaremos nuestros rectangulos
     * @param g 
     */
    public void paint(Graphics g){
       super.paint(g);
       g.setColor(Color.BLACK);
      
       for(int i=0;i<3;i++){ 
         g.fill3DRect(soporte[i].x, soporte[i].y, soporte[i].width, soporte[i].height, true);
         g.draw3DRect(soporte[i].x, soporte[i].y, soporte[i].width, soporte[i].height, true);
       //Aqui pintamos los pilares
       }
      
       g.drawLine(100,500, 850,500);
       lista.add(Color.blue);
       lista.add(Color.GREEN);
       lista.add(Color.YELLOW);
       lista.add(Color.red);
       lista.add(Color.ORANGE);
       lista.add(Color.PINK);
       //Con un ArrayList anadimos nuestro colores predeterminados 
       
       
       //Aqui pintamos los discos
      for(int i=0;i<n;i++){
          g.setColor(lista.get(i));
          g.fill3DRect(disco[i].x,disco[i].y,disco[i].width,disco[i].height,true);
          g.setColor(Color.black);
          g.draw3DRect(disco[i].x, disco[i].y, disco[i].width, disco[i].height, true);
          g.setColor(Color.black);
          
         }
    }
    
    /**
     * Metodos del JButton
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==inicia){
          t.start();
         
      }
       
        if(e.getSource()==fin){
            System.exit(0);
        }
    }
    
    /**
     * Para ir ejecutando la solucion
     */
    @Override
    public void run() {
        Resuelve(n,1,3,2);
    }
    
    
}
