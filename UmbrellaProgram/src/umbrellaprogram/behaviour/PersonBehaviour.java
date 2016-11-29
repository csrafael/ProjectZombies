/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.behaviour;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import umbrellaprogram.agents.Human;
import static umbrellaprogram.behaviour.WorldBehaviour.*;
import umbrellaprogram.agents.World.PosicaoPP;
import umbrellaprogram.movements.*;
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
    PosicaoPP position;
    private final int UM = 1;
    private final int DOIS = 2;
    private final int TRES = 3;
    private final int QUATRO = 4;
    private final int CINCO = 5;
    private final int ZERO = 0;

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
        registerTransition(FOURTH_STATE, FIRST_STATE, CINCO);

        registerDefaultTransition(FIRST_STATE, ERROR_STATE);
        registerDefaultTransition(SECOND_STATE, ERROR_STATE);
        registerDefaultTransition(THIRD_STATE, ERROR_STATE);
        registerDefaultTransition(FOURTH_STATE, ERROR_STATE);
    }
    
    public void enviaMsg(int direcao) {
            ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);

            //Preencher os campos necesários da mensagem
            mensagem.setSender(myAgent.getAID());
            mensagem.addReceiver(new AID("World", AID.ISLOCALNAME));
            mensagem.setContent(Integer.toString(direcao));
            myAgent.send(mensagem);
    }
    
    
    //DESNECESARIO
    
    
    
    /*void recebeMsg() {
        ACLMessage mensagem = myAgent.blockingReceive();
        String msgRecebida = mensagem.getContent();

        //Atualizando as crencas
        String[] linhasMsgRecebida = msgRecebida.split("\n");
        //pos 0 indica se moveu ou nao

        if (linhasMsgRecebida[0].endsWith("false")) {
            direcaoTurnoAnterior = DIRECTION_NONE;
        } else {
            atualizaPosicaoInterna(direcaoTurnoAnterior);
        }
        for (int y = 1; y < linhasMsgRecebida.length; y++) {
            String[] colunasDeUmaLinha = linhasMsgRecebida[y].split(",");
            System.arraycopy(colunasDeUmaLinha, 0,
                    agentPP.mapaVisao[y - 1],
                    0, colunasDeUmaLinha.length);
        }

    }*/

    private class FirstState extends Behaviour {

        public void action() {
            human.state = 1;
            StateOneMoves movesLikeJagger = new StateOneMoves(human);
            //alterar nome dos agentes de Pessoa para - Zumbi/Curado
            //necessario nos StateMoves
            int x = human.posX, y = human.posY;
            enviaMsg(movesLikeJagger.decision());
            //receiveMsg();
            try{
                Thread.sleep(50L);
            }catch(Exception e){System.out.println(e.getStackTrace());}
        }

        
        public int onEnd(){
            return human.transition;
        }
        public boolean done() {
            return human.state != 1;
        }
    }

    private class SecondState extends Behaviour {

        public void action() {
            human.state = 2;
            StateOneMoves movesLikeJagger = new StateOneMoves(human);
            enviaMsg(movesLikeJagger.decision());
            try{
                Thread.sleep(500L);
            }catch(Exception e){System.out.println(e.getStackTrace());}
        }

        public int onEnd(){
            return human.transition;
        }
        
        public boolean done() {
            return human.state != 2;
        }
    }

    private class ThirdState extends Behaviour {

        public void action() {

        }

        public int onEnd(){
            return human.transition;
        }
        
        public boolean done() {
            return human.state != 3;
        }
    }

    private class FourthState extends Behaviour {

        public void action() {

        }

        public int onEnd(){
            return human.transition;
        }
        
        public boolean done() {
            return human.state != 4;
        }
    }

    
    //sem ideias pra essa crianca
    private class ErrorState extends Behaviour {

        public void action() {

            System.out.println("Houve um erro na transicao de estados");
            System.out.println(super.getAgent().getAgentState());
        }
        
        public boolean done() {
            return true;
        }
    }
}
