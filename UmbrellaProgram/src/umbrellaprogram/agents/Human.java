/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.agents;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import umbrellaprogram.behaviour.*;

/**
 *
 * @author rafael
 */
public class Human extends Agent {
    
    public static final boolean HUMAN = true;
    public static final HashMap<String, Image> 
            avatars = new HashMap<String, Image>();

    public int posX, posY;
    public int distancia_visao;
    
    public String name;
    public String mapaVisao[][];
    public HashMap<String, Boolean> relacaoAgentes;
    
    private String imgHuman="res/human.png", imgZombie = "res/zombie.png";
    private Image avatar;

    protected Behaviour behaviour;
    
    Human(String name, int posX, int posY) 
    {
      //throw new UnsupportedOperationException("Not supported yet."); 
      //To change body of generated methods, choose Tools | Templates.
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        
        
        try {
                avatar = ImageIO.read(new File(imgZombie));
                avatars.put(imgZombie, avatar);
            } catch (IOException ex) {
                Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    public Image getAvatar() 
    {
        return avatar;
    }
    
    
    
    @Override
    protected void setup()
    {
        addBehaviour(behaviour);
    }
    
}