package ui;

import euromillions.CuponEuromillions;
import euromillions.Dip;
import euromillions.EuromillionsDraw;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;

public class DemoMain {
    private static MyLogger logger = new MyLogger();

    /**
     * demonstrates a client for ramdom euromillions bets
     */
    public static void main(String[] args) {

        // played sheet
        CuponEuromillions thisWeek = new CuponEuromillions();
        logger.log("Betting with three random bets...");
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());
        thisWeek.addDipToCuppon(Dip.generateRandomDip());

        // simulate a random draw
        EuromillionsDraw draw = EuromillionsDraw.generateRandomDraw();

        //report results
        logger.log("You played:");
        logger.log(thisWeek.format());

        logger.log("Draw results:");
        logger.log(draw.getDrawResults().format());

        logger.log("Your score:");
        for (Dip dip : draw.findMatches(thisWeek)) {
            logger.log(dip.format());

        }
    }
}
