/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.behaviour;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.Agent;

import javax.swing.JOptionPane;

/**
 *
 * @author rafael
 */
public class PersonBehaviour extends FSMBehaviour{
       //Define as constantes dos estados
   private static final String ONE_STATE = "EstadoHumano";
   private static final String TWO_STATE = "EstadoZumbi";
   private static final String THREE_STATE = "EstadoCurado";
   private static final String ERROR_STATE= "EstadoZero";
   
   //Define as constantes das transições
   private final int UM = 1;
   private final int DOIS = 2;
   private final int TRES = 3;
   private final int ZERO = 0;
   
   private int transicao=0;
   private String entrada="";
   
   public void PersonBehaviour ()
   {
       
   }
}

