package helpers;

import play.Play;

public class Globals
{
   public static String getUrlBase()
   {
      return Play.configuration.getProperty("application.baseUrl");
   }
   public static String getUrlCdn()
   {
      return Play.configuration.getProperty("urlcdn");
   }
   public static String getUrlImages()
   {
      return Globals.getUrlCdn() + "images/";
   }
   public static String getGoogleAnalyticsTracker()
   {
      return Play.configuration.getProperty("gatracker", "");
   }
}