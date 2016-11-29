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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import umbrellaprogram.agents.World;
import umbrellaprogram.agents.World.PosicaoPP;

/**
 *
 * @author rafael
 */
public class WorldBehaviour extends CyclicBehaviour {

    public static final int DIRECTION_NONE = 0;
    public static final int DIRECTION_UP = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;
    public static final int DIRECTION_RIGHT = 4;
    public static final String[] DIRECOES = {"NONE", "UP", "DOWN", "LEFT", "RIGHT"};

    LinkedList<ACLMessage> mensagensRecebidas;
    HashMap<AID, Boolean> decisoes;
    long time;

    public WorldBehaviour(Agent agent) {
        super(agent);
        mensagensRecebidas = new LinkedList<ACLMessage>();

        decisoes = new HashMap<AID, Boolean>();

        for (String name : World.listaPosicoes.keySet()) {
            decisoes.put(new AID(name, true), false);
        }
        time = System.currentTimeMillis();
    }

    public void action() {
        
        //TIME E SENDMSG SE TORNARAM OBSOLETOS
        
        //time = System.currentTimeMillis();
        //sendMsg();
        receivingMsg();
        makeDecision();

        try {
            //IRRELEVANTE, 100L EH O BASTANTE PARA O MUNDO DAR "REFRESH"
          //  int timeToSleep = (int) (400L - System.currentTimeMillis() + time);

            //if (timeToSleep > 0) {
                //System.out.printf("timeToSleep %d\n", timeToSleep);
                Thread.sleep(100L);
            //}
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        World.day++;
    }

    
    //GOODBYE, MOTHER FUCKER
    /*public void sendMsg() {
        for (Entry<String, PosicaoPP> nome_pos : World.listaPosicoes.entrySet()) {

            PosicaoPP posicaoPP = nome_pos.getValue();
            int centerX = posicaoPP.posX;
            int centerY = posicaoPP.posY;
            // Criação do objeto ACLMessage
            ACLMessage mensagem = new ACLMessage(ACLMessage.INFORM);

            //Preencher os campos necesários da mensagem
            mensagem.setSender(myAgent.getAID());

            //getKey retorna K em Entry<K,V>
            AID receiver = new AID(nome_pos.getKey(), AID.ISLOCALNAME);
            mensagem.addReceiver(receiver);

            StringBuilder sb = new StringBuilder();
            sb.append("moveu=").append(decisoes.get(receiver)).append("\n");
            for (int y = 0; y < posicaoPP.hz.distancia_visao * 2 + 1; y++) {
                for (int x = 0; x < posicaoPP.hz.distancia_visao * 2 + 1; x++) {
                    int posRealY = centerY - posicaoPP.hz.distancia_visao + y;
                    int posRealX = centerX - posicaoPP.hz.distancia_visao + x;
                    if (posRealY < 0 || posRealX < 0
                            || posRealY >= World.humanWorld.length
                            || posRealX >= World.humanWorld[0].length) {
                        sb.append("1");
                    } else if (World.humanWorld[posRealY][posRealX] == null) {
                        sb.append("0");
                    } else {
                        sb.append(
                                World.humanWorld[posRealY][posRealX].getClass().getName());
                    }
                    sb.append(",");
                }
                sb.append("\n");
            }

            mensagem.setContent(sb.toString());
            myAgent.send(mensagem);
        }
    }*/

    public void receivingMsg() {
        for (int numMsg = 0; numMsg < World.listaPosicoes.size(); numMsg++) {
            ACLMessage mensagem = myAgent.blockingReceive();
            mensagensRecebidas.add(mensagem);
        }
    }

    private void makeDecision() {
        decisoes.clear();
        Collections.shuffle(mensagensRecebidas);
        
        while (mensagensRecebidas.size() > 0) {
            ACLMessage mensagem = mensagensRecebidas.poll();
            int direcao = Integer.parseInt(mensagem.getContent());
            AID sender = mensagem.getSender();

            PosicaoPP pp = World.listaPosicoes.get(sender.getLocalName());
            //ignorar mensagens de agentes mortos;
            if (pp == null) {
                continue;
            }
            int y = pp.posY, x = pp.posX;
            if (y == World.humanWorld.length || x == World.humanWorld.length) {
                System.out.println("1 - DEBUG HERE");
            }
            int novoX = x;
            int novoY = y;
            if (direcao == DIRECTION_UP) {
                novoY--;
            } else if (direcao == DIRECTION_DOWN) {
                novoY++;
            } else if (direcao == DIRECTION_RIGHT) {
                novoX++;
            } else if (direcao == DIRECTION_LEFT) {
                novoX--;
            }

            if (novoY < 0 || novoY >= World.humanWorld.length || novoX < 0 || novoX >= World.humanWorld[0].length) {
                //IT'S NOT A BUG, IT'S A FEATURE!!
                //System.out.println("10 - DEBUG HERE");
                decisoes.put(sender, Boolean.FALSE);
            } else if (World.humanWorld[novoY][novoX] == null) {
                World.humanWorld[novoY][novoX] = World.humanWorld[y][x];
                World.humanWorld[y][x] = null;
                pp.posX = novoX;
                pp.posY = novoY;
                decisoes.put(sender, Boolean.TRUE);
            } else {
                decisoes.put(sender, Boolean.FALSE);
            }

        }

    }
}
