package jobs;

import models.Post;
import org.joda.time.DateTime;
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
      new Post("Adding initial windows support for the Play! Framework 2.0 preview",
               "windows-support-for-play-framework-2-preview",
               new Date(new DateTime(2011, 9, 8, 0, 0, 0, 0).getMillis())).save();

      new Post("A carpenter's house is always the last to get the attention it deserves",
               "carpenters-house-last-to-get-attention",
               new Date(new DateTime(2011, 9, 7, 0, 0, 0, 0).getMillis())).save();

      play.Logger.info("Populating posts - done");
   }
}