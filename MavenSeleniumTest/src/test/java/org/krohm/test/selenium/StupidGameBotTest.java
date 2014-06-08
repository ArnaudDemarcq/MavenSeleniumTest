/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
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

    @Test
    public void harvestCash() throws Exception {
        WebDriver driver = getTestDriver();
        stupidGameLogin(driver);
        stupidGameHarvest(driver);

    }

    private void stupidGameLogin(WebDriver driver) {
        LOGGER.info("Getting login URL ...");
        String loginUrl = STUPID_GAME_LOGIN_URL; //System.getProperty("test.google.url");
        LOGGER.info("Login URL is: <" + loginUrl + ">");
        driver.get(loginUrl);
        LOGGER.info("Login Page loaded, title is: " + driver.getTitle());
        // Effective login
        WebElement loginElement = driver.findElement(By.name("username"));
        WebElement passwordElement = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.className("button-ok"));
        loginElement.sendKeys(LOGIN);
        passwordElement.sendKeys(PASSWORD);
        loginButton.click();
        LOGGER.info("Post Login Page loaded, title is: " + driver.getTitle());
    }

    private void stupidGameHarvest(WebDriver driver) throws InterruptedException {
        driver.get(STUPID_GAME_HARVEST_URL);
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor jsDriver = ((JavascriptExecutor) driver);
            LOGGER.info("Loading Script: " + jsDriver.executeScript(STUPID_GAME_HARVEST_LOAD_SCRIPT));
            LOGGER.info("Runing harvest function: " + jsDriver.executeScript(STUPID_GAME_HARVEST_AJAX));;
        } else {
            LOGGER.info("Was worth trying ...");
        }
        LOGGER.debug(driver.getPageSource());
        /*
         Thread.sleep(60000);
         WebElement harvestButton = driver.findElement(By.className("btn-collect-all"));
         LOGGER.debug("###############" + driver.getPageSource());
         harvestButton.click();/**/
    }

    private WebDriver getTestDriver() {
        CustomizableWebDriver tmpDriver = new CustomizableWebDriver();
        tmpDriver.setJavascriptEnabled(true);
        WebClient proot = tmpDriver.getWebClient();
        proot.setAjaxController(new NicelyResynchronizingAjaxController());
        return tmpDriver;
    }

    private class CustomizableWebDriver extends HtmlUnitDriver {

        @Override
        public WebClient getWebClient() {
            return super.getWebClient();
        }
    }
}
