package reexamples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REPractice {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a file or URL");
            System.exit(0);
        }
        Scanner source = null;
        if (args[0].startsWith("http")) {
            URI uri;
            try {
                uri = new URI(args[0]);
                URL url = uri.toURL();
                source = new Scanner(url.openStream());
            } catch (Exception e) {
                System.out.println("Interpreted " + args[0] + " as a URL, but it was bad.");
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        else {
            try {
                source = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                System.out.println("Interpreted " + args[0] + " as a file, but it was bad.");
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        
        Pattern pat = Pattern.compile("\\b[A-Za-z]*ing\\b");
        //int count = 0;
        while (source.hasNext()) {
            //System.out.println(count++);
            String line = source.nextLine();
            //System.out.println(line);
            Matcher mat = pat.matcher(line);
            while (mat.find())
                System.out.println(mat.group());
        }
        
    }
    
}
