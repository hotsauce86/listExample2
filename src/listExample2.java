import javax.management.MBeanAttributeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;



/*
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    SAMPLE SCOREBOARD PROGRAM

    This code is based off of a coding challenge where the goal was to create a scoreboard application.

    The challenge required three methods with these specific functions:
        -an 'addscore' method to add a playerID and Score into a list and return the playerID and their scoreAverage
            -the ID and score are both Int, the returned Ave should be float
        -a 'topScore' method that returns a list of the players with the highest average score, the length of the list 'n'
            -similar to 'addscore', the returned values in the created list are the Int playerID, and sorted float Ave
        -a 'clear' method that deletes all the score values of the selected playerID from the main list, while not removing the playerID from the main list
            -this method does not need to return anything

     The challenge could be accomplished in several different languages, I picked Java. Now when I first started this project, my first
     idea was to use a matrix array[][], but that limits its maximum size and scores that it could store. The better way was to use lists.

     In order to maintain a list that keeps the scores and playerID together, we need to create a class that we can use to create
     objects that can store individual inputs of playerID and their Score. We can loop thought the list of scores, know as the 'scoreBoard', to find the average when we need to
     compute the output. But, the object we are making to store these individual scores use only Int values, so we need another class to create objects
     for a list that can hold float values; these objects will be for the 'topScores'. We also need a way to 'sort' these values, just to make it easier to find the sums for the average and to sort
     the highest average for the 'topscore' method.

     A few things I learned making this project
        -There was no way I could write all this in a hour
        -ArrayLists are pretty useful when you need to manage objects
        -the sorting method for float values should not work, but does anyway
        -I expected Kanye to drop his new album by now

     The real lesson was learning to use array lists, adding a check method so the placeholder values get replaced once a new score is added, using sorted lists to quickly find and insert averages into a list, and learning the many ways stackoverflow tells me I'm wrong and that this was the best way to sort items in a list (the method uses int values but still works for floats, somehow)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 */
public class listExample2 {

    static ArrayList<scoreBoard> mainScoreBoard =  new ArrayList<>();
    //static ArrayList<playerGames> topgames = new ArrayList<>();

    /*
        WARNING!!!
        This project only works on a few expectations:
            -there is at least one player
            -playerID cannot be negative
            -there are no gaps between playerID '1' -> playerID 'n'
            -all scores are positive int values only
                -scores are not negative unless they are a placeholder (-1)
     */
    public static void main (String[] args){

        /*
            TEST CASES
         */

        addscore(1,2);
        addscore(2,3);
        addscore(2,4);
        addscore(3,1);

        addscore(2,1);
        addscore(3,5);
        addscore(4,2);
        addscore(1,1);
        addscore(2,4);
        addscore(4,6);
        addscore(5,1);
        addscore(7,0);//out of order
        addscore(6,0);//but still 1->n

        topScore(3);

        clearPlayer(3);
        clearPlayer(3);//cleared twice

        topScore(3);

        addscore(3,1);
        clearPlayer(4);
        clearPlayer(5);

        addscore(7,1);

        topScore(7);    // does not work if n is greater than the # of players

        top2Diff();

        System.out.println("End of program");
    }



    /*
        ADDSCORE

        this method takes a playerID and score, adds it into the list of scores,
        the adds all the scores of that player and divides it by the number of
        unique scores to return the average.

        if the player had their scores deleted and is adding a new score, the placeholder score
        should be removes as not to throw off the average that gets returned
     */
    public static void addscore(int a, int b){

        removePlaceHolder(a);
        //create a new object
        scoreBoard newScore = new scoreBoard(a, b);
        //add it to the scoreboard list
        mainScoreBoard.add(newScore);
        //start counter for average
        int gameCounter=0;
        int scoreSum=0;
        float ave = 0;      //the float value of = scoreSum/gameCounter

        //Iterator<scoreBoard> aveit = mainScoreBoard.iterator();
        for(int i=0; i<mainScoreBoard.size(); i++){
            if(mainScoreBoard.get(i).getPlayerID() == a){
                gameCounter++;
                scoreSum += mainScoreBoard.get(i).getScore();
            }
        }
        ave = (float) scoreSum/gameCounter;
        System.out.println("scoreboard size = " + mainScoreBoard.size());
        System.out.println("player ID: " + a + ", Score: "+ ave);

        //playerGames somescore = new playerGames(a, ave);
        //topgames.add(somescore);

    }



