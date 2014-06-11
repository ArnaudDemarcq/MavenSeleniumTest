/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium.stupidgame;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arnaud
 */
public class RivalDescriptionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RivalDescriptionUtil.class);

    public static List<RivalDescription> getRivals(JSONObject rawList) {
        List<RivalDescription> returnList = new ArrayList<RivalDescription>();
        LOGGER.trace("Runing Fight function: " + rawList.toJSONString());
        JSONArray rivals = (JSONArray) rawList.get("response");
        RivalDescription bestRival = null;
        for (int i = 0; i < rivals.size(); i++) {
            JSONObject currentRival = (JSONObject) rivals.get(i);
            LOGGER.trace("got one: " + currentRival.toJSONString());
            RivalDescription currentRivalDescription = parseRivalJson(currentRival);
            returnList.add(currentRivalDescription);
            LOGGER.info(currentRivalDescription.toString());
        }
        return returnList;
    }

    public static RivalDescription parseRivalJson(JSONObject currentRival) {
        RivalDescription returnRivalDescription = new RivalDescription();
        // number of items
        try {
            returnRivalDescription.setItemNumber(((JSONArray) currentRival.get("items")).size());
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        }
        // numerical values
        returnRivalDescription.setDefense(getJsonValueAsInt(currentRival, "defence", Integer.MAX_VALUE));
        returnRivalDescription.setAttack(getJsonValueAsInt(currentRival, "attack", Integer.MAX_VALUE));
        returnRivalDescription.setId(getJsonValueAsInt(currentRival, "id", -1));
        try {
            returnRivalDescription.setName((String) currentRival.get("username"));
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        }
        return returnRivalDescription;

    }

    public static int getJsonValueAsInt(JSONObject currentRival, String key, int defaultValue) {
        try {
            String stringNum = "" + currentRival.get(key);
            return Integer.parseInt(stringNum);
        } catch (Exception ex) {
            LOGGER.warn("Cannot parse value for key: <" + key + "> Reason: " + ex.getMessage());
        }
        return defaultValue;
    }

    public static RivalDescription getPlayerInfo(JSONObject userData) {
        RivalDescription returnRivalDescription = new RivalDescription();

        returnRivalDescription.setDefense(getJsonValueAsInt(userData, "defence", Integer.MAX_VALUE));
        returnRivalDescription.setAttack(getJsonValueAsInt(userData, "attack", Integer.MAX_VALUE));
        returnRivalDescription.setId(getJsonValueAsInt(userData, "id", -1));

        // ressources 
        JSONObject ressources = (JSONObject) userData.get("resources");
        returnRivalDescription.setCash(getJsonValueAsInt(ressources, "bucks", Integer.MIN_VALUE));
        returnRivalDescription.setEnergy(getJsonValueAsInt(ressources, "energy", Integer.MIN_VALUE));
        returnRivalDescription.setEnergy_lim(getJsonValueAsInt(ressources, "energy_lim", Integer.MIN_VALUE));
        returnRivalDescription.setGold(getJsonValueAsInt(ressources, "gold", Integer.MIN_VALUE));
        returnRivalDescription.setStamina(getJsonValueAsInt(ressources, "stamina", -1));
        returnRivalDescription.setStamina_lim(getJsonValueAsInt(ressources, "stamina_lim", -1));

        return returnRivalDescription;
    }

    public static String toStringSimple(RivalDescription rival) {
        return "todo";

    }
}
