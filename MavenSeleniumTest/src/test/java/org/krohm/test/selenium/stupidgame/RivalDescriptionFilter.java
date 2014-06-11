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
public interface RivalDescriptionFilter {

    public List<RivalDescription> doFilter(RivalDescription player, List<RivalDescription> rivals);
}
