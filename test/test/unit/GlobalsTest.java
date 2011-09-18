package test.unit;

import com.nesbot.commons.tests.TestHelpers;
import helpers.Globals;
import org.junit.Test;
import play.Play;
import play.test.UnitTest;
import test.functional.BaseFunctionalTest;

public class GlobalsTest extends UnitTest
{
   private String temp;

   private void setConfig(String key, String value)
   {
      temp = Play.configuration.getProperty(key);
      Play.configuration.setProperty(key, value);
   }
   private void resetConfig(String key)
   {
      Play.configuration.setProperty(key, temp);
   }

   @Test
   public void testStaticPrivateCtors()
   {
      TestHelpers.assertPrivateNoArgsCtor(Globals.class);
   }
   @Test
   public void testUrlBaseNoSlash()
   {
      setConfig("application.baseUrl", "http://url/");
      assertEquals("http://url", Globals.getUrlBaseNoSlash());
      resetConfig("application.baseUrl");

      setConfig("application.baseUrl", "http://url");
      assertEquals("http://url", Globals.getUrlBaseNoSlash());
      resetConfig("application.baseUrl");
   }
   @Test
   public void testUrlBaseWithSlash()
   {
      setConfig("application.baseUrl", "http://url/");
      assertEquals("http://url/", Globals.getUrlBaseWithSlash());
      resetConfig("application.baseUrl");

      setConfig("application.baseUrl", "http://url");
      assertEquals("http://url/", Globals.getUrlBaseWithSlash());
      resetConfig("application.baseUrl");
   }
   @Test
   public void testUrlCdnNoSlash()
   {
      setConfig("urlcdn", "http://url/");
      assertEquals("http://url", Globals.getUrlCdnNoSlash());
      resetConfig("urlcdn");

      setConfig("urlcdn", "http://url");
      assertEquals("http://url", Globals.getUrlCdnNoSlash());
      resetConfig("urlcdn");
   }
   @Test
   public void testUrlCdnWithSlash()
   {
      setConfig("urlcdn", "http://url/");
      assertEquals("http://url/", Globals.getUrlCdnWithSlash());
      resetConfig("urlcdn");

      setConfig("urlcdn", "http://url");
      assertEquals("http://url/", Globals.getUrlCdnWithSlash());
      resetConfig("urlcdn");
   }
   @Test
   public void testUrlImagesWithTrailingSlash()
   {
      assertEquals(Globals.getUrlCdnWithSlash() + "images/", Globals.getUrlImagesWithSlash());
   }
   @Test
   public void testGoogleAnalyticsTracker()
   {
      assertEquals("", Globals.getGoogleAnalyticsTracker());

      setConfig("gatracker", "trackme");
      assertEquals("trackme", Globals.getGoogleAnalyticsTracker());
      resetConfig("gatracker");
   }
}