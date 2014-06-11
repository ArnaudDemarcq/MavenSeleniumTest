/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium.stupidgame;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arnaud
 */
public class AllStatsChooser implements StupidGameFightChooser, RivalDescriptionFilter {

    private WeakerRivalChooser weakerRivalChooser = new WeakerRivalChooser();
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AllStatsChooser.class);

    @Override
    public RivalDescription chooseOpponent(RivalDescription player, List<RivalDescription> rivals) {
        List<RivalDescription> filteredList = doFilter(player, rivals);
        if (filteredList.size() > 0) {
            return weakerRivalChooser.chooseOpponent(player, filteredList);
        }
        LOGGER.warn("No perfect opponent choosed. Defaulting to WeakerRivalChooser");
        return weakerRivalChooser.chooseOpponent(player, rivals);
    }

    @Override
    public List<RivalDescription> doFilter(RivalDescription player, List<RivalDescription> rivals) {
        List<RivalDescription> filteredList = new ArrayList<RivalDescription>();
        for (RivalDescription currentRival : rivals) {
            if (player.getAttack() > currentRival.getDefense()
                    && player.getDefense() > currentRival.getAttack()
                    && player.getAttack() > currentRival.getAttack()
                    && player.getDefense() > currentRival.getDefense()) {
                LOGGER.info("Perfectly weaker opponent found: " + currentRival.toString());
                filteredList.add(currentRival);
            }
        }
        return filteredList;

    }
}
