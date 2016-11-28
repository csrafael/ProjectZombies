package umbrellaprogram.movements;

import umbrellaprogram.agents.Human;
import umbrellaprogram.agents.World;
import java.util.Random;
import umbrellaprogram.behaviour.WorldBehaviour;

public class StateOneMoves {

    //a mudanca entre os estados deve acontecer aqui
    int x, y;
    int view[][];
    Human h;

    public StateOneMoves(Human h) {
        this.h = h;
        x = h.posX;
        y = h.posY;
        view = new int[5][5];
    }

    public int decision() {
        int l = 0, c = 0;
        for (int i = x - 2; i < x + 2; i++) {
            if (i > 0 && i < World.humanWorld.length) {
                for (int j = y - 2; j < y + 2; j++) {
                    if(j > 0 && j < World.humanWorld.length)
                        if(World.humanWorld[i][j] != null && (World.humanWorld[i][j].state == 1 
                                || World.humanWorld[i][j].state == 3)) view[l][c] = World.humanWorld[i][j].state;
                        else if(World.humanWorld[i][j] != null && World.humanWorld[i][j].state == 2) view[l][c] = World.humanWorld[i][j].state;
                        else view[l][c] = 0;
                    c++;
                }
            }
            c=0;
            l++;
        }
        int flagR = 0, flagL = 0, flagU = 0, flagD = 0;
        for(int i = 0; i < 5; i++){
            if(view[i][0] == 1 || view[i][1] == 1) flagL++;
            //if(view[i][0] == 2) flagL--;
            if(view[i][4] == 1 && view[i][3] == 1) flagR++;
            //if(view[i][4] == 2) flagR--;
        }
        for(int i = 0; i < 5; i++){
            if(view[0][i] == 1 && view[1][i] == 1) flagU++;
            //if(view[0][i] == 2) flagU--;
            if(view[4][i] == 1 && view[3][i] == 1) flagD++;
            //if(view[4][i] == 2) flagD--;
        }
        int retorno = getMax(flagR, flagL, flagU, flagD);
        
        switch(retorno){
            case 1:
                h.posX++;
                return WorldBehaviour.DIRECTION_RIGHT;
            case 2:
                h.posX--;
                return WorldBehaviour.DIRECTION_LEFT;
            case 3:
                h.posY--;
                return WorldBehaviour.DIRECTION_UP;
            case 4:
                h.posY++;
                return WorldBehaviour.DIRECTION_DOWN;
        }
        
        return WorldBehaviour.DIRECTION_NONE;
    }
    
    private int getMax(int a, int b, int c, int d){
        if((a > b) && (a > c) && (a >d)) return 1;
        else if ((b > a) && (b > c) && (b > d)) return 2;
        else if ((c > a) && (c > b) && (c > d)) return 3;
        else if ((d > a) && (d > b) && (d > c)) return 4;
        return 0;
    }
}
