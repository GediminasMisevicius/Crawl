package infare.task.crawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BasicCrawler {

  private Document doc;

  public BasicCrawler() {}

  public void norwegianData() throws InterruptedException {
    String norURL = "";
    FileWriter writer = null;

    try {
      writer = new FileWriter("norway.txt", true);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

    //Different URL for each flight
    for (int i = 1; i < 31; i++) {
      if (i < 10) {
        norURL =
            "https://www.norwegian.com/uk/ipc/availability/avaday?D_City=OSL&A_City=RIX&TripType=1&D_Day=0"
                + i
                + "&D_Month=201904&D_SelectedDay=01&R_Day=24&R_Month=201904&R_SelectedDay=24&dFlight=DY1072OSLRIX&dCabinFareType=1&IncludeTransit=false&AgreementCodeFK=-1&CurrencyCode=EUR&rnd=44929&processid=98027&mode=ab";
      } else {
        norURL =
            "https://www.norwegian.com/uk/ipc/availability/avaday?D_City=OSL&A_City=RIX&TripType=1&D_Day="
                + i
                + "&D_Month=201904&D_SelectedDay=01&R_Day=24&R_Month=201904&R_SelectedDay=24&dFlight=DY1072OSLRIX&dCabinFareType=1&IncludeTransit=false&AgreementCodeFK=-1&CurrencyCode=EUR&rnd=44929&processid=98027&mode=ab";
      }

      try {
        Thread.sleep(2000);
        this.doc = Jsoup.connect(norURL).get();
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
      
      //Select needed elements from the page
      Elements dep = doc.select("td.depdest > div.content");
      Elements arr = doc.select("td.arrdest > div.content");
      Elements prices = doc.select("td.fareselect > div.content > label");
      Elements taxes = doc.select("tr:has(span#bookingPrice_TaxesToggleIcon) > td.rightcell");

      //Write the data to file
      try {
        writer.write("Day: " + i + "\n");
        writer.write("Departure time and airport:\n");
        for (Element el : dep) {
          writer.write(el.text() + "\n");
        }
        writer.write("Arrival time and airport:\n");
        for (Element el : arr) {
          writer.write(el.text() + "\n");
        }
        writer.write("Prices:\n");
        ArrayList<Double> pricesDbl = new ArrayList<>();
        for (Element el : prices) {
          writer.write(el.text() + "\n");
          pricesDbl.add(Double.parseDouble(el.text()));
        }
        Collections.sort(pricesDbl);
        try {
          writer.write("Cheapest price:\n" + pricesDbl.get(0) + "\n");
        } catch (IndexOutOfBoundsException e) {
          writer.write("Cheapest price:\n N/A \n");
        }
        writer.write("Taxes:\n");
        for (Element el : taxes) {
          writer.write(el.text() + "\n");
        }
        writer.write("-------------------------------\n");
      } catch (IOException e) {
        e.printStackTrace();
      }

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
