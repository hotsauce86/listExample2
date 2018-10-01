import java.util.Comparator;



import java.util.Comparator;

public class sorterTops implements Comparator<playerGames> {


    @Override
    public int compare(playerGames o1, playerGames o2) {
        return Float.compare(o2.getGameTotal(), o1.getGameTotal());
    }

}
