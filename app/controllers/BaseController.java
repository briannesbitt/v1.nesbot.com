package controllers;

import helpers.Globals;
import play.mvc.Before;
import play.mvc.Controller;

public class BaseController extends Controller
{
   @Before
   public static void defaultVars()
   {
      renderArgs.put("urlbase", Globals.getUrlBaseWithSlash());
      renderArgs.put("urlcdn", Globals.getUrlCdnWithSlash());
      renderArgs.put("urlimages", Globals.getUrlImagesWithSlash());
      renderArgs.put("gatracker", Globals.getGoogleAnalyticsTracker());
   }
}