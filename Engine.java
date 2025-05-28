import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.*;
import control.Keyboard;
import control.Render;
import java.util.Random;

public class Engine extends JFrame {
    Render panel = null;
    public static int W = 1920, H = 1080;
    public Engine(){
        Random rand = new Random();
        // int rand_int1 = rand.nextInt(1000);
        // int rand_int2 = rand.nextInt(1000);
   
        panel = new Render(W,H,1000);
        // for(int i = 0;i < 100; ++i){
        //     panel.add(
        //         rand.nextDouble(100),
        //         rand.nextDouble(100),
        //         rand.nextDouble(20),
        //         rand.nextDouble(20),
        //         rand.nextDouble(2000),
        //         rand.nextDouble(2000),
        //         new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));    
        // }
        panel.add(-10,-10,20,20,30,50,Color.RED);
        panel.add(-20,-20,10,10,50,70,Color.BLUE);
        panel.add(-10,-10,20,20,30,31,Color.GREEN);
        panel.add(-1,-1,1,1,10,11,Color.RED);
        panel.add(-5,-5,5,5,-5,0,Color.RED);
        panel.add(-10,-10,5,5,-10,-5,Color.GREEN);
        panel.add(-10,10,5,5,-10,-5,Color.BLUE);
        panel.add(10,-10,5,5,-10,-5,Color.GREEN);
        panel.add(10,10,5,5,-10,-5,Color.BLUE);
        
        panel.add(-10,-10,5,5,10,15,Color.GREEN);
        panel.add(-10,10,5,5,10,15,Color.BLUE);
        panel.add(10,-10,5,5,10,15,Color.GREEN);
        panel.add(10,10,5,5,10,15,Color.BLUE);
        
        panel.add(-100,0,100,2,-100,100,Color.MAGENTA);
        
        //panel.add(0,0,10,1,0,1,Color.RED);
        //panel.add(0,0,1,10,0,1,Color.BLUE);
        //panel.add(0,0,1,1,0,10,Color.GREEN);
        add(panel);
        setSize(W, H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(new Keyboard(panel));
    }

    public void inToin(int move){
        panel.redraw(move);
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.inToin(KeyEvent.VK_C);
        try{
            while(true){
                Thread.sleep(10);
                engine.inToin(KeyEvent.VK_Q);
            }
        }catch(Exception e){
            System.out.println("err : "+e.getMessage());
        }
    }
}
