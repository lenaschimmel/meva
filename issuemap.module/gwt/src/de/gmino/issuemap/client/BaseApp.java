package de.gmino.issuemap.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.gmino.issuemap.client.view.Footer;
import de.gmino.issuemap.client.view.Header;
import de.gmino.issuemap.client.view.backend.Login;
import de.gmino.issuemap.shared.domain.User;
import de.gmino.meva.client.UtilClient;
import de.gmino.meva.client.request.NetworkRequestsImplAsyncJson;
import de.gmino.meva.shared.EntityFactory;
import de.gmino.meva.shared.Log;
import de.gmino.meva.shared.Util;
import de.gmino.meva.shared.request.RequestListener;
import de.gmino.meva.shared.request.Requests;

public abstract class BaseApp implements EntryPoint, UncaughtExceptionHandler {

	protected Footer footer;
	protected Header header;
	protected DecoratedPopupPanel decorated_panel = new DecoratedPopupPanel();
	protected Login login = new Login();
	protected User loggedInUser;
	private boolean activeAutoLogin;
	
	public User getLoggedInUser() {
		return loggedInUser;
	}

	protected static BaseApp instance;

	public static BaseApp getInstance()
	{
		return instance;
	}
	
	public BaseApp(boolean activeAutoLogin) {
		super();
		this.activeAutoLogin = activeAutoLogin;
		footer = new Footer(decorated_panel);
		header = new Header(decorated_panel);
		header.setLoggedIn(false);
	}

	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(this);
		
		try {
			EntityFactory.setImplementations(new EntityFactoryImpl());
			Util.setImpl(new UtilClient());
			Requests.setImplementation(new NetworkRequestsImplAsyncJson("http://" + Location.getHost() + "/"));
	
			RootPanel.get("bar_top").add(header);
			RootPanel.get("bar_bottom").add(footer);
			
			Requests.checkLogin(new RequestListener<Long>() {
				@Override
				public void onNewResult(Long result) {
					if(result > 0)
						onLoggedInUserIdKnown(result);
					else if(activeAutoLogin)
						showLoginField();
				}
				
				@Override
				public void onError(String message, Throwable e) {
					if(activeAutoLogin)
						showLoginField();
				}
			});
			
			initApp();
		} catch (Exception e) {
			e.printStackTrace();
			Log.exception("Error in onModuleLoad caught.", e);
		}
	}

	@Override
	public void onUncaughtException(Throwable e) {
		System.err.println("### UncaughtExceptionHandler ###");
		e.printStackTrace();
		Log.exception("### UncaughtExceptionHandler ###", e);
	}
	
	protected abstract void initApp() throws Exception;
	public abstract void onLogin();
	public abstract void onLogout();

	public void showLoginField() {
		RootPanel.get("parent").add(login);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute() {
				login.user.setFocus(true);
			}
		});
	}
	
	public void onLoggedInUserIdKnown(Long loggedInUserId) {
		Requests.getLoadedEntityById(User.type, loggedInUserId, new RequestListener<User>() {
			@Override
			public void onNewResult(User user) {
				loggedInUser = user;
				header.setLoggedIn(true);
				onLogin();
				login.removeFromParent();
			}
		});
	}

	public void doLogin(String userName, String password) {
		Requests.login(userName, password, new RequestListener<Long>() {
			@Override
			public void onNewResult(Long loggedInUserId) {
				onLoggedInUserIdKnown(loggedInUserId);
			}
		});
	}
	
	public void doLogout()
	{
		Requests.logout();
		loggedInUser = null;
		header.setLoggedIn(false);
		onLogout();
	}
	
	public void doLogAction()
	{
		if(loggedInUser == null)
			showLoginField();
		else
			doLogout();
	}
}