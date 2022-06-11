import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class Automation2 {
  static final String alphaNumericDictionary = "0123456789";
  static public WebDriver driver;

  public static void main(String[] args) throws InterruptedException, IOException {
    System.setProperty("webdriver.chrome.driver", "/Users/ebu/Desktop/SEL/chromedriver");
    driver = new ChromeDriver();

    String url = "http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx";
    String username = "Tester";
    String password = "test";
    List<String[]> fakeUsers = Utility.readFromCSV("MOCK_DATA.csv");
    String[] fakeUser = fakeUsers.get(randNumber(0, fakeUsers.size()));
    int quantity = randNumber(1, 100);
    int cardChoice = randNumber(0, 3);
    String cardNumber = "";

    driver.get(url);

    sendKeysElementById("ctl00_MainContent_username", username);
    sendKeysElementById("ctl00_MainContent_password", password);
    clickElementById("ctl00_MainContent_login_button");
    clickElementByXPath("//a[@href='Process.aspx']");
    sendKeysElementById("ctl00_MainContent_fmwOrder_txtQuantity", String.valueOf(quantity));
    clickElementByXPath("//input[@value='Calculate']");
    double total = Double.parseDouble(getElementById("ctl00_MainContent_fmwOrder_txtTotal").getAttribute("value"));

    if(quantity >= 10){
      Assert.assertTrue(total == (quantity * 100 * 0.92));
    }
    else{
      Assert.assertTrue(total == (quantity * 100));
    }

    sendKeysElementById("ctl00_MainContent_fmwOrder_txtName", fakeUser[1] + " " + fakeUser[2]);
    sendKeysElementById("ctl00_MainContent_fmwOrder_TextBox2", fakeUser[3]);
    sendKeysElementById("ctl00_MainContent_fmwOrder_TextBox3", fakeUser[4]);
    sendKeysElementById("ctl00_MainContent_fmwOrder_TextBox4", fakeUser[5]);
    sendKeysElementById("ctl00_MainContent_fmwOrder_TextBox5", fakeUser[6]);

    clickElementById("ctl00_MainContent_fmwOrder_cardList_" + cardChoice);

    if(cardChoice == 0){
      cardNumber += "4";
      cardNumber += generateRandNumberWithLength(15);
    }
    else if(cardChoice == 1){
      cardNumber += "5";
      cardNumber += generateRandNumberWithLength(15);
    }
    else {
      cardNumber += "3";
      cardNumber += generateRandNumberWithLength(14);
    }

    sendKeysElementById("ctl00_MainContent_fmwOrder_TextBox6", cardNumber);
    sendKeysElementById("ctl00_MainContent_fmwOrder_TextBox1", "07/25");

    clickElementById("ctl00_MainContent_fmwOrder_InsertButton");


    Thread.sleep(250);
    String page = driver.getPageSource();
    Assert.assertTrue(page.contains("New order has been successfully added"));
    clickElementById("ctl00_logout");
    Thread.sleep(250);

    driver.quit();

  }

  public static void clickElementById(String element){
    driver.findElement(By.id(element)).click();
  }

  public static void clickElementByXPath(String xPath){
    driver.findElement(By.xpath(xPath)).click();
  }

  public static void sendKeysElementById(String element, String keys){
    driver.findElement(By.id(element)).sendKeys(keys);
  }

  public static WebElement getElementById(String element){
    return driver.findElement(By.id(element));
  }

  public static int randNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public static String generateRandNumberWithLength(int length) {
    String number = "";

    for (int i = 0; i < length; i++) {
      number += alphaNumericDictionary.charAt(randNumber(0, alphaNumericDictionary.length() - 1));
    }

    return number;
  }
}
