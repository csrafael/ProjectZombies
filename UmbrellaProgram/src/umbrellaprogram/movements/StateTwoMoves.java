/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umbrellaprogram.movements;

import java.util.Random;
import umbrellaprogram.agents.Human;
import umbrellaprogram.agents.World;
import umbrellaprogram.behaviour.PersonBehaviour;
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
            if (view[i][4] == 1)
                flagD+=2;
            if (view[i][3] == 1)
                flagD+=2;
        }
        for (int i = 2; i < 3; i++) {
            if (view[0][i] == 1)
                flagL+=2;
            if(view[1][i] == 1)
                flagL+=2;
            if (view[4][i] == 1)
                flagR+=2;
            if (view[3][i] == 1)
                flagR+=2;
        }
        
        Double probability = Math.random();
        
        float DIE = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (view[i][j] == 1 || view[i][j] == 3) {
                    DIE++;
                }
            }
        }
        
        for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    if (view[i][j] == 1) {
                        if (probability < 0.6) {
                            h.state = 4;
                            h.transition = PersonBehaviour.ZUMBIMORTO;
                            System.out.println("YEAH, BITCH!");
                            return WorldBehaviour.DIRECTION_NONE;
                        }
                    }
                }
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
