package test.functional;

import controllers.Application;
import ext.PostExtensions;
import helpers.Globals;
import models.Post;
import net.sourceforge.jwebunit.api.IElement;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.templates.JavaExtensions;

import java.util.Date;

public class ApplicationTest extends BaseFunctionalTest
{
   private static Post post1;
   private static Post post2;
   private static Post post3;

   private static void deleteAllPosts()
   {
      for (Post post : Post.findAll())
      {
         post.delete();
      }
   }

   @Before
   public void addPost()
   {
      deleteAllPosts();
      post1 = new Post("my title", "myslug", new Date(new DateTime(2011, 5, 21, 0, 0, 0, 0).getMillis()));
      post1.save();
      post2 = new Post("my title 2", "myslug 2", new Date());
      post2.save();
      post3 = new Post("Adding initial windows support for the Play! Framework 2.0 preview",
               "windows-support-for-play-framework-2-preview",
               new Date(new DateTime(1975, 5, 21, 0, 0, 0, 0).getMillis()));
      post3.save();
   }
   @Test
   public void testStaticPrivateCtors()
   {
      assertPrivateNoArgsCtor(Globals.class);
      assertPrivateNoArgsCtor(Application.class);
      assertPrivateNoArgsCtor(PostExtensions.class);
   }
   @Test
   public void testHomeIndexPage()
   {
      wt.beginAt(getRoute("Application.index"));
      wt.assertTitleEquals("Brian Nesbitt");
      wt.assertElementPresent("header");
      wt.assertLinkPresentWithExactText(post1.title);
      wt.assertLinkPresentWithExactText(post2.title);
      wt.assertLinkPresentWithExactText(post3.title);

      IElement e = wt.getElementByXPath("//a[@href='/2011/5/21/" + post1.slug + "']");
      assertEquals(post1.title, e.getTextContent());

      e = wt.getElementByXPath("//a[@href='" + JavaExtensions.format(post2.date, "/yyyy/M/d/") + post2.slug + "']");
      assertEquals(post2.title, e.getTextContent());

      e = wt.getElementByXPath("//a[@href='/1975/5/21/" + post3.slug + "']");
      assertEquals(post3.title, e.getTextContent());
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
      wt.setIgnoreFailingStatusCodes(true);
      wt.beginAt(PostExtensions.url(post1));
      wt.assertResponseCode(404);
   }
   @Test
   public void testShow()
   {
      wt.beginAt(getRoute("Application.index"));
      wt.clickLinkWithExactText(post3.title);
      wt.assertTitleEquals(post3.title + " -- Brian Nesbitt");
      wt.assertTextInElement("content", post3.title);
      wt.assertTextInElement("content", "Congrats to the Play! team and also to the community as we get to reap the benefits of their hard work!");
   }
   @Test
   public void testRss()
   {
      deleteAllPosts();
      post3.save();

      wt.beginAt("/rss");
      assertEquals("application/rss+xml", wt.getHeader("Content-type"));
      wt.assertTextPresent("Brian Nesbitt's  Blog");
      wt.assertTextPresent(post3.title);
      wt.assertTextPresent("/1975/5/21/" + post3.slug + "</link>");
   }
}