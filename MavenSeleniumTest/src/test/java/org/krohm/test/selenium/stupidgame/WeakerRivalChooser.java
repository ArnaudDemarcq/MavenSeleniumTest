/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium.stupidgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arnaud
 */
public class WeakerRivalChooser implements StupidGameFightChooser, RivalDescriptionFilter {

    @Override
    public RivalDescription chooseOpponent(RivalDescription player, List<RivalDescription> rivals) {
        RivalDescription currentBest = null;
        for (RivalDescription currentRival : rivals) {
            currentBest = compareRivals(currentBest, currentRival);
        }
        return currentBest;
    }

    private RivalDescription compareRivals(RivalDescription rd1, RivalDescription rd2) {
        if (rd2 == null) {
            return rd1;
        }
        if (rd1 == null) {
            return rd2;
        }
        if (rd2.getDefense() < rd1.getDefense()) {
            return rd2;
        }
        return rd1;
    }

    @Override
    public List<RivalDescription> doFilter(RivalDescription player, List<RivalDescription> rivals) {
        List<RivalDescription> filteredList = new ArrayList<RivalDescription>();
        filteredList.add(chooseOpponent(player, rivals));
        return filteredList;
    }
}
