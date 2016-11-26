package umbrellaprogram.movements;

import umbrellaprogram.agents.Human;
import umbrellaprogram.agents.World;

public class StateOneMoves {

    int x, y;
    int view[][];

    public StateOneMoves(Human h) {
        x = h.posX;
        y = h.posY;

        view = new int[5][5];
    }

    public int decision() {
        for (int i = x - 2; i < x + 2; i++) {
            if (i > 0 && i < World.humanWorld.length) {
                for (int j = y + 2; j > y - 2; j--) {
                    if(j > 0 && j < World.humanWorld.length)
                        if(World.humanWorld[i][j] != null && World.humanWorld[i][j].state == 1) view[i%5][j%5] = 1;
                }
            }
        }
        return 1;
    }
}