    /*
        TOPSCORE

        this method creates an empty list, adds all the players and scores, creates
        an average for each player like above in the 'addScore', sorts it by highest average,
        THEN returns the top 'n' results based on the value 'counter' has
     */
    public static ArrayList<playerGames> topScore(int counter){

        //list to store sorted scores
        ArrayList<scoreBoard> sortedBoard = new ArrayList<>();
        sortedBoard = mainScoreBoard;
        //returns a list of player id and their ave
        ArrayList<playerGames> tops = new ArrayList<>();

        Collections.sort(sortedBoard, new sorter());

        int scoreSum=0;
        int gameDiv=0;
        int lastID=1;

        /*
            The sorter:
                This is why we need 1->n playerID as a gap could count a player twice.
                The loop starts with player '1' and goes to the end of the list until
                it reaches the end. it keeps adding the sum of scores and counting
                games until it reaches a new player or it hits the end of the list
                in which case it then computes the average and stores the values
                into the list
         */
        for(int i=0; i<sortedBoard.size(); i++){
            if(sortedBoard.get(i).getPlayerID() != lastID){
                //we add the last playerID and their ave score
                playerGames ranker = new playerGames(sortedBoard.get(i-1).getPlayerID(), (float) scoreSum/gameDiv);
                tops.add(ranker);
                //we don't stop yet, so we reset the score sum and the game divider and increment the playerID
                scoreSum=sortedBoard.get(i).getScore();
                gameDiv=1;
                lastID++;
            }
            else{
                //we continue to add to the sum and game counter
                scoreSum += sortedBoard.get(i).getScore();
                gameDiv++;
            }
            if(i == sortedBoard.size()-1){
                //we are at the end of the list and the playerID won't change, so we compute the score ave here
                playerGames ranker = new playerGames(sortedBoard.get(i).getPlayerID(), (float) scoreSum/gameDiv);
                tops.add(ranker);
                //scoreSum=sortedBoard.get(i).getScore();
               // gameDiv=1;
               // lastID++;
            }
            System.out.println("SORTED BOARD: "+ sortedBoard.get(i).getPlayerID()+ " " + sortedBoard.get(i).getScore());
        }

        //we sort the list by the highest average score
        Collections.sort(tops, new sorterTops());

        //this it just to test what is in the entire list
        System.out.println("/////////////CURRENT STATE OF TOPS///////////////");
        for(int i =0; i < tops.size(); i ++){
            System.out.println(tops.get(i).getPlayerID()+ " " +tops.get(i).getGameTotal());
        }
        //this is the proper output with 'n' results
        System.out.println("///////////////////////////HERE ARE THE RETURNED RESULTS WITH COUNTER SIZE/////////////////////////////");
        for(int i =0; i < counter; i ++){
            System.out.println(tops.get(i).getPlayerID()+ " " +tops.get(i).getGameTotal());
        }

        System.out.println("////end of sorted tops////");

        return tops;

    }



    /*
        CLEARPLAYER

        this method finds all player values in the scoreboard list and removes all of them,
        but must leave one entry with null values so that the player can still appear in the
        scoreboard (just without any scores)
     */
    public static void clearPlayer(int playerToRemove){
        //need to iterate through the main scoreboard
        Iterator<scoreBoard> clearplayer = mainScoreBoard.iterator();
        while(clearplayer.hasNext()){
            Object o = clearplayer.next();
            //we remove the score if we find the same playerID
            if (((scoreBoard) o).getPlayerID()== playerToRemove){
                clearplayer.remove();
            }
        }
        //we need to add a placeholder score so they can still show up in the results
        //and also not break the topScore sorting method
         scoreBoard addempty = new scoreBoard(playerToRemove, -1);
        mainScoreBoard.add(addempty);

    }



    /*
        REMOVEPLACEHOLDER

        since we need to make sure when we account for player scores that the average should be properly valued,
        we need to remove the placeholder '-1' when we add in a new score.

        this method checks to make sure it only removes the player in question and not all of the potential
        players who may have had their record cleared
     */
    public static void removePlaceHolder(int a){
        Iterator<scoreBoard> clearplayer = mainScoreBoard.iterator();
        while (clearplayer.hasNext()) {
            Object o = clearplayer.next();
            if (((scoreBoard) o).getScore() == -1 && ((scoreBoard) o).getPlayerID() == a) {
                System.out.println("removed placeholder:  " + ((scoreBoard) o).getPlayerID());
                clearplayer.remove();

            }
        }

    }

    /*
        The topDiff finds the positive difference between the highest score and the second highest score.

     */
    public static void top2Diff(){
        ArrayList<playerGames> difference = new ArrayList<>();

        difference = topScore(0);
        float x=0;
        x = difference.get(0).getGameTotal()-difference.get(1).getGameTotal();
        System.out.println("Difference: +" + x);

    }

}
