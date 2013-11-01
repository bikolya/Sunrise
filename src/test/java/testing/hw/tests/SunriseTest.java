package testing.hw.tests;


import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testing.hw.SunrisePage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.MalformedURLException;
import java.net.URL;


public class SunriseTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "hub", "url"})
    public void setUp(String browser, String hub, String url) throws MalformedURLException {
        if (browser.toLowerCase().contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.chrome());
        }
        else if (browser.toLowerCase().contains("firefox"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.firefox());
        else if (browser.toLowerCase().contains("safari"))
            this.driver = new RemoteWebDriver(new URL(hub), DesiredCapabilities.safari());
        else
            throw new NotImplementedException();
        this.driver.get(url);
    }

    @Test
    public void suggestGeoTest() {
        String suggests = new SunrisePage(this.driver).inputTextGeo("Мо").getSuggests();
        Assert.assertTrue(suggests.contains("Москва"));

    }

    @Test
    public void inputGeoTest() {
        String titleCity = new SunrisePage(this.driver).enterTextGeo("Таганрог").getTitleCity();
        Assert.assertTrue(titleCity.contains("Таганрог"));
    }

    @Test
    public void inputAbsenceGeoTest() {
        String currentTitleCity = new SunrisePage(this.driver).getTitleCity();
        String titleCity = new SunrisePage(this.driver).enterTextGeo("Троицк").getTitleCity();
        Assert.assertEquals(currentTitleCity, titleCity);
    }

    @Test
    public void calendarSelectDayTest() {
        String day = "6";
        new SunrisePage(this.driver).expandCalendar().selectDay(day);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {}
        String selectDay = new SunrisePage(this.driver).currentDay();
        Assert.assertEquals("6", selectDay);
    }

    @Test
    public void calendarSelectOtherMonthDayTest() {
        String day = "1";
        new SunrisePage(this.driver).expandCalendar().selectOtherMonthDay(day);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {}
        String selectDay = new SunrisePage(this.driver).currentDay();
        Assert.assertEquals("1", selectDay);
    }

    @Test
    public void calendarSelectMonthControlTest() {
        String month = "2";
        String selectMonth = new SunrisePage(this.driver).selectMonth(month).currentMonth();
        Assert.assertEquals(selectMonth, month);
    }

    @Test
    public void calendarPrevMonthControlTest() {
        String rndDay = "12";
        String currentMonth = new SunrisePage(this.driver).currentMonth();
        new SunrisePage(this.driver).prevMonth().selectDay(rndDay);
        String prevMonth = new SunrisePage(this.driver).currentMonth();
        Assert.assertEquals(Integer.parseInt(prevMonth) + 1, Integer.parseInt(currentMonth));
    }

    @Test
    public void calendarNextYearControlTest() {
        String rndDay = "12";
        String currentYear = new SunrisePage(this.driver).currentYear();
        new SunrisePage(this.driver).nextYear().selectDay(rndDay);
        String nextYear = new SunrisePage(this.driver).currentYear();
        Assert.assertEquals(Integer.parseInt(nextYear), Integer.parseInt(currentYear) + 1);
    }

    @Test
    public void calendarCorrectYearTest() {
        String correctYear = "2014";
        String rndDay = "12";
        new SunrisePage(this.driver).enterYear(correctYear).selectDay(rndDay);
        String year = new SunrisePage(this.driver).currentYear();
        Assert.assertEquals(correctYear, year);
    }

    @Test
    public void calendarStringYearTest()  {
        String correctYear = "Москва";
        String rndDay = "12";
        new SunrisePage(this.driver).enterYear(correctYear).selectDay(rndDay);
        String year = new SunrisePage(this.driver).currentYear();
        Assert.assertFalse(correctYear.equals(year));
    }

    @Test
    public void calendarTwoDigitYearTest() {
        String correctYear = "99";
        String rndDay = "6";
        new SunrisePage(this.driver).enterYear(correctYear).selectDay(rndDay);
        String year = new SunrisePage(this.driver).currentYear();
        Assert.assertEquals("19" + correctYear, year);
    }

    @Test
    public void calendarEmptyYearTest() {
        String correctYear = "  ";
        String rndDay = "12";
        new SunrisePage(this.driver).enterYear(correctYear).selectDay(rndDay);
        String year = new SunrisePage(this.driver).currentYear();
        Assert.assertFalse(correctYear.equals(year));
    }

    @Test
    public void calendarBigYearTest() {
        String correctYear = "99999";
        String rndDay = "12";
        new SunrisePage(this.driver).enterYear(correctYear).selectDay(rndDay);
        String year = new SunrisePage(this.driver).currentYear();
        Assert.assertEquals(correctYear, year);
    }

    @Test
    public void calendarNegativeYearTest() {
        String correctYear = "-2013";
        String rndDay = "12";
        new SunrisePage(this.driver).enterYear(correctYear).selectDay(rndDay);
        String year = new SunrisePage(this.driver).currentYear();
        Assert.assertEquals(correctYear, year);
    }

    @AfterMethod
    public void killDriver() {
        this.driver.quit();
    }
}
