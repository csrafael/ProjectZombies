package umbrellaprogram.movements;

import umbrellaprogram.agents.Human;
import umbrellaprogram.agents.World;

public class StateOneMoves {

    int x, y;
    String view[][];

    public StateOneMoves(Human h) {
        x = h.posX;
        y = h.posY;

        view = new String[5][5];

        for (int i = x - 2; i < x + 2; i++) {
            if (i > 0 && i < World.humanWorld.length) {
                for (int j = y + 2; j > y - 2; j--) {
                    if(j > 0 && j < World.humanWorld.length);
                        //view[i][j] = World.humanWorld[i][j].name;
                }
            }
        }
    }

    public int decision() {
        return 1;
    }
}
