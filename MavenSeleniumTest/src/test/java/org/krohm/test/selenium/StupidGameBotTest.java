/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.krohm.test.selenium.stupidgame.AllStatsChooser;
import org.krohm.test.selenium.stupidgame.RivalDescription;
import org.krohm.test.selenium.stupidgame.RivalDescriptionUtil;
import org.krohm.test.selenium.stupidgame.StupidGameFightChooser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arnaud
 */
public class StupidGameBotTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StupidGameBotTest.class);
    private static final String STUPID_GAME_CUSTOM_JS_URL = "https://raw.githubusercontent.com/ArnaudDemarcq/MavenSeleniumTest/master/MavenSeleniumTest/src/main/resources/StupidGameHelpers.js";
    //private static final String STUPID_GAME_CUSTOM_JS_URL = Utils.readRessource("/StupidGameHelpers.js");
    private static final String STUPID_GAME_LOGIN_URL = "http://www.sexgangsters.com/login/";
    private static final String LOGIN = "krohm";
    private static final String PASSWORD = "kil";
    private static final String STUPID_GAME_HARVEST_URL = "http://www.sexgangsters.com/#business";
    private static final String STUPID_GAME_HARVEST_LOAD_SCRIPT = "jQuery.getScript(\"" + STUPID_GAME_CUSTOM_JS_URL + "\");";
    private static final String STUPID_GAME_HARVEST_AJAX = "return harvestAll();";
    private static final String STUPID_GAME_FIGHT_LIST_AJAX = "return getRivalsList();";//getRivalsList()
    private static final String STUPID_GAME_USER_DATA = "return JSON.stringify(userData);";//getRivalsList()
    private static final String STUPID_GAME_FIGHT_STEP1_AJAX = "return fightAll(";//getRivalsList()
    //private static final String STUPID_GAME_FIGHT_LIST_AJAX = "return JSON.stringify(getRivalsList());";//getRivalsList()
    //private static final String STUPID_GAME_FIGHT_STEP1_AJAX = "return JSON.stringify(makeFightStep1(";//getRivalsList()
    private static final StupidGameFightChooser CHOOSER = new AllStatsChooser();

    public StupidGameBotTest() throws Exception {
    }

    private void stupidGameLogin(WebDriver driver) throws Exception {
        LOGGER.debug("Getting login URL ...");
        String loginUrl = STUPID_GAME_LOGIN_URL; //System.getProperty("test.google.url");
        LOGGER.debug("Login URL is: <" + loginUrl + ">");
        driver.get(loginUrl);
        LOGGER.debug("Login Page loaded, title is: " + driver.getTitle());
        // Effective login
        WebElement loginElement = driver.findElement(By.name("username"));
        WebElement passwordElement = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("button-ok"));
        loginElement.sendKeys(LOGIN);
        passwordElement.sendKeys(PASSWORD);
        loginButton.click();
        LOGGER.debug("Post Login Page loaded, title is: " + driver.getTitle());
        Utils.readRessource("/StupidGameHelpers.js");
        driver.get(STUPID_GAME_HARVEST_URL);
    }

    private void loadCustomScript(JavascriptExecutor jsDriver) throws Exception {
        LOGGER.debug("Loading custom Script: " + jsDriver.executeScript(STUPID_GAME_HARVEST_LOAD_SCRIPT));
    }

    private void loadCustomScript_new(JavascriptExecutor jsDriver) throws Exception {
        String script = Utils.readRessource("/StupidGameHelpers.js");
        LOGGER.error(script);
        LOGGER.debug("Loading custom Script: " + jsDriver.executeScript(script));

    }

    @Test
    public void harvestCash() throws Exception {
        WebDriver driver = getTestDriver();
        stupidGameLogin(driver);
        stupidGameHarvest(driver);
        stupidGameFight(driver);
    }

    private void stupidGameHarvest(WebDriver driver) throws Exception {
        LOGGER.info("Starting Harverst Feature ...");
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor jsDriver = ((JavascriptExecutor) driver);
            loadCustomScript(jsDriver);
            LOGGER.debug("Runing harvest function: " + jsDriver.executeScript(STUPID_GAME_HARVEST_AJAX));;
        } else {
            LOGGER.warn("It was worth trying, but JS is disabled");
        }
        LOGGER.trace(driver.getPageSource());
    }

    private void stupidGameFight(WebDriver driver) throws Exception {
        LOGGER.info("Starting Fight Feature ...");
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor jsDriver = ((JavascriptExecutor) driver);
            RivalDescription player = getPlayerInfo(jsDriver);
            LOGGER.info("Player stats are: " + player.toString());
            if (player.getStamina() > 1) {
                List<RivalDescription> rivals = getRivals(jsDriver);
                RivalDescription bestRival = CHOOSER.chooseOpponent(player, rivals);
                LOGGER.info("Best rival id is: <" + bestRival.getId() + ">. Stamina is: <" + player.getStamina() + ">.");
                String realQuerry = STUPID_GAME_FIGHT_STEP1_AJAX + bestRival.getId() + ");";
                LOGGER.debug("Runing Fight function: " + realQuerry);
                Object rawAnswer = jsDriver.executeScript(realQuerry);
                LOGGER.debug("Runing Fight function: " + rawAnswer);
            } else {
                LOGGER.info("not enougth stamina. Skipping fight");
            }
        } else {
            LOGGER.warn("It was worth trying, but JS is disabled");
        }
        LOGGER.trace(driver.getPageSource());
    }

    private List<RivalDescription> getRivals(JavascriptExecutor jsDriver) throws ParseException {
        String rawList = (String) jsDriver.executeScript(STUPID_GAME_FIGHT_LIST_AJAX);
        LOGGER.trace("Runing Fight List function: " + rawList);;
        JSONParser parser = new JSONParser();
        JSONObject result = (JSONObject) parser.parse(rawList);
        return RivalDescriptionUtil.getRivals(result);
    }

    private RivalDescription getPlayerInfo(JavascriptExecutor jsDriver) throws ParseException {
        String rawData = (String) jsDriver.executeScript(STUPID_GAME_USER_DATA);
        LOGGER.trace("User data is: " + rawData);
        JSONParser parser = new JSONParser();
        JSONObject result = (JSONObject) parser.parse(rawData);
        return RivalDescriptionUtil.getPlayerInfo(result);
    }

    private WebDriver getTestDriver() {
        CustomizableWebDriver tmpDriver = new CustomizableWebDriver();
        tmpDriver.setJavascriptEnabled(true);
        WebClient internalWebClient = tmpDriver.getWebClient();
        AjaxController tmpAjaxController = new NicelyResynchronizingAjaxController();
        internalWebClient.setAjaxController(tmpAjaxController);
        return tmpDriver;
    }

    private WebDriver getTestDriverChrome() {
        return new ChromeDriver();
    }

    private class CustomAjaxController extends NicelyResynchronizingAjaxController {

        @Override
        public boolean processSynchron(HtmlPage page, WebRequest settings, boolean async) {
            return super.processSynchron(page, settings, false);
        }
    }

    private class CustomizableWebDriver extends HtmlUnitDriver {

        @Override
        public WebClient getWebClient() {
            this.newWebClient(BrowserVersion.CHROME);
            return super.getWebClient();
        }
    }
}
