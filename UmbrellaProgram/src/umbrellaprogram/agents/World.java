/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.agents;

import jade.core.Agent;

import jade.wrapper.ContainerController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import static umbrellaprogram.agents.World.humanWorld;

import umbrellaprogram.behaviour.WorldBehaviour;
import umbrellaprogram.principal.UmbrellaProgram;
/**
 *
 * @author rafael
 */
public class World extends Agent{
    
    public class PosicaoPP {

        public int posX, posY;
        public Human hz;

        private PosicaoPP(int posY, int posX, Human hz) {
            this.posX = posX;
            this.posY = posY;
            this.hz = hz;
        }
    }
    
//World Dimension = 50
    public static Human[][] humanWorld;
    public static Random rnd;
    public static HashMap<String, PosicaoPP> listaPosicoes =
            new HashMap<String, PosicaoPP>(1000);
        
    public static int population = 100;
    public static long day;
            
    ContainerController containerController;
    
    public void setup()
    {
        containerController = this.getContainerController();
        rnd = new Random();
        humanWorld  = new Human[50][50];
        
        for (int i = 0; i < population ; i++) 
        {
            int x, y;
            do 
            {
                x = rnd.nextInt(humanWorld[0].length);
                y = rnd.nextInt(humanWorld.length);
            } while (humanWorld[y][x] != null);
            addHuman(new Human("Pessoa " + i, x, y));

        }

        try 
        {
            Thread.sleep(2000);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addBehaviour(new WorldBehaviour(this));
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() 
            {
                createAndShowGUI();
            }
        });
        
    }
    
    
    public void addHuman (Human h) 
    {
        //containerController = this.getContainerController();
        humanWorld[h.posY][h.posX] = h;
        PosicaoPP posicaoPP = new PosicaoPP(h.posY, h.posX, h);
        UmbrellaProgram.addExistingAgent(containerController, h.name , humanWorld[h.posY][h.posX]);
        listaPosicoes.put(h.getLocalName(), posicaoPP);

    }
    
    public static void removeHuman (Human h)
    {
        //change state zombie to human;
    }
      
    private static void createAndShowGUI()
    {
        JFrame f = new JFrame("SMA: Modelo Humano-Zumbi");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 500);
        f.setVisible(true);
        MyPanel myPanel = new MyPanel(humanWorld, f);
        Thread thread = new Thread(myPanel);

        f.add(myPanel);
        f.pack();
        f.setVisible(true);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }
    
}

class MyPanel extends JPanel implements Runnable 
{

    Human [][] humanWorld;
    JFrame frame;
    int mousex, mousey;

    public MyPanel(Human[][] humanWorld, JFrame frame) 
    {
        this.humanWorld = humanWorld;
        this.frame = frame;
        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseMoved(MouseEvent e) 
            {
                mousex = e.getX();
                mousey = e.getY();

            }
        });
    }

  @Override
    public Dimension getPreferredSize() 
    {
        return new Dimension(2000, 500);
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float scaleY = getHeight() / humanWorld.length;
        float scaleX = getWidth() / humanWorld[0].length;
        float scale = scaleX < scaleY ? scaleX : scaleY;
        g2d.setColor(Color.gray);
        for (int y = 0; y < humanWorld.length; y++) 
        {
            for (int x = 0; x < humanWorld[0].length; x++)
            {

                g2d.fillRect((int) (x * scale), (int) (y * scale), (int) scale, (int) scale);
                if (humanWorld[y][x] != null) {
                    g2d.drawImage(humanWorld[y][x].getAvatar(), (int) (x * scale),
                            (int) (y * scale), (int) scale, (int) scale, null);

                }

            }


        }

        g2d.setColor(Color.black);
        
        for (int y = 0; y <= humanWorld.length; y++) 
        {
            g2d.drawLine(0, (int) (y * scale), (int) (humanWorld[0].length * scale), (int) (y * scale));
        }
        for (int x = 0; x <= humanWorld[0].length; x++) 
        {
            g2d.drawLine((int) (x * scale), 0, (int) (x * scale), (int) (humanWorld.length * scale));

        }


        //g2d.drawString(String.valueOf(scale), 50, 50);
        g2d.setColor(Color.red);
        for (int y = 0; y < humanWorld.length; y++) 
        {
            for (int x = 0; x < humanWorld[0].length; x++) 
            {

                if (humanWorld[y][x] != null && mousex > (int) (x * scale) &&
                        mousey > (int) (y * scale) && mousex < 
                        (int) ((x + 1) * scale) && mousey < 
                        (int) ((y + 1) * scale)
                        )
                {
                    g2d.drawString(humanWorld[y][x].getLocalName(), mousex, mousey);

                }
            }


        }


    }

    @Override
    public void run() {
        while (true) {
            //System.out.println("Pintou");
            frame.repaint();
            try {
                Thread.sleep(15);


            } catch (InterruptedException ex) {
                Logger.getLogger(MyPanel.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
    