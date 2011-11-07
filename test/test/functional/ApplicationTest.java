package test.functional;

import com.nesbot.commons.datetime.Dater;
import com.nesbot.commons.datetime.Now;
import com.nesbot.commons.tests.TestHelpers;
import controllers.Application;
import ext.PostExtensions;
import models.Post;
import net.sourceforge.jwebunit.api.IElement;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationTest extends BaseFunctionalTest
{
   private static Post post1;
   private static Post post2;
   private static Post post3;
   private static Post postReal;
   private static String postRealContent;

   @Before
   public void refreshPosts() throws IOException
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

      for (File f : new File(PostExtensions.postsPath).listFiles())
      {
         if (f.isFile())
         {
            Pattern filePattern = Pattern.compile("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2})-(.+)\\.html$");

            Matcher matcher = filePattern.matcher(f.getName());
            if (matcher.matches() && matcher.groupCount() == 2)
            {
               BufferedReader br = new BufferedReader(new FileReader(f.getCanonicalPath()));
               String titleLine = br.readLine();

               while ((postRealContent = br.readLine()) != null)
               {
                  if (postRealContent.length() > 0)
                  {
                     postRealContent = postRealContent.replaceAll("\\<.*?>", "");
                     postRealContent = postRealContent.replaceAll(" +", " ");     // html will combine multiple spaces
                     break;
                  }
               }

               br.close();

               if (titleLine.startsWith("*{") && titleLine.endsWith("}*"))
               {
                  postReal = new Post(titleLine.substring(2, titleLine.length()-2), matcher.group(2), Dater.create(matcher.group(1)).timestamp());
                  postReal.save();
                  break;
               }
            }
         }
      }
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
      wt.clickLinkWithExactText(postReal.title);
      wt.assertTitleEquals(postReal.title + " -- Brian Nesbitt");
      wt.assertTextInElement("content", postReal.title);
      play.Logger.info(postRealContent);
      play.Logger.info(wt.getElementById("content").getTextContent());
      wt.assertTextInElement("content", postRealContent);
   }
   @Test
   public void testRss()
   {
      Post.clear();
      postReal.save();

      wt.beginAt("/rss");
      assertEquals("application/rss+xml", wt.getHeader("Content-type"));
      wt.assertTextPresent("Brian Nesbitt's  Blog");
      wt.assertTextPresent(postReal.title);
      wt.assertTextPresent(PostExtensions.fullUrl(postReal) + "</link>");
   }
}