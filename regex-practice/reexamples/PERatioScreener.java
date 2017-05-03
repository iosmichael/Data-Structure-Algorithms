package reexamples;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PERatioScreener {

    private static String source =
        "http://online.wsj.com/mdc/public/page/2_3024-NYSE.html?mod=stocksdaily";
	
    public static void main(String[] args) {
        int lower = Integer.parseInt(args[0]),
            upper = Integer.parseInt(args[1]);
        
        try {
            Scanner page = new Scanner((new URI(source)).toURL().openStream());

            // patterns... 
                      //for a company's name
            Pattern namePat = Pattern.compile("\\s*\\Q<td style=\"padding-left: 5px;\" align=\"left\" height=\"18\"><b>\\E(.*)\\Q</b></td>\\E"),
                     // for a stock symbol
                symPat = Pattern.compile(">([A-Z%$]{1,5})<"),
                     // for a number
                numPat = Pattern.compile("\\Q<nobr>\\E(\\-?\\d*\\.\\d{2})\\Q</nobr>\\E"),
                     // for an apostrophe
                aposPat = Pattern.compile("\\Q&#039;\\E"),
                     // for an ampersand
                ampPat = Pattern.compile("&amp;");
            while (page.hasNext()) {
                // grab the next line
                String line = page.nextLine();
                Matcher mat = namePat.matcher(line);
                // if we can find a company name...
                if (mat.find()) {    
                    String name = mat.group(1);
                    // fix the apostrophes and ampersands
                    name = aposPat.matcher(name).replaceAll("\'");
                    name = ampPat.matcher(name).replaceAll("&");
                    // get the stock symbol (next line)
                    Matcher mat2 = symPat.matcher(page.nextLine());
                    String symbol = "??";
                    if (mat2.find())
                        symbol = mat2.group(1);
                    // skip the next 10 lines (to line with the PE ratio)
                    for (int i = 0; i < 11; i++) page.nextLine();
                    // get the PE ratio
                    Matcher mat3 = numPat.matcher(page.nextLine());
                    String pe = "??";
                    // if it's a number and in the desired range, print 
                    if (mat3.find()) {
                        pe = mat3.group(1);
                        try {
                            double peNum = Double.parseDouble(pe);
                            if (peNum >= lower && peNum <= upper)
                             System.out.println(name + "\t" + symbol + "\t" + pe);
                        } catch (NumberFormatException nfe) {}
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
