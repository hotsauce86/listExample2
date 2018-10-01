import java.util.Comparator;



        import java.util.Comparator;

public class sorter implements Comparator<scoreBoard> {


    @Override
    public int compare(scoreBoard o1, scoreBoard o2) {
        return o1.getPlayerID() - o2.getPlayerID();
    }

}
