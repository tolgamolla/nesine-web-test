package web;

import base.BaseClass;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.web.HomePage;
import pages.web.PopularBetsPage;
import pages.web.myaccount.MyBetsPage;
import util.UrlFactory;

import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class BetTests extends BaseClass {

    private HomePage homePage;

    private static final String MARKET_NO = "MarketNo";
    private static final String PLAYED_COUNT = "PlayedCount";
    private static final String POPULAR_BET_LIST = "PopularBetList";

    private static final int FIRST_INDEX = 1;
    private static final int POPULAR_BETS_SIZE = 50;
    private static final int MY_BETS_SIZE = 5;

    @BeforeEach
    public void setUp() throws Exception
    {
        homePage = new HomePage(browser);
        browser.get(UrlFactory.BASE_URL.pageUrl);

        Properties mavenProps = initMavenProps();

        homePage
                .sendKeysTxtUsername(mavenProps.getProperty("Credential.USERNAME"))
                .sendKeysTxtPassword(mavenProps.getProperty("Credential.PASSWORD"))
                .clickLoginBtn();
    }

    @Test
    public void testPopularFootballBets() throws Exception
    {
        // navigate popular bets and url control
        PopularBetsPage popularBetsPage = homePage.clickPopularBets();
        assertEquals(UrlFactory.POPULAR_BETS.pageUrl, browser.getCurrentUrl());

        // navigate popular football bets
        popularBetsPage.clickFootballTab();

        // JsonArray from post request
        JSONArray popularBets = getPopularFootballBetsResponse();

        //Assert marketNo and PlayedCount
        IntStream.range(FIRST_INDEX, POPULAR_BETS_SIZE).forEach(index -> {

            String betCode = popularBetsPage.getTextPopularBetsCode(index);
            String betCount = popularBetsPage.getTextPopularBetsPlayedCount(index);

            JSONObject popularBetsObject = (JSONObject) popularBets.get(index -1);

            String marketNo = getValueByKeyName(popularBetsObject, MARKET_NO);
            String playedCount = getValueByKeyName(popularBetsObject, PLAYED_COUNT);

            assertAll(
                    () -> assertEquals(betCode, marketNo, "Bet counts are not equals. Index :" + index),
                    () -> assertEquals(betCount, playedCount, "Played count are not equals. Index :" + index));
        });

        //log out
        homePage
                .moveToMyAccount()
                .clickLogOutBtn();

        // check logout
        assertTrue(homePage.isDisplayedRegisterBtn());
    }

    @Test
    public void testOtherScenario() throws Exception {
        MyBetsPage myBetsPage = homePage
                .moveToMyAccount()
                .clickMyBets();

        assertAll(
                () -> assertEquals(MY_BETS_SIZE, myBetsPage.getSizeMyBetsList()),
                () -> assertEquals(UrlFactory.MY_BETS.pageUrl, browser.getCurrentUrl())
        );
    }

    // -- methods

    private JSONArray getPopularFootballBetsResponse () throws IOException, ParseException {

        // Create a url object
        URL url = new URL ("https://www.nesine.com/Iddaa/GetPopularBets");

        // Open a connection
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        // Set the request method
        con.setRequestMethod("POST");

        // Set the Request Content-Type Header Parameter
        con.setRequestProperty("Content-Type", "application/json; utf-8");

        // Set Response Format Type
        con.setRequestProperty("Accept", "application/json");

        // Ensure the Connection Will Be Used to Send Content
        con.setDoOutput(true);

        // Create the Request Body
        String jsonInputString = "{'eventType': '1', 'date': 'null'}";
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read the Response from Input Stream
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());

            JSONArray popularBets = (JSONArray) json.get(POPULAR_BET_LIST);

            return popularBets;
        }
    }

    private String getValueByKeyName (JSONObject object, String key)
    {
        String popularBetValueByKey = object.get(key).toString();

        return popularBetValueByKey;
    }
}
