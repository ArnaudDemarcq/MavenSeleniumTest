/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium;

import org.apache.commons.io.IOUtils;

/**
 *
 * @author Arnaud
 */
public class Utils {

    public static String readRessource(String resourceName) throws Exception {
        String returnString = IOUtils.toString(
                Utils.class.getResource(resourceName),
                "UTF-8");
        return returnString;
    }
}
