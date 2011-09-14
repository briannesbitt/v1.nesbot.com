package test.unit;

import ext.PostExtensions;
import helpers.Globals;
import models.Post;
import org.junit.Test;
import play.test.UnitTest;
import test.functional.BaseFunctionalTest;

import java.text.SimpleDateFormat;

public class PostExtensionsTest extends UnitTest
{
   @Test
   public void testStaticPrivateCtors()
   {
      BaseFunctionalTest.assertPrivateNoArgsCtor(PostExtensions.class);
   }
   @Test
   public void testTagName()
   {
      Post post = Post.findAll().get(0);
      assertEquals("app/views/tags/posts/" + post.slug + ".html", PostExtensions.tagName(post));
   }
   @Test
   public void testUrl()
   {
      Post post = Post.findAll().get(0);
      assertEquals(new SimpleDateFormat("/yyyy/M/d/").format(post.date) + post.slug, PostExtensions.url(post));
   }
   @Test
   public void testFullUrl()
   {
      Post post = Post.findAll().get(0);
      assertEquals(Globals.getUrlBaseNoSlash() + new SimpleDateFormat("/yyyy/M/d/").format(post.date) + post.slug, PostExtensions.fullUrl(post));
   }
}