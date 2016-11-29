/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.movements;

import java.util.Random;
import umbrellaprogram.agents.Human;
import umbrellaprogram.agents.World;
import umbrellaprogram.behaviour.WorldBehaviour;

/**
 *
 * @author nero
 */
public class StateTwoMoves {

    //a mudanca entre os estados deve acontecer aqui
    int x, y;
    int view[][];
    Human h;

    public StateTwoMoves(Human h, int view[][]) {
        this.h = h;
        x = h.posX;
        y = h.posY;
        this.view = view;
    }

    public class struct {

        public String name;
        public int count, retorno;
    }

    public int decision() {
        int flagR = 0, flagL = 0, flagU = 0, flagD = 0;
        for (int i = 0; i < 5; i++) {
            if (view[i][0] == 1)
                flagU+=2;
            if (view[i][1] == 1)
                flagU+=2;
            if (view[i][0] == 2)
                flagU++;
            if (view[i][1] == 2)
                flagU++;
            if (view[i][4] == 1)
                flagD+=2;
            if (view[i][3] == 1)
                flagD+=2;
            if (view[i][4] == 2)
                flagD++;
            if (view[i][3] == 2)
                flagD++;
        }
        for (int i = 2; i < 3; i++) {
            if (view[0][i] == 1)
                flagL+=2;
            if(view[1][i] == 1)
                flagL+=2;
            if (view[0][i] == 2)
                flagL++;
            if (view[1][i] == 2)
                flagL++;
            if (view[4][i] == 1)
                flagR+=2;
            if (view[3][i] == 1)
                flagR+=2;
            if (view[4][i] == 2)
                flagR++;
            if (view[3][i] == 2) 
                flagR++;
        }

        struct R = new struct();
        R.count = flagR;
        R.name = "R";
        R.retorno = WorldBehaviour.DIRECTION_RIGHT;

        struct L = new struct();
        L.count = flagL;
        L.name = "L";
        L.retorno = WorldBehaviour.DIRECTION_LEFT;

        struct U = new struct();
        U.count = flagU;
        U.name = "U";
        U.retorno = WorldBehaviour.DIRECTION_UP;

        struct D = new struct();
        D.count = flagD;
        D.name = "D";
        D.retorno = WorldBehaviour.DIRECTION_DOWN;

        struct k[] = {R, L, U, D};
        ordena(k);

        int c = 0;
        for (int i = 0; i < k.length; i++) {
            if (k[i].count == k[0].count) {
                c++;
            }
        }

        Random rand = new Random();
        return k[rand.nextInt(c)].retorno;
        /*int retorno = getMax(flagR, flagL, flagU, flagD);

        //AS ATUALIZACOES DE X E Y SERAO DEIXADAS PARA A COMINUCACAO COM
        //A CLASSE WORLDBEHAVIOUR
        switch (retorno) {
            case 0:
                
            case 1:
                return WorldBehaviour.DIRECTION_RIGHT;

            case 2:
                return WorldBehaviour.DIRECTION_LEFT;

            case 3:
                return WorldBehaviour.DIRECTION_UP;

            case 4:
                return WorldBehaviour.DIRECTION_DOWN;
        }

        return WorldBehaviour.DIRECTION_NONE;
    }

    private int getMax(int a, int b, int c, int d) {
        if ((a > b) && (a > c) && (a > d)) {
            return 1;
        } else if ((b > a) && (b > c) && (b > d)) {
            return 2;
        } else if ((c > a) && (c > b) && (c > d)) {
            return 3;
        } else if ((d > a) && (d > b) && (d > c)) {
            return 4;
        }
        return 0;
    }*/

    }
    private void ordena(struct k[]) {
        for (int i = 0; i < k.length; i++) {
            for (int j = i + 1; j < k.length; j++) {
                if (k[j].count > k[i].count) {
                    struct tmp = k[i];
                    k[i] = k[j];
                    k[j] = tmp;
                }
            }
        }
    }
}
