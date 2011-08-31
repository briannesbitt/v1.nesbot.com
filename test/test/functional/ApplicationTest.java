package test.functional;

import controllers.Application;
import helpers.Globals;
import org.junit.Test;

public class ApplicationTest extends BaseFunctionalTest
{
   @Test
   public void testStaticPrivateCtors()
   {
      assertPrivateNoArgsCtor(Globals.class);
      assertPrivateNoArgsCtor(Application.class);
   }
   @Test
   public void testHomeIndexPage()
   {
      wt.beginAt(getRoute("Application.index"));
      wt.assertTitleEquals("Brian Nesbitt : nesbot.com");
      wt.assertElementPresent("header");
   }
   @Test
   public void testPoweredByLink()
   {
      wt.beginAt(getRoute("Application.index"));
      wt.clickLinkWithExactText("powered by");
      wt.gotoWindowByTitle("Play framework - Home");
   }
}