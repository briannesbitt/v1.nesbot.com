package controllers;

import models.Post;

import java.util.Collection;

public class Application extends BaseController
{
   private static String postTagName(Post post)
   {
      return "app/views/tags/posts/" + post.slug + ".html";
   }
   public static void index()
   {
      Collection<Post> posts = Post.findAll();
      render(posts);
   }
   public static void show(Long year, Long month, Long day, String slug)
   {
      Post post = Post.findBySlug(slug);

      if (post == null)
      {
         notFound(slug);
      }

      if (!templateExists(postTagName(post)))
      {
         notFound("template for : " + postTagName(post));
      }

      render(post);
   }
   public static void rss(String subject)
   {
      Collection<Post> posts = Post.findAll();
      response.contentType = "application/rss+xml";
      render(posts);
   }
}