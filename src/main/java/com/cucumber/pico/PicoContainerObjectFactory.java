package com.cucumber.pico;

import com.cucumber.actions.StepActions;
import com.cucumber.base.PageContext;
import com.cucumber.pages.ArticleDetailsPage;
import com.cucumber.pages.LandingPage;
import com.cucumber.pages.LoginPage;
import com.cucumber.pages.MyAccountPage;
import com.cucumber.pages.common.CommonPage;

import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.picocontainer.PicoFactory;


public class PicoContainerObjectFactory implements ObjectFactory {

	private final PicoFactory delegate = new PicoFactory();

	public PicoContainerObjectFactory() {

		addClass(LandingPage.class);
		addClass(LoginPage.class);
		addClass(StepActions.class);
		addClass(ArticleDetailsPage.class);
		addClass(CommonPage.class);
		addClass(MyAccountPage.class);
		try {
			addClass(PageContext.class);
			
		}catch (Exception e) {
			System.err.println("Control in catch block");
		}
		
	}

	@Override
	public void start() {
		delegate.start();
	}

	@Override
	public void stop() {
		delegate.stop();
	}

	@Override
	public boolean addClass(Class<?> clazz) {
		return delegate.addClass(clazz);
	}

	@Override
	public <T> T getInstance(Class<T> clazz) {
		return delegate.getInstance(clazz);
	}

}
