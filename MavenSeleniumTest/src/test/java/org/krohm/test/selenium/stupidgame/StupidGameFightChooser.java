/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium.stupidgame;

import java.util.List;

/**
 *
 * @author Arnaud
 */
public interface StupidGameFightChooser {
    
    public RivalDescription chooseOpponent(RivalDescription player, List<RivalDescription> rivals); 
    
}
