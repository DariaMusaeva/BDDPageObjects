package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public final SelenideElement loginField = $("[data-test-id=login] input");
    public final SelenideElement passField = $("[data-test-id=password] input");
    public final SelenideElement button = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passField.setValue(info.getPass());
        button.click();
        return new VerificationPage();
    }
}
