import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GUI extends Application {

    private static final Font FONT = new Font(20);
    private static final Color PRIMARY = Color.WHITE;
    private static final Color SECONDARY = Color.BLACK;

    private static final Logger log = LogManager.getLogger("GUI");


    public static Dataset data = new Dataset();

    @Override public void start(Stage stage) throws Exception {
        log.debug("Starting GUI");
        Group group = new Group();
        Scene scene = new Scene(group, 480, 640);

        BorderPane bp = new BorderPane();
        bp.prefWidthProperty().bind(scene.widthProperty());
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.setPadding(new Insets(10, 10, 10, 10));

        BorderPane top = new BorderPane();
        top.prefWidthProperty().bind(bp.widthProperty());
        BorderPane bot = new BorderPane();
        bot.prefWidthProperty().bind(bp.widthProperty());
        BorderPane mid = new BorderPane();
        mid.prefWidthProperty().bind(bp.widthProperty());
        mid.prefHeightProperty().bind(bp.heightProperty().multiply(0.5));

        Label dataLabel = new Label(data.size() + " apps loaded.");
        Button[] dataButtons = new Button[] {
            new Button("Load"),
            new Button("Fetch")
        };
        VBox dataButtonBox = new VBox(dataButtons[0], dataButtons[1]);
        dataButtons[0].prefWidthProperty().bind(dataButtonBox.widthProperty());
        dataButtons[1].prefWidthProperty().bind(dataButtonBox.widthProperty());
        dataButtonBox.setAlignment(Pos.CENTER_RIGHT);
        VBox dataset = new VBox(dataLabel, dataButtonBox);

        Label searchBarLabel = new Label("Search by Name");
        TextField searchBar = new TextField();

        Button searchGoButton = new Button("Search");
        HBox searchBarBox = new HBox(searchBar, searchGoButton);
        searchBarBox.setSpacing(5);
        VBox searchBox = new VBox(searchBarLabel, searchBarBox);

        Label sortLabel = new Label("Sort by ");
        ChoiceBox<String> sortDirectionChoice = new ChoiceBox<>(FXCollections.observableArrayList("best", "worst"));
        ChoiceBox<String> sortChoice = new ChoiceBox<>(FXCollections.observableArrayList("booster", "three card avg", "gem"));
        Label sortLabel2 = new Label(" ratios.");
        Button sortButton = new Button("Sort");

        HBox sortButtons = new HBox(sortLabel, sortDirectionChoice, sortChoice, sortLabel2);
        HBox sortButtonsBox = new HBox(sortButtons, sortButton);
        sortButtonsBox.setSpacing(5);
        VBox trBox = new VBox(searchBox, sortButtonsBox);
        trBox.setSpacing(5);

        top.setRight(dataset);
        top.setLeft(trBox);

        Label searchNumbers = new Label();
        searchNumbers.setAlignment(Pos.TOP_LEFT);
        TableView<SearchResult> searchResults = new TableView<>();
        TextArea searchResult = new TextArea();
        searchResult.setWrapText(true);
        searchResult.setEditable(false);
        searchResult.prefWidthProperty().bind(mid.widthProperty().multiply(0.35));
        searchResults.prefWidthProperty().bind(mid.widthProperty().multiply(0.65));
        searchResults.setEditable(false);
        searchResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<SearchResult, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<SearchResult, String> nameColumn = new TableColumn<>("Name");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setMaxWidth(1000);
        searchResults.getColumns().addAll(idColumn, nameColumn);

        mid.setTop(searchNumbers);
        mid.setLeft(searchResults);
        mid.setRight(searchResult);

        // actionevents (put here for context granted from creation of ui)
        dataButtons[0].setOnAction(actionEvent -> {
            dataButtons[0].setDisable(true);
            dataButtons[1].setDisable(true);
            data.load();
            dataLabel.setText(data.size() + " apps loaded.");
            dataButtons[0].setDisable(false);
            dataButtons[1].setDisable(false);
        });
        dataButtons[1].setOnAction(actionEvent -> {
            dataButtons[0].setDisable(true);
            dataButtons[1].setDisable(true);
            data.fetch();
            dataLabel.setText(data.size() + " apps loaded.");
            dataButtons[0].setDisable(false);
            dataButtons[1].setDisable(false);
        });
        searchGoButton.setOnAction(actionEvent -> {
            String searchText = searchBar.getText();
            if (searchText.length() != 0) {
                ObservableList<SearchResult> a = FXCollections.observableArrayList(data.nameSearch(searchText.toLowerCase()));
                searchResults.scrollTo(0);
                searchResults.setItems(a);
                searchNumbers.setText(a.size() + " items found.");
            }
        });
        sortButton.setOnAction(actionEvent -> {
            boolean searchDir = sortDirectionChoice.getValue() != "best";
            String selSort = sortChoice.getValue();
            ObservableList<SearchResult> a;
            switch(selSort) {
                case "booster":
                    a = FXCollections.observableArrayList(data.boosterSort(searchDir));
                    searchResults.scrollTo(0);
                    searchResults.setItems(a);
                    break;
                case "gem":
                    a = FXCollections.observableArrayList(data.gemSort(searchDir));
                    searchResults.scrollTo(0);
                    searchResults.setItems(a);
                    break;
                case "three card avg":
                    a = FXCollections.observableArrayList(data.threeCardSort(searchDir));
                    searchResults.scrollTo(0);
                    searchResults.setItems(a);
                    break;
            }
        });
        searchResults.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, sr, t1) -> {
                if (t1 != null) {
                    searchResult.setText(data.apps.get(t1.getId()).flatString());
                }
            });


        // box organization
        bp.setTop(top);
        bp.setCenter(mid);
        bp.setBottom(bot);

        group.getChildren().add(bp);

        stage.setTitle("SteamCard");
        stage.setScene(scene);
        stage.show();
    }

    public static void launch() {
        Application.launch();
    }

}
