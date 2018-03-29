import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

public class Main extends Application {

    private Fridge myFridge=new Fridge();

    private Stage window;

    private Scene baseScene;

    private static Date dateFromInt(int day, int month, int year ){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day);
        return cal.getTime();
    }

    private boolean isInt(String text){
        try{
            Integer.parseInt(text);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    private void baseWindow(){
        //Base scene
        Label labelBase= new Label("Choose action:");
        Button  addProductButton=new Button("Add product"); //creating buttons with text
        addProductButton.setOnAction(event ->addWindow());// handling the button events (click), here is the lambda expression min v8
        Button openProductsButton=new Button("List of opened products");
        openProductsButton.setOnAction(event -> listWindow("Opened products: ",0));
        Button expiringProductsButton= new Button("List of expiring products");
        expiringProductsButton.setOnAction(event -> listWindow("Expiring products: ",1));
        //Base Layout
        VBox layoutBase= new VBox(20);
        layoutBase.getChildren().addAll(labelBase,addProductButton, openProductsButton, expiringProductsButton);
        layoutBase.setAlignment(Pos.CENTER);
        baseScene=new Scene(layoutBase, 500,300); //creating scene with layout

        window.setScene(baseScene);

    }

    private void errorWindow(String message){
        Label labelError= new Label(message);
        Button goBackButtonError=new Button("Cancel");
        goBackButtonError.setOnAction(event -> window.setScene(baseScene));
        //Add layout
        VBox layoutError = new VBox(20);
        layoutError.getChildren().addAll(labelError, goBackButtonError);
        layoutError.setAlignment(Pos.CENTER);
        Scene errorScene=new Scene(layoutError, 500, 300);

        window.setScene(errorScene);
    }

    private void addWindow(){

        //TOP
        Label labelAdd= new Label("Details about product:");
        //Add layout
        VBox top = new VBox(20);
        top.getChildren().addAll(labelAdd);
        top.setAlignment(Pos.CENTER);

        //CENTER
        GridPane center= new GridPane();
        center.setPadding(new Insets(10,10,10,10));
        center.setVgap(8);
        center.setHgap(10);

        Label nameLabel= new Label("Name of product: ");
        GridPane.setConstraints(nameLabel,0,0);
        TextField nameInput= new TextField();
        GridPane.setConstraints(nameInput, 1,0);

        Label typeLabel= new Label("Type of product: ");
        GridPane.setConstraints(typeLabel,0,1);
        ChoiceBox<String> typeInput= new ChoiceBox<>();
        typeInput.getItems().addAll("VEGETABLES", "FRUITS", "DAIRY", "MEAT", "DRINKS");
        typeInput.setValue("VEGETABLES");
        GridPane.setConstraints(typeInput, 1,1);

        Label purchaseDateLabel= new Label("Purchase date: ");
        GridPane.setConstraints(purchaseDateLabel,0,2);
        TextField pDayInput= new TextField();
        pDayInput.setPromptText("dd");
        GridPane.setConstraints(pDayInput, 0,3);
        TextField pMonthInput= new TextField();
        pMonthInput.setPromptText("mm");
        GridPane.setConstraints(pMonthInput, 1,3);
        TextField pYearInput= new TextField();
        pYearInput.setPromptText("yyyy");
        GridPane.setConstraints(pYearInput, 2,3);

        Label expiryDateLabel= new Label("Expiry date: ");
        GridPane.setConstraints(expiryDateLabel,0,4);
        TextField eDayInput= new TextField();
        eDayInput.setPromptText("dd");
        GridPane.setConstraints(eDayInput, 0,5);
        TextField eMonthInput= new TextField();
        eMonthInput.setPromptText("mm");
        GridPane.setConstraints(eMonthInput, 1,5);
        TextField eYearInput= new TextField();
        eYearInput.setPromptText("yyyy");
        GridPane.setConstraints(eYearInput, 2,5);

        Label isOpenedLabel= new Label("Is opened?: ");
        GridPane.setConstraints(isOpenedLabel,0,6);
        CheckBox isOpenedInput=new CheckBox();
        isOpenedInput.setSelected(true);
        GridPane.setConstraints(isOpenedInput, 1,6);


        center.getChildren().addAll(nameLabel,nameInput, typeLabel, typeInput,
                purchaseDateLabel, pDayInput,pMonthInput, pYearInput,
                expiryDateLabel, eDayInput,eMonthInput,eYearInput, isOpenedLabel, isOpenedInput);



        center.setAlignment(Pos.CENTER);

        //BOTTOM
        Button addButton=new Button("Add");
        addButton.setOnAction(event -> {
            if(nameInput.getText().isEmpty()){errorWindow("NO NAME");}
            else{

                if(!(isInt(pDayInput.getText())&& isInt(pMonthInput.getText()) && isInt(pYearInput.getText())
                        && isInt(eDayInput.getText())&& isInt(eMonthInput.getText()) && isInt(eYearInput.getText()))){
                    errorWindow("WRONG DATE"); }
                else {
                    String name=nameInput.getText();
                    productType type= productType.valueOf(typeInput.getValue());
                    Date purchaseDate=dateFromInt(Integer.parseInt(pDayInput.getText()),Integer.parseInt(pMonthInput.getText()),Integer.parseInt(pYearInput.getText()));
                    Date expiryDate=dateFromInt(Integer.parseInt(eDayInput.getText()),Integer.parseInt(eMonthInput.getText()),Integer.parseInt(eYearInput.getText()));
                    boolean isOpened=isOpenedInput.isSelected();
                    myFridge.addObject(new Product(name,type,expiryDate,purchaseDate,isOpened));
                    window.setScene(baseScene);}}});

        Button goBackButtonAdd=new Button("Cancel");
        goBackButtonAdd.setOnAction(event -> window.setScene(baseScene));

        HBox bottom= new HBox(20);
        bottom.getChildren().addAll(addButton, goBackButtonAdd);
        bottom.setAlignment(Pos.CENTER);


        BorderPane borderPane=new BorderPane();
        borderPane.setTop(top);
        borderPane.setBottom(bottom);
        borderPane.setCenter(center);
        Scene addScene=new Scene(borderPane, 500, 300);

        window.setScene(addScene);
    }

    private void listWindow(String text,int i){
        //Displaying list of opened products scene
        Label labelOpened= new Label(text);
        Button goBackButtonList=new Button("Cancel");
        goBackButtonList.setOnAction(event -> window.setScene(baseScene));
        //Add layout
        List <Product> temp=new ArrayList<>();
        if(i==0){temp=myFridge.getOpenedObjects();}
        else{temp=myFridge.getExpiringObjects(7);}
        ListView <String> listView=new ListView<>();
        for(Product t: temp){
            listView.getItems().add(t.toString());}

        VBox layoutOpened = new VBox(20);
        layoutOpened.getChildren().addAll(labelOpened,listView, goBackButtonList);
        layoutOpened.setAlignment(Pos.CENTER);
        Scene listScene=new Scene(layoutOpened, 500, 300);

        window.setScene(listScene);
    }

    public static void main(String[] args) {

      launch(args); //go into Application, always when you want to use javafx (->GUI)
    }

    @Override
    public void start(Stage primaryStage) throws Exception  {
        window=primaryStage;  //window with title
        primaryStage.setTitle("First GUI project");

        baseWindow();

        window.show();
    }

}
