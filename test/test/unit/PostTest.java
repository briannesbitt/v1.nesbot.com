package test.unit;

import com.nesbot.commons.datetime.Dater;
import ext.PostExtensions;
import models.Post;
import org.junit.Before;
import org.junit.Test;
import play.test.UnitTest;
import test.functional.BaseFunctionalTest;

import java.util.Date;

public class PostTest extends UnitTest
{
   @Before
   public void clear()
   {
      Post.clear();
   }
   public Post createPost(String title, String slug, Dater d)
   {
      return new Post(title, slug, d.timestamp());
   }
   public Post createPost(String title, String slug, int y, int m, int d)
   {
      return new Post(title, slug, Dater.create(y, m, d).timestamp());
   }
   @Test
   public void testCtor()
   {
      Dater d = Dater.now();
      Post post = createPost("my title", "my slug", d);

      assertEquals("my title", post.title);
      assertEquals("my slug", post.slug);
      assertEquals(d.timestamp(), post.updated);
   }
   @Test
   public void testFindAllEmpty()
   {
      assertEquals(0, Post.findAll().size());
   }
   @Test
   public void testFindAll()
   {
      assertEquals(0, Post.findAll().size());
      Post p0 = createPost("my title", "my slug", Dater.now()).save();
      assertEquals(1, Post.findAll().size());
      Post p1 = createPost("my title2", "my slug2", Dater.now()).save();
      assertEquals(2, Post.findAll().size());

      int index = 0;
      for (Post p : Post.findAll())
      {
         if (index == 0)
         {
            assertEquals(p0, p);
         }
         if (index == 1)
         {
            assertEquals(p1, p);
         }
         index++;
      }
   }
   @Test
   public void findMostRecentWithNegativeCount()
   {
      assertEquals(0, Post.findMostRecent(-1).size());
   }
   @Test
   public void findMostRecentWithCountZero()
   {
      assertEquals(0, Post.findMostRecent(0).size());
   }
   @Test
   public void findMostRecentWithCountLargerThanSize()
   {
      Post p0 = createPost("my title", "my slug", Dater.now()).save();
      Post p1 = createPost("my title2", "my slug2", Dater.now()).save();

      int index = 0;
      for (Post p : Post.findMostRecent(Post.count() + 1))
      {
         if (index == 0)
         {
            assertEquals(p0, p);
         }
         if (index == 1)
         {
            assertEquals(p1, p);
         }
         index++;
      }
   }
   @Test
   public void findMostRecent()
   {
      Post p0 = createPost("my title", "my slug", Dater.now()).save();
      Post p1 = createPost("my title2", "my slug2", Dater.now()).save();
      Post p2 = createPost("my title3", "my slug3", Dater.now()).save();
      createPost("my title4", "my slug4", Dater.now()).save();
      assertEquals(4, Post.count());

      int index = 0;
      for (Post p : Post.findMostRecent(3))
      {
         if (index == 0)
         {
            assertEquals(p0, p);
         }
         if (index == 1)
         {
            assertEquals(p1, p);
         }
         if (index == 2)
         {
            assertEquals(p2, p);
         }
         index++;
      }
   }
   @Test
   public void testSaveDupReturnsNull()
   {
      Dater d = Dater.now();
      Post post = createPost("my title", "my slug", d);

      assertEquals(0, Post.count());
      assertSame(post, post.save());
      assertNull(post.save());
      assertEquals(1, Post.count());
   }
   @Test
   public void testSave()
   {
      Dater d = Dater.now();
      Post post = createPost("my title", "my slug", d);

      assertEquals(0, Post.count());
      post.save();
      assertEquals(1, Post.count());

      post = Post.findMostRecent(1).get(0);
      assertEquals("my title", post.title);
      assertEquals("my slug", post.slug);
      assertEquals(d.timestamp(), post.updated);
   }
   @Test
   public void testFindBySlugNotFound()
   {
      assertNull(Post.findBySlug("my slug diff1"));
      Post post = createPost("my title", "my slug", Dater.now()).save();
      assertNull(Post.findBySlug("my slug diff2"));
   }
   @Test
   public void testFindBySlug()
   {
      Dater d = Dater.now();
      Post post = createPost("my title", "my slug", d);

      assertEquals(0, Post.count());
      post.save();
      assertEquals(1, Post.count());

      Post found = Post.findBySlug("my slug");

      assertNotNull(found);
      assertEquals(post.title, found.title);
      assertEquals(post.slug, found.slug);
      assertEquals(post.updated, found.updated);
   }
   @Test
   public void testCount()
   {
      assertEquals(0, Post.count());

      createPost("t", "s", Dater.now()).save();
      assertEquals(1, Post.count());
      createPost("t2", "s2", Dater.now()).save();
      assertEquals(2, Post.count());
      createPost("t3", "s3", Dater.now()).save();
      assertEquals(3, Post.count());

      Post.clear();
      assertEquals(0, Post.count());
   }
   @Test
   public void testClear()
   {
      assertEquals(0, Post.count());
      createPost("t", "s", Dater.now()).save();
      createPost("t2", "s2", Dater.now()).save();
      createPost("t3", "s3", Dater.now()).save();
      assertEquals(3, Post.count());

      Post.clear();
      assertEquals(0, Post.count());
   }
   @Test
   public void testPrevious()
   {
      Post p0 = createPost("my title", "my slug", Dater.now()).save();
      Post p1 = createPost("my title2", "my slug2", Dater.now()).save();
      Post p2 = createPost("my title3", "my slug3", Dater.now()).save();
      assertEquals(3, Post.count());

      assertNull(p0.previous());
      assertSame(p0, p1.previous());
      assertSame(p1, p2.previous());
   }
   @Test
   public void testNext()
   {
      Post p0 = createPost("my title", "my slug", Dater.now()).save();
      Post p1 = createPost("my title2", "my slug2", Dater.now()).save();
      Post p2 = createPost("my title3", "my slug3", Dater.now()).save();
      assertEquals(3, Post.count());

      assertSame(p1, p0.next());
      assertSame(p2, p1.next());
      assertNull(p2.next());
   }
}