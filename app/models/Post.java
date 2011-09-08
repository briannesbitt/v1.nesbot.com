package models;

import java.util.*;

public class Post
{
   public String title;
   public String slug;
   public Date date;

   private static HashMap<String, Post> posts = new LinkedHashMap<String, Post>();

   public Post(String title, String slug, Date date)
   {
      this.title = title;
      this.slug = slug;
      this.date = date;
   }

   public static Post findBySlug(String slug)
   {
      return posts.containsKey(slug) ? posts.get(slug) : null;
   }
   public static List<Post> findAll()
   {
      return new ArrayList<Post>(posts.values());
   }

   public void save()
   {
      posts.put(slug, this);
   }
   public void delete()
   {
      posts.remove(slug);
   }
}