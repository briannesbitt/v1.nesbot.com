package test.unit;

import models.Post;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;

import java.util.Date;

public class PostTest extends UnitTest
{
   @Before
   public void deleteAllPosts()
   {
      for(Post p : Post.findAll())
      {
         p.delete();
      }
   }
   public Post createPost(String title, String slug, int y, int m, int d)
   {
      return new Post(title, slug, new Date(new DateTime(y, m, d, 0, 0, 0, 0).getMillis()));
   }
   public Post createPost(String title, String slug, Date d)
   {
      return new Post(title, slug, d);
   }
   @Test
   public void testCtor()
   {
      Date d = new Date();
      Post post = createPost("my title", "my slug", d);

      assertEquals("my title", post.title);
      assertEquals("my slug", post.slug);
      assertEquals(d, post.date);
   }
   @Test
   public void testFindAllEmpty()
   {
      assertEquals(0, Post.findAll().size());
   }
   @Test
   public void testSave()
   {
      Date d = new Date();
      Post post = createPost("my title", "my slug", d);

      assertEquals(0, Post.findAll().size());

      post.save();

      assertEquals(1, Post.findAll().size());

      assertEquals("my title", post.title);
      assertEquals("my slug", post.slug);
      assertEquals(d, post.date);
   }
   @Test
   public void testFindBySlug()
   {
      Date d = new Date();
      Post post = createPost("my title", "my slug", d);

      assertEquals(0, Post.findAll().size());
      post.save();
      assertEquals(1, Post.findAll().size());

      Post found = Post.findBySlug("my slug");

      assertNotNull(found);
      assertEquals(post.title, found.title);
      assertEquals(post.slug, found.slug);
      assertEquals(post.date, found.date);
   }
   @Test
   public void testFindBySlugDNE()
   {
      Date d = new Date();
      createPost("my title", "my slug", d).save();

      assertNull(Post.findBySlug("my slug 2"));
   }
}