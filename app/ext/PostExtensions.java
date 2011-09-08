package ext;

import models.Post;
import play.templates.JavaExtensions;

public class PostExtensions extends JavaExtensions
{
   public static String tagName(Post post)
   {
      return "app/views/tags/posts/" + post.slug + ".html";
   }
   public static String url(Post post)
   {
      return JavaExtensions.format(post.date, "/yyyy/M/d/") + post.slug;
   }
}