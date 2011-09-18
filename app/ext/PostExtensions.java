package ext;

import com.nesbot.commons.datetime.Dater;
import helpers.Globals;
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
      return Dater.create(post.updated).toString("/yyyy/M/d/") + post.slug;
   }
   public static String fullUrl(Post post)
   {
      return Globals.getUrlBaseNoSlash() + url(post);
   }
   public static String prettyUpdated(Post post)
   {
      return Dater.create(post.updated).toString("MMMM d, yyyy");
   }
   public static String toRFC822(Post post)
   {
      return Dater.create(post.updated).toRFC822();
   }
}