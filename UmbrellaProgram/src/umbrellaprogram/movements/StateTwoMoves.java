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

    public StateTwoMoves(Human h) {
        this.h = h;
        x = h.posX;
        y = h.posY;
        view = new int[5][5];
    }

    public int decision() {
        int l = 0, c = 0;
        for (int i = x - 2; i <= x + 2; i++) {
            if (i > 0 && i < World.humanWorld.length) {
                for (int j = y - 2; j <= y + 2; j++) {
                    if (j > 0 && j < World.humanWorld.length) {
                        if (World.humanWorld[i][j] != null) {
                            view[l][c] = World.humanWorld[i][j].state;
                        } else {
                            view[l][c] = 0;
                        }
                    }
                    c++;
                }
            }
            c = 0;
            l++;
        }
        int flagR = 0, flagL = 0, flagU = 0, flagD = 0;
        for (int i = 0; i < 5; i++) {
            if (view[i][0] == 1 || view[i][1] == 1) {
                flagL += 2;
            }
            if (view[i][0] == 2 || view[i][1] == 2) {
                flagL++;
            }
            if (view[i][4] == 1 || view[i][3] == 1) {
                flagR += 2;
            }
            if (view[i][4] == 2 || view[i][3] == 2) {
                flagR++;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (view[0][i] == 1 || view[1][i] == 1) {
                flagU += 2;
            }
            if (view[0][i] == 2 || view[1][i] == 2) {
                flagU++;
            }
            if (view[4][i] == 1 || view[3][i] == 1) {
                flagD += 2;
            }
            if (view[4][i] == 2 || view[3][i] == 2) {
                flagD++;
            }
        }
        int retorno = getMax(flagR, flagL, flagU, flagD);

        //AS ATUALIZACOES DE X E Y SERAO DEIXADAS PARA A COMINUCACAO COM
        //A CLASSE WORLDBEHAVIOUR
        switch (retorno) {
            case 0:
                Random rnd = new Random();
                int move = rnd.nextInt(4) + 1;
                switch (move) {
                    case WorldBehaviour.DIRECTION_RIGHT:
                        return WorldBehaviour.DIRECTION_RIGHT;

                    case WorldBehaviour.DIRECTION_LEFT:
                        return WorldBehaviour.DIRECTION_LEFT;

                    case WorldBehaviour.DIRECTION_UP:
                        return WorldBehaviour.DIRECTION_UP;

                    case WorldBehaviour.DIRECTION_DOWN:
                        return WorldBehaviour.DIRECTION_DOWN;

                }
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
    }

}
