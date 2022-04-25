package ui;

import businessLogic.BlFacade;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import uicontrollers.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainGUI {

  private Window mainLag, registerLag, loginLag, adminViewLag, userViewLag,
          browseQuestionsLag, createQuestionsLag, createRemoveEventLag, createFeeLag, betLag,
          publishResultLag;

  private BlFacade businessLogic;
  private Stage stage;
  private Scene scene;

  public BlFacade getBusinessLogic() {
    return businessLogic;
  }

  public void setBusinessLogic(BlFacade afi) {
    businessLogic = afi;
  }

  public MainGUI(BlFacade bl) {
    Platform.startup(() -> {
      try {
        setBusinessLogic(bl);
        init(new Stage());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }


  class Window {
    Controller c;
    Parent ui;
  }

  private Window load(String fxmlfile) throws IOException {
    Window window = new Window();
    FXMLLoader loader = new FXMLLoader(MainGUI.class.getResource(fxmlfile), ResourceBundle.getBundle("Etiquetas", Locale.getDefault()));
    loader.setControllerFactory(controllerClass -> {

      if (controllerClass == LogInController.class) {
        return new LogInController(businessLogic);
      }

      if (controllerClass == AdminViewController.class) {
        return new AdminViewController(businessLogic);
      }

      if (controllerClass == UserViewController.class) {
        return new UserViewController(businessLogic);
      }

      if (controllerClass == BrowseQuestionsController.class) {
        return new BrowseQuestionsController(businessLogic);
      }

      if (controllerClass == betController.class) {
        return new betController(businessLogic);
      }

      if (controllerClass == CreateQuestionController.class) {
        return new CreateQuestionController(businessLogic);
      }

      if (controllerClass == SetFeeController.class) {
        return new  SetFeeController(businessLogic);
      }
      if (controllerClass == PublishResultController.class) {
        return new PublishResultController(businessLogic);
      }

      if (controllerClass == AddRemoveController.class) {
        return new AddRemoveController(businessLogic);
      }

      if (controllerClass == SignUpController.class) {
        return new SignUpController(businessLogic);
      } else {
        // default behavior for controllerFactory:
        try {
          return controllerClass.getDeclaredConstructor().newInstance();
        } catch (Exception exc) {
          exc.printStackTrace();
          throw new RuntimeException(exc); // fatal, just bail...
        }
      }


    });
    window.ui = loader.load();
    ((Controller) loader.getController()).setMainApp(this);
    window.c = loader.getController();
    return window;
  }

  public void init(Stage stage) throws IOException {

    this.stage = stage;

    mainLag = load("/MainGUI.fxml");
    loginLag = load("/login-view.fxml");
    registerLag = load("/signup-view.fxml");
    adminViewLag = load("/admin-view.fxml");
    userViewLag = load("/user-view.fxml");
    browseQuestionsLag = load("/BrowseQuestions.fxml");
    createQuestionsLag = load("/CreateQuestion.fxml");
    createRemoveEventLag = load("/CreateRemoveEvent-view.fxml");

    createFeeLag = load("/SetFee-view.fxml");
    betLag = load("/bet-view.fxml");
    publishResultLag = load("/publish-result-view.fxml");

    showMain();

  }

//  public void start(Stage stage) throws IOException {
//      init(stage);
//  }


  public void showMain(){
    setupScene(mainLag.ui, "MainTitle", 363, 282);
  }

  public void showRegister() {
    setupScene(registerLag.ui, "Register", 432, 742);
  }

  public void showLogin() {
    setupScene(loginLag.ui, "Login", 385, 400);
  }

  public void showBrowseQ() {
    setupScene(browseQuestionsLag.ui, "BrowseQuestions", 996, 420);
  }

  public void showCreateQ() {
    setupScene(createQuestionsLag.ui, "CreateQuestion", 650, 400);
  }

  public void showCreateFee() {
    setupScene(createFeeLag.ui, "CreateFee", 650, 430);
  }

  public void showAdminView() { setupScene(adminViewLag.ui, "Admin", 625, 400);}

  public void showPublishResult() { setupScene(publishResultLag.ui, "PublishResult", 750, 800);}


  public void showUserView() {
    ((UserViewController)userViewLag.c).setUser();
    setupScene(userViewLag.ui, "MainTitle", 615, 650);
  }

  public void showBetView() {
    ((betController)betLag.c).setUser();
    setupScene(betLag.ui, "Bet", 750, 865);
  }

  public void showCreateEventView() { setupScene(createRemoveEventLag.ui, "CreateEvent", 700, 610);}

  private void setupScene(Parent ui, String title, int width, int height) {
    if (scene == null){
      scene = new Scene(ui, width, height);
      scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
      stage.setScene(scene);
    }
    stage.setWidth(width);
    stage.setHeight(height);
    stage.setTitle(ResourceBundle.getBundle("Etiquetas",Locale.getDefault()).getString(title));
    scene.setRoot(ui);
    stage.show();
  }

//  public static void main(String[] args) {
//    launch();
//  }
}
