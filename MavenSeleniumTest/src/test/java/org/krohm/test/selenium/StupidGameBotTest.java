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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arnaud
 */
public class StupidGameBotTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StupidGameBotTest.class);
    private static final String STUPID_GAME_CUSTOM_JS_URL = "https://raw.githubusercontent.com/ArnaudDemarcq/MavenSeleniumTest/master/MavenSeleniumTest/src/main/resources/StupidGameHelpers.js";
    private static final String STUPID_GAME_LOGIN_URL = "http://www.sexgangsters.com/login/";
    private static final String LOGIN = "krohm";
    private static final String PASSWORD = "kil";
    private static final String STUPID_GAME_HARVEST_URL = "http://www.sexgangsters.com/#business";
    private static final String STUPID_GAME_HARVEST_LOAD_SCRIPT = "jQuery.getScript(\"" + STUPID_GAME_CUSTOM_JS_URL + "\");";
    private static final String STUPID_GAME_HARVEST_AJAX = "harvestAll();";
    private static final String STUPID_GAME_FIGHT_LIST_AJAX = "return JSON.stringify(getRivalsList());";//getRivalsList()

    private void stupidGameLogin(WebDriver driver) {
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
    }

    @Test
    public void harvestCash() throws Exception {
        WebDriver driver = getTestDriver();
        stupidGameLogin(driver);
        stupidGameHarvest(driver);
        stupidGameFight(driver);
    }

    public void testLog() throws Exception {
        WebDriver driver = getTestDriver();
        logStupidGameCounters(driver);
    }

    private void stupidGameHarvest(WebDriver driver) throws InterruptedException {
        LOGGER.info("Starting Harverst Feature ...");
        driver.get(STUPID_GAME_HARVEST_URL);
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor jsDriver = ((JavascriptExecutor) driver);
            LOGGER.debug("Loading Script: " + jsDriver.executeScript(STUPID_GAME_HARVEST_LOAD_SCRIPT));
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
            //LOGGER.debug("Loading Script: " + jsDriver.executeScript(STUPID_GAME_HARVEST_LOAD_SCRIPT));
            String rawList = (String) jsDriver.executeScript(STUPID_GAME_FIGHT_LIST_AJAX);
            rawList = rawList.substring(1, rawList.length() - 1);
            rawList = rawList.replace("\\", "");
            LOGGER.trace("Runing Fight function: " + rawList);
            JSONParser parser = new JSONParser();
            JSONObject result = (JSONObject) parser.parse(rawList);
            LOGGER.trace("Runing Fight function: " + result.toJSONString());
            JSONArray rivals = (JSONArray) result.get("response");
            for (int i = 0; i < rivals.size(); i++) {
                JSONObject currentRival = (JSONObject) rivals.get(i);
                LOGGER.trace("got one: " + currentRival.toJSONString());
                RivalDescription currentRivalDescription = parseRivalJson(currentRival);
                LOGGER.info(currentRivalDescription.toString());
            }
            LOGGER.debug("Runing Fight function: " + rawList);

        } else {
            LOGGER.warn("It was worth trying, but JS is disabled");
        }
        LOGGER.trace(driver.getPageSource());
    }

    private RivalDescription parseRivalJson(JSONObject currentRival) {
        RivalDescription returnRivalDescription = new RivalDescription();
        // number of items
        try {
            returnRivalDescription.setItemNumber(((JSONArray) currentRival.get("items")).size());
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        }
        // defense
        try {
            String stringNum = "" + currentRival.get("defence");
            returnRivalDescription.setDefense(Integer.parseInt(stringNum));
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        }
        // attack
        try {
            String stringNum = "" + currentRival.get("attack");
            returnRivalDescription.setAttack(Integer.parseInt(stringNum));
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        }
        // id
        try {
            String stringNum = "" + currentRival.get("id");
            returnRivalDescription.setId(Integer.parseInt(stringNum));
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        } 
        // name
        try {
            returnRivalDescription.setName((String)currentRival.get("username"));
        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
        }
        return returnRivalDescription;

    }

    private void logStupidGameCounters(WebDriver driver) throws InterruptedException {
        LOGGER.info("Starting Counters Logging ...");
        driver.get(STUPID_GAME_HARVEST_URL);
        LOGGER.error("before wait ...");
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("main")));
        LOGGER.error("after wait ...");
        WebElement mainDiv = driver.findElement(By.id("main"));
        LOGGER.debug("############### Main Div" + mainDiv);
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
