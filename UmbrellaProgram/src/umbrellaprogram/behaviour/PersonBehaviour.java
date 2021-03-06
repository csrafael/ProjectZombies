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
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import umbrellaprogram.agents.Human;
import static umbrellaprogram.agents.Human.avatars;
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
    private int direcaoTurnoAnterior;

    //Define as constantes das transições
    Human human;
    PosicaoPP position;
    int countTres, countQuatro;
    public static final int INFECTADO = 1;
    public static final int CURADO = 2;
    public static final int ZUMBIMORTO = 4;
    public static final int NASCEU = 5;
    public static final int FIMDACURA = 6;
    public static final int ZERO = 0;

    
    
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

        registerTransition(FIRST_STATE, SECOND_STATE, INFECTADO);
        registerTransition(FIRST_STATE, THIRD_STATE, CURADO);
        registerTransition(SECOND_STATE, FOURTH_STATE, ZUMBIMORTO);
        registerTransition(FOURTH_STATE, FIRST_STATE, NASCEU);
        registerTransition(THIRD_STATE,FIRST_STATE,FIMDACURA);

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
    void atualizaPosicaoInterna(int mov){
        direcaoTurnoAnterior = mov;
        switch(mov){
            case WorldBehaviour.DIRECTION_UP:
                human.posY--;
            case WorldBehaviour.DIRECTION_DOWN:
                human.posY++;
            case WorldBehaviour.DIRECTION_RIGHT:
                human.posX++;
            case WorldBehaviour.DIRECTION_LEFT:
                human.posX--;
        }
    }
    
    void recebeMsg() {
        ACLMessage mensagem = myAgent.blockingReceive();
        String msgRecebida = mensagem.getContent();
        String[] linhasMsgRecebida = msgRecebida.split("\n");

        if (linhasMsgRecebida[0].endsWith("false")) {
            direcaoTurnoAnterior = DIRECTION_NONE;
        }else {
            atualizaPosicaoInterna(direcaoTurnoAnterior);
        }
        for (int y = 1; y < linhasMsgRecebida.length; y++) {
            String[] colunasDeUmaLinha = linhasMsgRecebida[y].split(",");
            for(int x = 0; x < 5; x++){
                human.mapaVisao[x][y-1] = Integer.parseInt(colunasDeUmaLinha[x]);
            }
        }
    }

    private class FirstState extends Behaviour {

        public void action() {
            Double probability = Math.random();
            recebeMsg();
            human.state = 1;
            
                        
            if (human.state==1)
            {    
                try {
                    human.avatar = ImageIO.read(new File(human.imgHuman));
                    avatars.put(human.imgHuman, human.avatar);
                } catch (IOException ex) {
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            StateOneMoves movesLikeJagger = new StateOneMoves(human, human.mapaVisao);
            enviaMsg(movesLikeJagger.decision());
            
            
             if (probability<0.001){
                human.transition=INFECTADO;
                human.state=2;
             }
             else if (probability>0.999){
                 System.out.println("passei por aqui - Curado "+probability);
                 human.transition=CURADO;
                 human.state=3;
             }
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
            recebeMsg();
            StateTwoMoves movesLikeJagger = new StateTwoMoves(human, human.mapaVisao);
            enviaMsg(movesLikeJagger.decision());
            
            if (human.state==2)
            {    
                try {
                    human.avatar = ImageIO.read(new File(human.imgZombie));
                    avatars.put(human.imgZombie, human.avatar);
                } catch (IOException ex) {
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        public int onEnd(){
            return human.transition;
        }
        
        public boolean done() {
            return human.state != 2;
        }
    }

    private class ThirdState extends Behaviour {

        public void onStart(){
            countTres = 0;
        }
        public void action() {
            recebeMsg();
            StateOneMoves movesLikeJagger = new StateOneMoves(human, human.mapaVisao);
             enviaMsg(movesLikeJagger.decision());
            human.state = 3;
            if (human.state==3)
            {    
                try {
                    human.avatar = ImageIO.read(new File(human.imgHealed));
                    avatars.put(human.imgHealed, human.avatar);
                } catch (IOException ex) {
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             
            countTres++;
            if(countTres>10){
                human.state=1;
                human.transition=FIMDACURA;
            }
        }

        
        public int onEnd(){
            return human.transition;
        }
        
        public boolean done() {
            return human.state != 3;
        }
    }

    private class FourthState extends Behaviour {

        public void onStart(){
            countQuatro = 0;
        }
        
        public void action() 
        {
            recebeMsg();
            enviaMsg(WorldBehaviour.DIRECTION_NONE);
            human.state=4;
            
            if (human.state==4)
            {    
                try {
                    human.avatar = ImageIO.read(new File(human.imgDead));
                    avatars.put(human.imgDead, human.avatar);
                } catch (IOException ex) {
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                       
            countQuatro++;
            if(countQuatro > 100){
                human.state=1;
                human.transition=NASCEU;
            }
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
