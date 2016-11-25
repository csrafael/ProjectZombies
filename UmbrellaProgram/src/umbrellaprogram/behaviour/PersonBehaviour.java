/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.behaviour;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import umbrellaprogram.agents.Human;
import javax.swing.JOptionPane;
import static umbrellaprogram.behaviour.WorldBehaviour.*;

/**
 *
 * @author rafael
 */
public class PersonBehaviour extends FSMBehaviour {
    //Define as constantes dos estados

    private static final String FIRST_STATE = "EstadoHumano";
    private static final String SECOND_STATE = "EstadoZumbi";
    private static final String THIRD_STATE = "EstadoImune";
    private static final String FOURTH_STATE = "EstadoMorto";
    private static final String ERROR_STATE = "EstadoZero";

    //Define as constantes das transições
    Human human;
    private final int UM = 1;
    private final int DOIS = 2;
    private final int TRES = 3;
    private final int QUATRO = 4;
    private final int ZERO = 0;

    private int transicao = 0;
    private String entrada = "";

    public PersonBehaviour(Human h) {
        super(h);
        human = h;
    }

    public void onStart() {
        registerFirstState(new FirstState(), FIRST_STATE);
        registerState(new SecondState(), SECOND_STATE);
        registerState(new ThirdState(), THIRD_STATE);
        registerState(new FourthState(), FOURTH_STATE);
        registerState(new ErrorState(), ERROR_STATE);

        registerTransition(FIRST_STATE, SECOND_STATE, UM);
        registerTransition(FIRST_STATE, THIRD_STATE, DOIS);
        registerTransition(FIRST_STATE, FOURTH_STATE, TRES);
        registerTransition(SECOND_STATE, FOURTH_STATE, QUATRO);

        registerDefaultTransition(FIRST_STATE, ERROR_STATE);
        registerDefaultTransition(SECOND_STATE, ERROR_STATE);
        registerDefaultTransition(THIRD_STATE, ERROR_STATE);
        registerDefaultTransition(FOURTH_STATE, ERROR_STATE);
    }

    private class FirstState extends Behaviour {

        int c = 0;
        public void action() {
            enviaMsg(DIRECTION_UP);
            try{
                Thread.sleep(5000L);
            }catch(Exception e){System.out.println(e.getStackTrace());}
        }

        public boolean done() {
            return c > 10;
        }

        private void enviaMsg(int direcao) {
            ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);

            //Preencher os campos necesários da mensagem
            mensagem.setSender(myAgent.getAID());
            mensagem.addReceiver(new AID("World", AID.ISLOCALNAME));
            mensagem.setContent(Integer.toString(direcao));
            myAgent.send(mensagem);

        }
    }

    private class SecondState extends Behaviour {

        public void action() {

        }

        public boolean done() {
            return false;
        }
    }

    private class ThirdState extends Behaviour {

        public void action() {

        }

        public boolean done() {
            return false;
        }
    }

    private class FourthState extends Behaviour {

        public void action() {

        }

        public boolean done() {
            return false;
        }
    }

    private class ErrorState extends Behaviour {

        public void action() {

        }

        public boolean done() {
            return false;
        }
    }
}
