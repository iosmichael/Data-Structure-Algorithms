package reexamples;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamPro {
    
    public static void main(String[] args) {
        try {
            URI uri = new URI(args[0]);
            URL url = uri.toURL();
            Scanner page = new Scanner(url.openStream());
            Pattern pat = Pattern.compile("\\b[A-Za-z0-9\\.]+@[A-Za-z0-9\\.]+\\.(com|net|org|uk|mil|gov|edu|us|uk)\\b");
            while (page.hasNext()) {
                String line = page.nextLine();
                Matcher mat = pat.matcher(line);
                while (mat.find())
                    System.out.println(mat.group());
            }
            
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
}
