/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.principal;


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


import umbrellaprogram.agents.*;

/**
 *
 * @author rafael
 */
public class UmbrellaProgram {

    static ContainerController containerController;
    static AgentController agentController;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        startMainContainer("127.0.0.1", Profile.LOCAL_PORT, "UFABC");
        //adicionando agente
        //SINTAXE: addAgent(container, nome_do_agente, classe, parametros de inicializacao)
        addAgent(containerController, "World", World.class.getName(), null );
        
        
        
       // addAgent(containerController, "rma", "jade.tools.rma.rma", null);
        
        addAgent(containerController, "Sniffer", "jade.tools.sniffer.Sniffer", 
                                       new Object[]{"World", ";", "Human"});
    }
    
    public static void startMainContainer(String host, String port, String name) {
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, host);
        profile.setParameter(Profile.MAIN_PORT, port);
        profile.setParameter(Profile.PLATFORM_ID, name);
        
        containerController = runtime.createMainContainer(profile);
    }

    public static void addAgent(ContainerController cc, String agent, String classe, Object[] args) {
        try {
            //agentController = cc.createNewAgent(agent, classe, args);
            agentController = cc.createNewAgent(agent, classe, args);
            agentController.start();
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }
     public static void addExistingAgent(ContainerController cc,
            String nickname, Agent agent) 
     {
        //long time = System.currentTimeMillis();
        
        try 
        {
            //agentController = cc.createNewAgent(agent, classe, args);
            agentController = cc.acceptNewAgent(nickname, agent);
            
            agentController.start();
            //System.out.printf("Time to add agent: %d\n", System.currentTimeMillis() - time);
        } catch (StaleProxyException s) 
        {
            System.out.println("error!");
            s.printStackTrace();
        }
        //System.out.printf("agente adicionado: %d\n", System.currentTimeMillis() - time);
    }
}
