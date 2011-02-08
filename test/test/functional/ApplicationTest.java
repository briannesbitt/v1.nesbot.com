package test.functional;

import org.junit.Test;

public class ApplicationTest extends BaseFunctionalTest
{
   @Test
   public void testIndexPage()
   {
      assertPageLoadsOk("/");
   }
}