package main.design;

import com.vaadin.ui.*;

public class LoginDesign extends VerticalLayout {
	private FormLayout formLayout = new FormLayout();
	protected Label label = new Label("Welcome");
	protected TextField login = new TextField("Login");
	protected PasswordField password = new PasswordField("Password");
	protected Button loginButton = new Button("Login");

	public LoginDesign() {
		setSizeFull();
		addStyleName("user-menu");

		formLayout.setSizeUndefined();
		addComponent(formLayout);
		setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);

		label.setSizeUndefined();
		label.addStyleName("colored");
		label.addStyleName("h2");
		formLayout.addComponent(label);

		formLayout.addComponent(login);
		formLayout.addComponent(password);

		loginButton.addStyleName("primary");
		formLayout.addComponent(loginButton);
	}
}
