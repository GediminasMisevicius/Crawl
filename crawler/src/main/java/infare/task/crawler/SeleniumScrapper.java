package infare.task.crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SeleniumScrapper {


  FirefoxProfile profile = new FirefoxProfile();
  public WebDriver driver;

  //Opens a browser that goes to SAS website
  public void openSAS() {
    profile.setPreference("general.useragent.override",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/71.0.3578.98 Chrome/71.0.3578.98 Safari/537.36");
    FirefoxOptions options = new FirefoxOptions();
    options.setCapability(FirefoxDriver.PROFILE, profile);
    driver = new FirefoxDriver(options);
    driver.navigate().to("https://classic.flysas.com/en/de/");
  }

  //Closes the browser
  public void closeBrowser() {
    driver.close();
  }

  //Submits the parameters for specific flights
  public void submitFlightSearch() throws InterruptedException {
    WebElement from = driver.findElement(By.id(
        "ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtFrom"));
    WebElement to = driver.findElement(By.id(
        "ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_predictiveSearch_txtTo"));
    WebElement outwardDate = driver.findElement(By.className("flOutDate"));
    WebElement returnDate = driver.findElement(By.className("flInDate"));
    WebElement searchButton = driver.findElement(By.id(
        "ctl00_FullRegion_MainRegion_ContentRegion_ContentFullRegion_ContentLeftRegion_CEPGroup1_CEPActive_cepNDPRevBookingArea_Searchbtn_ButtonLink"));

    from.sendKeys("ARN");
    Thread.sleep(1000);
    WebElement arn = driver.findElement(By.id("ARN"));
    arn.click();
    Thread.sleep(1100);
    to.sendKeys("LHR");
    Thread.sleep(1200);
    WebElement lhr = driver.findElement(By.id("LHR"));
    lhr.click();
    Thread.sleep(800);

    outwardDate.click();
    WebElement outMo = driver.findElement(By.xpath("/html/body/div[3]/div/div/span[4]"));
    outMo.click();
    Thread.sleep(1000);

    WebElement outDay = driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[2]/td[1]/a"));
    outDay.click();
    Thread.sleep(500);

    returnDate.click();
    Thread.sleep(1000);

    WebElement retDay = driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[3]/td[2]/a"));
    retDay.click();
    Thread.sleep(1000);

    searchButton.click();
  }

  //Writes page source to text file
  public void getSource() throws InterruptedException {
    Thread.sleep(10000);
    String pageSource = driver.getPageSource();

    FileWriter writer = null;

    try {
      writer = new FileWriter("SAS_Source.txt", true);
      writer.write(pageSource);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    if (writer != null) {
      try {
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  //Extracts needed data from source file
  public void getDataFromSource() {

    String file = "SAS.txt";
    StringBuilder builder = new StringBuilder();
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(file));

      String currentLine = reader.readLine();
      while (currentLine != null) {
        builder.append(currentLine);
        builder.append("\n");
        currentLine = reader.readLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null)
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }


    String data = builder.toString();
    
    Pattern pattern = Pattern.compile("((segment\\.(b[LD]|e).*)|(recommendation\\[(.*\\n){6}))");
    Matcher matcher = pattern.matcher(data);
    
    String truncatedData = "";
    
    while (matcher.find()) {
      truncatedData += matcher.group() + "\n";
    }
    
    Pattern p2 = Pattern.compile("segment.bL.*;\\n(?=.*?CPH)(.*\\n)*?}");
    Matcher m2 = p2.matcher(truncatedData);
    
    String copenhagelessData = m2.replaceAll("");

    FileWriter writer = null;

    try {
      writer = new FileWriter("SAS_data.txt");
      writer.write(copenhagelessData);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    if (writer != null) {
      try {
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}


