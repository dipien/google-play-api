package com.jdroid.android.googleplay.publisher;

import org.junit.Assert;
import org.junit.Test;

public class AppContextTest {
	
	@Test
	public void testUserFraction() {
		AppContext appContext = new AppContext();
		appContext.setUserFraction(0.001);
		Assert.assertEquals(0.1, appContext.getUserPercentage(), 0);
		
		appContext = new AppContext();
		appContext.setUserFraction(0.01);
		Assert.assertEquals(1, appContext.getUserPercentage(), 0);
		
		appContext = new AppContext();
		appContext.setUserFraction(0.1);
		Assert.assertEquals(10, appContext.getUserPercentage(), 0);
		
		appContext = new AppContext();
		appContext.setUserFraction(0.5);
		Assert.assertEquals(50, appContext.getUserPercentage(), 0);
	}
	
	@Test
	public void testUserPercentage() {
		AppContext appContext = new AppContext();
		appContext.setUserPercentage(0.1);
		Assert.assertEquals(0.001, appContext.getUserFraction(), 0);
		
		appContext = new AppContext();
		appContext.setUserPercentage(1D);
		Assert.assertEquals(0.01, appContext.getUserFraction(), 0);
		
		appContext = new AppContext();
		appContext.setUserPercentage(10D);
		Assert.assertEquals(0.1, appContext.getUserFraction(), 0);
		
		appContext = new AppContext();
		appContext.setUserPercentage(50D);
		Assert.assertEquals(0.5, appContext.getUserFraction(), 0);
	}
}
