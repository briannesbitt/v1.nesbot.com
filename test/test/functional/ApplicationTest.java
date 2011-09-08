package test.functional;

import controllers.Application;
import helpers.Globals;
import jobs.Startup;
import models.Post;
import org.junit.Before;
import org.junit.Test;
import play.templates.JavaExtensions;

import java.util.Date;

public class ApplicationTest extends BaseFunctionalTest
{
   private static Post post1;
   private static Post post2;
   private static Post post3;

   @Before
   public void addPost()
   {
      post1 = new Post("my title", "myslug", new Date());
      post1.save();
      post2 = new Post("my title 2", "myslug 2", new Date());
      post2.save();
      post3 = new Post("Adding initial windows support for the Play! Framework 2.0 preview",
               "windows-support-for-play-framework-2-preview",
               new Date());
      post3.save();
   }
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
      wt.assertTitleEquals("Brian Nesbitt");
      wt.assertElementPresent("header");
      wt.assertLinkPresentWithExactText(post1.title);
      wt.assertLinkPresentWithExactText(post2.title);
   }
   @Test
   public void testPoweredByLink()
   {
      wt.beginAt(getRoute("Application.index"));
      wt.clickLinkWithExactText("powered by");
      wt.gotoWindowByTitle("Play framework - Home");
   }
   @Test
   public void testShowNotFound()
   {
      wt.setIgnoreFailingStatusCodes(true);
      wt.beginAt("/2011/5/6/notfound");
      wt.assertResponseCode(404);
   }
   @Test
   public void testShowNoTemplate()
   {
      String url = JavaExtensions.format(post1.date, "/yyyy/m/d/") + post1.slug;
      wt.setIgnoreFailingStatusCodes(true);
      wt.beginAt(url);
      wt.assertResponseCode(404);
   }
   @Test
   public void testShow()
   {
      String url = JavaExtensions.format(post3.date, "/yyyy/m/d/") + post3.slug;
      wt.beginAt(url);
      wt.assertTitleEquals(post3.title + " -- Brian Nesbitt");
      wt.assertTextInElement("content", post3.title);
   }
   @Test
   public void testRss()
   {
      wt.beginAt(getRoute("Appplication.rss"));
      assertEquals("application/rss+xml", wt.getHeader("Content-type"));
      wt.assertTextPresent("Brian Nesbitt's  Blog");
      wt.assertTextPresent(post3.title);
   }
}