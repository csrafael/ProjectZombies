package umbrellaprogram.movements;

import umbrellaprogram.agents.Human;
import umbrellaprogram.agents.World;
import java.util.Random;
import umbrellaprogram.behaviour.PersonBehaviour;
import umbrellaprogram.behaviour.WorldBehaviour;

public class StateOneMoves {

    //a mudanca entre os estados deve acontecer aqui
    int x, y;
    int view[][];
    Human h;
    public static final int AGRUPAMENTO = 1;
    public static final int FUGA = 10;

    public class struct {

        public String name;
        public int count, retorno;
    }

    public StateOneMoves(Human h, int view[][]) {
        this.h = h;
        x = h.posX;
        y = h.posY;
        this.view = view;
    }

    public int decision() {

        int flagR = 0, flagL = 0, flagU = 0, flagD = 0;
        
        Double probability = Math.random();
        
        for(int i = 1; i < 4; i++)
            for(int j = 1; j < 4; j++)
                if(view[i][j] == 2 && probability < 0.1){
                    h.state = 2;
                    h.transition = PersonBehaviour.INFECTADO;
                    return WorldBehaviour.DIRECTION_NONE;
                }
        
        for (int i = 0; i < 5; i++) {
            if (view[i][0] == 1 || view[i][0] == 3) {
                flagU += AGRUPAMENTO;
            }
            if (view[i][1] == 1 || view[i][1] == 3) {
                flagU += AGRUPAMENTO;
            }
            if (view[i][0] == 2 || view[i][0] == 4) {
                flagU -= FUGA;
                flagD += FUGA;
            }
            if (view[i][1] == 2 || view[i][1] == 4) {
                flagU -= FUGA;
                flagD += FUGA;
            }
            if (view[i][4] == 1 || view[i][4] == 3) {
                flagD += AGRUPAMENTO;
            }
            if (view[i][3] == 1 || view[i][3] == 3) {
                flagD += AGRUPAMENTO;
            }
            if (view[i][4] == 2 || view[i][4] == 4) {
                flagD -= FUGA;
                flagU += FUGA;
            }
            if (view[i][3] == 2 || view[i][3] == 4) {
                flagD -= FUGA;
                flagU += FUGA;
            }
        }
        for (int i = 2; i < 3; i++) {
            if (view[0][i] == 1 ||view[0][i] == 3) {
                flagL += AGRUPAMENTO;
            }
            if (view[1][i] == 1||view[1][i] == 3) {
                flagL += AGRUPAMENTO;
            }
            if (view[0][i] == 2||view[0][i] == 4) {
                flagL -= FUGA;
                flagR += FUGA;
            }
            if (view[1][i] == 2||view[1][i] == 4) {
                flagL -= FUGA;
                flagR += FUGA;
            }
            if (view[4][i] == 1||view[4][i] == 3) {
                flagR += AGRUPAMENTO;
            }
            if (view[3][i] == 1||view[3][i] == 3) {
                flagR += AGRUPAMENTO;
            }
            if (view[4][i] == 2||view[4][i] == 4) {
                flagR -= FUGA;
                flagL += FUGA;
            }
            if (view[3][i] == 2||view[3][i] == 4) {
                flagR -= FUGA;
                flagL += FUGA;
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
        int retorno = k[rand.nextInt(c)].retorno;
        return retorno;
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
