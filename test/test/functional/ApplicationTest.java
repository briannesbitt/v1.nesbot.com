package test.functional;

import com.nesbot.commons.datetime.Dater;
import com.nesbot.commons.datetime.Now;
import com.nesbot.commons.tests.TestHelpers;
import controllers.Application;
import ext.PostExtensions;
import helpers.Globals;
import models.Post;
import net.sourceforge.jwebunit.api.IElement;
import org.junit.Before;
import org.junit.Test;
import play.templates.JavaExtensions;

import java.util.ArrayList;
import java.util.Date;

public class ApplicationTest extends BaseFunctionalTest
{
   private static Post post1;
   private static Post post2;
   private static Post post3;

   @Before
   public void refreshPosts() throws InterruptedException
   {
      Post.clear();
      post1 = new Post("my title", "ansi-colour-support-for-play-framework-2-preview", Dater.create(2011, 5, 21).timestamp());
      post1.save();
      post2 = new Post("my title 2", "myslug 2", Now.timestamp());
      post2.save();
      post3 = new Post("Adding initial windows support for the Play! Framework 2.0 preview",
               "windows-support-for-play-framework-2-preview",
               Dater.create(1975, 5, 21).timestamp());
      post3.save();
   }
   @Test
   public void testStaticPrivateCtors()
   {
      TestHelpers.assertPrivateNoArgsCtor(Application.class);
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

      e = wt.getElementByXPath("//a[@href='" + Dater.create(post2.updated).toString("/yyyy/M/d/") + post2.slug + "']");
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
      wt.beginAt(PostExtensions.url(post2));
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
      Post.clear();
      post1.save();
      post3.save();

      wt.beginAt("/rss");
      assertEquals("application/rss+xml", wt.getHeader("Content-type"));
      wt.assertTextPresent("Brian Nesbitt's  Blog");
      wt.assertTextPresent(post3.title);
      wt.assertTextPresent(Globals.getUrlBaseNoSlash() + "/1975/5/21/" + post3.slug + "</link>");
   }
}