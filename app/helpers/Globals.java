package helpers;

import play.Play;

public class Globals
{
   private static String getUrlBase()
   {
      return Play.configuration.getProperty("application.baseUrl");
   }
   public static String getUrlBaseWithSlash()
   {
      String urlbase = getUrlBase();
      return urlbase.endsWith("/") ? urlbase : urlbase + "/";
   }
   public static String getUrlBaseNoSlash()
   {
      String urlbase = getUrlBase();
      return urlbase.endsWith("/") ? urlbase.substring(0, urlbase.length() - 1) : urlbase;
   }
   private static String getUrlCdn()
   {
      return Play.configuration.getProperty("urlcdn");
   }
   public static String getUrlCdnWithSlash()
   {
      String urlcdn = getUrlCdn();
      return urlcdn.endsWith("/") ? urlcdn : urlcdn + "/";
   }
   public static String getUrlCdnNoSlash()
   {
      String urlcdn = getUrlCdn();
      return urlcdn.endsWith("/") ? urlcdn.substring(0, urlcdn.length() - 1) : urlcdn;
   }
   public static String getUrlImagesWithSlash()
   {
      return getUrlCdnWithSlash() + "images/";
   }
   public static String getGoogleAnalyticsTracker()
   {
      return Play.configuration.getProperty("gatracker", "");
   }
}