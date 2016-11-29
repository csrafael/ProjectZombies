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
    
    public static final HashMap<String, Image> avatars = new HashMap<String, Image>();

    public int posX, posY;
    public int distancia_visao;
    
    public String name;
    public int state, transition;
    public int mapaVisao[][];
    
    public String imgHuman="res/human.png", imgZombie = "res/zombie.png",imgHealed = "res/healed.png";
    public  Image avatar;
    protected Behaviour behaviour;
    
    public Human(String name, int posX, int posY, int state) 
    {
        this.transition = 0;
        distancia_visao = 2;
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.state = state;
        mapaVisao = new int[distancia_visao * 2 + 1][distancia_visao * 2 + 1];
        switch (state) {
            case 1:
                try {
                    avatar = ImageIO.read(new File(imgHuman));
                    avatars.put(imgHuman, avatar);
                } catch (IOException ex) {
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case 2:
                try {
                    avatar = ImageIO.read(new File(imgZombie));
                    avatars.put(imgZombie, avatar);
                } catch (IOException ex) {
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            case 3:
                try {
                    avatar = ImageIO.read(new File(imgHealed));
                    avatars.put(imgHealed, avatar);
                } catch (IOException ex) {
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                }   break;
            default:
                break;
        }
    }
    
    public Image getAvatar() 
    {
        return avatar;
    }
    
    
    
    @Override
    protected void setup()
    {
        PersonBehaviour pb = new PersonBehaviour(this);
        addBehaviour(pb);
    }
    
}
