/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.agents;

import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

import umbrellaprogram.behaviour.WorldBehaviour;
/**
 *
 * @author rafael
 */
public class World extends Agent{
    
    //World Dimension = 50
    public static Human[][] humanWorld = new Human[50][50];
    public static long day;
        
    public void setup()
    {
        addBehaviour(new WorldBehaviour(this));
    }
    

    
}
