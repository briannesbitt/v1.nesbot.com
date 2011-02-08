package controllers;

import helpers.Globals;
import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller
{
   @Before
   public static void defaultVars()
   {
      renderArgs.put("urlcdn", Globals.getUrlCdn());
      renderArgs.put("urlimages", Globals.getUrlImages());
      renderArgs.put("gatracker", Globals.getGoogleAnalyticsTracker());
   }
}