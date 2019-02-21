package infare.task.crawler;

/**
 * 
 *
 */
public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    // For Fiddler to monitor connections
    System.setProperty("http.proxyHost", "127.0.0.1");
    System.setProperty("https.proxyHost", "127.0.0.1");
    System.setProperty("http.proxyPort", "8888");
    System.setProperty("https.proxyPort", "8888");
    // For Selenium webdriver
    System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");

    BasicCrawler crawlTest = new BasicCrawler();
    try {
      crawlTest.norwegianData();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    SeleniumScrapper crawlerSAS = new SeleniumScrapper();
    try {
      crawlerSAS.openSAS();
    } catch (InterruptedException e) {
      e.printStackTrace();
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
