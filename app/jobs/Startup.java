package jobs;

import com.nesbot.commons.datetime.Dater;
import models.Post;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import java.util.Date;

@OnApplicationStart
public class Startup extends Job
{
   public void doJob()
   {
      play.Logger.info("Populating posts - start");

      /******* Move all of this to MongoDB soon *******/

      Post.clear();

      new Post("Tricks for using the cobertura module with the Play Framework",
               "cobertura-module-tricks-with-the-play-framework",
               Dater.create(2011, 9, 20).timestamp()).save();

      new Post("ANSI colour support in Windows for the Play! Framework 2.0 preview",
               "ansi-colour-support-for-play-framework-2-preview",
               Dater.create(2011, 9, 9).timestamp()).save();

      new Post("Adding initial Windows support for the Play! Framework 2.0 preview",
               "windows-support-for-play-framework-2-preview",
               Dater.create(2011, 9, 8).timestamp()).save();

      new Post("A carpenter's house is always the last to get the attention it deserves",
               "carpenters-house-last-to-get-attention",
               Dater.create(2011, 9, 7).timestamp()).save();

      play.Logger.info("Populating posts - done");
   }
}