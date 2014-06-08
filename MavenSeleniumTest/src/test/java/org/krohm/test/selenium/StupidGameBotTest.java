/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.krohm.test.selenium;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Arnaud
 */
public class StupidGameBotTest {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StupidGameBotTest.class);
    private static final String STUPID_GAME_LOGIN_URL = "http://www.sexgangsters.com/login/";
    private static final String LOGIN = "krohm";
    private static final String PASSWORD = "kil";

    @Test
    public void harvestCash() {
        WebDriver driver = new HtmlUnitDriver();
        stupidGameLogin(driver);

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
        loginElement.sendKeys(LOGIN);
        passwordElement.sendKeys(PASSWORD);
        loginElement.submit();
        LOGGER.info("Post Login Page loaded, title is: " + driver.getTitle());

    }
}
