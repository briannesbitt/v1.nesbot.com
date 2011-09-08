package controllers;

import ext.PostExtensions;
import models.Post;

import java.util.Collection;

public class Application extends BaseController
{
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

      if (!templateExists(PostExtensions.tagName(post)))
      {
         notFound("template for : " + PostExtensions.tagName(post));
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