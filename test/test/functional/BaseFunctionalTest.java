package test.functional;

import play.mvc.Http;
import play.test.FunctionalTest;

public abstract class BaseFunctionalTest extends FunctionalTest
{
   protected Http.Response latestResponse;

   protected void assertPageLoadsOk(String url)
   {
      latestResponse = GET(url);
      assertIsOk(latestResponse);
      assertContentType("text/html", latestResponse);
      assertCharset("utf-8", latestResponse);
   }
}