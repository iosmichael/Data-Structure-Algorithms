package reexamples;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class FormatChapter {

  public static void main(String[] args){
     String source = "https://www.biblegateway.com/passage/?search=" +
         args[0] + "+" + args[1] + "&version=ESV";
        

     try {
        Scanner page = new Scanner((new URI(source)).toURL().openStream());
           
        String chapter = page.nextLine();
        while (! chapter.startsWith("<h1 class=\"passage-display")) {
            chapter = page.nextLine();
        }
        
        chapter = Pattern.compile("</?a[^>]*>").matcher(chapter).replaceAll("");

        chapter = Pattern.compile("</?sup[^>]*>").matcher(chapter).replaceAll("");

        chapter = Pattern.compile("</?font[^>]*>").matcher(chapter).replaceAll("");
        
        chapter = Pattern.compile("<p[^>]*>").matcher(chapter).replaceAll("\n\n");
        
        chapter = Pattern.compile("<h5>").matcher(chapter).replaceAll("\n\n\t");

        chapter = Pattern.compile("</h5>&nbsp;").matcher(chapter).replaceAll("\n\n");
        
        
        chapter = Pattern.compile("<h\\d[^>]*>").matcher(chapter).replaceAll("\n\n\t");
       
        
        chapter = Pattern.compile("&#8217;").matcher(chapter).replaceAll("\'");
        chapter = Pattern.compile("&#8212;").matcher(chapter).replaceAll("---");
        
        chapter = Pattern.compile("\\([A-Z]*\\)").matcher(chapter).replaceAll("");
        chapter = Pattern.compile("\\[[a-z]*\\]").matcher(chapter).replaceAll("");
        
        chapter = Pattern.compile("(\\d+)(\\D)").matcher(chapter).replaceAll("$1 $2");
        
        chapter = Pattern.compile("<div[^>]*>").matcher(chapter).replaceAll("");
        
       
        chapter = Pattern.compile("<br( /)?>").matcher(chapter).replaceAll("\n");
        chapter = Pattern.compile("&nbsp;").matcher(chapter).replaceAll("   ");

        chapter = Pattern.compile("<span[^>]*>").matcher(chapter).replaceAll("");
        chapter = Pattern.compile("</[^>]*>").matcher(chapter).replaceAll("");

        
        /*
        chapter = Pattern.compile("<.*\\>").matcher(chapter).replaceAll("");
        */
        System.out.println(chapter);
        
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
