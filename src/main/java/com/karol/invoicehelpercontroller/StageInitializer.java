package com.karol.invoicehelpercontroller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<InvoiceApplication.StageReadyEvent> {

    @Value("classpath:/mainScene.fxml")
    private Resource sceneResource;
    private ApplicationContext applicationContext;

    public StageInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(InvoiceApplication.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(sceneResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 800, 600));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
