package jobs;

import com.nesbot.commons.datetime.Dater;
import ext.PostExtensions;
import models.Post;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.regex.*;

@OnApplicationStart
public class Startup extends Job
{
   public void doJob() throws IOException
   {
      play.Logger.info("Populating posts - start");

      Post.clear();

      TreeMap<Long, Post> posts = new TreeMap<Long, Post>();

      for (File f : new File(PostExtensions.postsPath).listFiles())
      {
         if (f.isFile())
         {
            Pattern filePattern = Pattern.compile("^([0-9]{4}-[0-9]{1,2}-[0-9]{1,2})-(.+)\\.html$");

            Matcher matcher = filePattern.matcher(f.getName());
            if (matcher.matches() && matcher.groupCount() == 2)
            {
               BufferedReader br = new BufferedReader(new FileReader(f.getCanonicalPath()));
               String s = br.readLine();
               br.close();

               if (s.startsWith("*{") && s.endsWith("}*"))
               {
                  Post p = new Post(s.substring(2, s.length()-2), matcher.group(2), Dater.create(matcher.group(1)).timestamp());
                  posts.put(p.updated, p);
               }
            }
         }
      }

      for (Post p : posts.descendingMap().values())
      {
         p.save();
      }

      play.Logger.info("Populating posts - done");
   }
}