package testing.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.ListIterator;
import java.lang.String;

public class SunrisePage {
    private WebDriver driver;

    public SunrisePage(WebDriver driver) {
        this.driver = driver;
        if (!driver.getTitle().startsWith("восход")) {
            throw new IllegalStateException("This is not sunrise page");
        }
    }

    public SunrisePage inputTextGeo(String query) {
        driver.findElement(By.id("geoName")).sendKeys(query);
        return this;
    }

    public SunrisePage enterTextGeo(String query) {
        driver.findElement(By.id("geoName")).sendKeys(query, Keys.RETURN);
        return this;
    }

    public String getTitleCity() {
        return driver.findElement(By.className("sunrise__title__city")).getText();
    }

    public SunrisePage expandCalendar() {
        driver.findElement(By.id("calSelect")).click();
        return this;
    }

    public SunrisePage selectDay(String value) {
        WebElement cal = driver.findElement(By.className("calendar__selector"));
        List<WebElement> calDays = cal.findElements(By.tagName("td"));
        ListIterator<WebElement> iterator = calDays.listIterator();
        while (iterator.hasNext()) {
            WebElement currentDay = iterator.next().findElement(By.className("calendar__day-content"));
            if (currentDay.getText().equals(value)) {
                currentDay.click();
                break;
            }
        }
        return this;
    }

    public SunrisePage selectOtherMonthDay(String value) {
        WebElement cal = driver.findElement(By.className("calendar__selector"));
        List<WebElement> calDays = cal.findElements(By.tagName("td"));
        ListIterator<WebElement> iterator = calDays.listIterator();
        while (iterator.hasNext()) {
            WebElement currentDay = iterator.next();
            if (currentDay.getAttribute("class").contains("calendar__day_other-month")
                    && currentDay.getText().equals(value)) {
                currentDay.click();
                break;
            }
        }
        return this;
    }

    public String currentDay() {
        String tmp = driver.findElement(By.id("calSelect")).getText();
        return tmp.substring(0, tmp.indexOf(" "));
    }

    public SunrisePage selectMonth(String value) {
        expandCalendar();
        WebElement cal = driver.findElement(By.className("calendar__controls"));
        cal.findElement(By.cssSelector(".calendar__control"));
        cal.click();
        List<WebElement> months = driver.findElements(By.tagName("option"));
        ListIterator<WebElement> iterator = months.listIterator();
        while (iterator.hasNext()) {
            WebElement currentMonth = iterator.next();
            if (currentMonth.getAttribute("data-month").equals(value)) {
                currentMonth.click();
                break;
            }
        }
        return this;
    }

    public String currentMonth() {
        WebElement cal = driver.findElement(By.className("calendar__current-year-month"));
        List<WebElement> months = cal.findElements(By.tagName("option"));
        ListIterator<WebElement> iterator = months.listIterator();
        while (iterator.hasNext()) {
            WebElement currentMonth = iterator.next();
            if (currentMonth.getAttribute("selected") != null)
                return currentMonth.getAttribute("data-month");
        }
        return "";
    }

    public String currentYear() {
        expandCalendar();
        expandCalendar();
        WebElement year = driver.findElement(By.className("calendar__controls"));
        return year.findElement(By.tagName("input")).getAttribute("value");
    }

    public SunrisePage prevMonth() {
        expandCalendar();
        WebElement cal = driver.findElement(By.className("calendar__prev"));
        List<WebElement> prev = cal.findElements(By.tagName("span"));
        ListIterator<WebElement> iterator = prev.listIterator();
        while (iterator.hasNext()) {
            WebElement month = iterator.next();
            if (month.getAttribute("class").contains("month"))
                month.click();
        }
        return this;
    }

    public SunrisePage nextYear() {
        expandCalendar();
        WebElement cal = driver.findElement(By.className("calendar__next"));
        List<WebElement> prev = cal.findElements(By.tagName("span"));
        ListIterator<WebElement> iterator = prev.listIterator();
        while (iterator.hasNext()) {
            WebElement month = iterator.next();
            if (month.getAttribute("class").contains("year"))
                month.click();
        }
        return this;
    }


    public SunrisePage enterYear(String value) {
        expandCalendar();
        WebElement cal = driver.findElement(By.className("calendar__controls"));
        List<WebElement> year = cal.findElements(By.tagName("input"));
        ListIterator<WebElement> iterator = year.listIterator();
        while (iterator.hasNext()) {
            WebElement tmp = iterator.next();
            if (tmp.getAttribute("class").contains("calendar__control"))  {
                tmp.clear();
                tmp.sendKeys(value, Keys.RETURN);
                break;
            }
        }
        return this;
    }

    public String getSuggests() {
        return driver.findElement(By.className("autocomplete")).getText();
    }


}
