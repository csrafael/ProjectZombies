/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.behaviour;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.lang.reflect.InvocationTargetException;

import umbrellaprogram.agents.World;

/**
 *
 * @author rafael
 */
public class WorldBehaviour extends CyclicBehaviour {
    
    /**
     *
     * @param agent
     */
    public WorldBehaviour(Agent agent)
    {
        super(agent);
    }
    public void action()
    {
        sendMsg();
        receivingMsg();
        
        World.day++;
    }
    
    public void receivingMsg ()
    {
        
    }
    
    public void sendMsg()
    {
        
    }
}
