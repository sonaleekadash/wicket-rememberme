package pl.tomaszdziurko.wicket.view;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import pl.tomaszdziurko.wicket.UserSession;
import pl.tomaszdziurko.wicket.WicketApplication;
import pl.tomaszdziurko.wicket.service.CookieService;
import pl.tomaszdziurko.wicket.service.SessionProvider;

public class HomePage extends BasePage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new FeedbackPanel("feedback"));

        Link<Void> logoutLink = new Link<Void>("logout") {
            @Override
            public void onClick() {
                CookieService cookieService = WicketApplication.get().getCookieService();
                cookieService.removeCookieIfPresent(getRequest(), getResponse(), SessionProvider.REMEMBER_ME_LOGIN_COOKIE);
                cookieService.removeCookieIfPresent(getRequest(), getResponse(), SessionProvider.REMEMBER_ME_PASSWORD_COOKIE);

                UserSession.get().setUser(null);
                UserSession.get().invalidate();
            }
        };
        logoutLink.setVisible(UserSession.get().userLoggedIn());
        add(logoutLink);

        BookmarkablePageLink<LoginPage> loginLink = new BookmarkablePageLink<LoginPage>("loginLink", LoginPage.class);
        loginLink.setVisible(UserSession.get().userNotLoggedIn());
        add(loginLink);

        String userName = UserSession.get().userLoggedIn() ? UserSession.get().getUser().getLogin() : "Anonymous user";
        add(new Label("username", userName));
    }

}
