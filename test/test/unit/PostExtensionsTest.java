package test.unit;

import com.nesbot.commons.datetime.Dater;
import com.nesbot.commons.tests.TestHelpers;
import ext.PostExtensions;
import helpers.Globals;
import jobs.Startup;
import models.Post;
import org.hibernate.dialect.PostgreSQLDialect;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostExtensionsTest extends UnitTest
{
   private Post _post;

   @Before
   public void runStartupAndGetFirstPost()
   {
      Post.clear();
      new Startup().doJob();
      _post = Post.findMostRecent(1).get(0);
   }
   @Test
   public void testStaticPrivateCtors()
   {
      TestHelpers.assertPrivateNoArgsCtor(PostExtensions.class);
   }
   @Test
   public void testTagName()
   {
      assertEquals("app/views/tags/posts/" + _post.slug + ".html", PostExtensions.tagName(_post));
   }
   @Test
   public void testUrl()
   {
      assertEquals(Dater.create(_post.updated).toString("/yyyy/M/d/") + _post.slug, PostExtensions.url(_post));
   }
   @Test
   public void testFullUrl()
   {
      assertEquals(Globals.getUrlBaseNoSlash() + Dater.create(_post.updated).toString("/yyyy/M/d/") + _post.slug, PostExtensions.fullUrl(_post));
   }
   @Test
   public void testPrettyUpdated()
   {
      assertEquals(Dater.create(_post.updated).toString("MMMM d, yyyy"), PostExtensions.prettyUpdated(_post));
   }
   @Test
   public void testToRFC822()
   {
      assertEquals(Dater.create(_post.updated).toRFC822(), PostExtensions.toRFC822(_post));
   }
}