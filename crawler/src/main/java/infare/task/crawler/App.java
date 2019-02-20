package infare.task.crawler;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    // For Fiddler
    System.setProperty("http.proxyHost", "127.0.0.1");
    System.setProperty("https.proxyHost", "127.0.0.1");
    System.setProperty("http.proxyPort", "8888");
    System.setProperty("https.proxyPort", "8888");
    // For Selenium
    System.setProperty("webdriver.gecko.driver",
        "/home/gediminas/Downloads/geckodriver-v0.24.0-linux64/geckodriver");
    System.setProperty("webdriver.chrome.driver",
        "/home/gediminas/Downloads/chromedriver_linux64/chromedriver");

    BasicCrawler crawlTest = new BasicCrawler();
    try {
      crawlTest.norwegianData();
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    SeleniumScrapper crawlerSAS = new SeleniumScrapper();
    try {
      crawlerSAS.openSAS();
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    try {
      crawlerSAS.submitFlightSearch();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    try {
      crawlerSAS.getSource();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    crawlerSAS.closeBrowser();
    crawlerSAS.getDataFromSource();
    System.out.println("Goodbye World!");
  }
}
