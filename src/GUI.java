import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        BorderPane middle = new BorderPane();
        middle.prefWidthProperty().bind(bp.widthProperty());
        BorderPane bottom = new BorderPane();
        bottom.prefWidthProperty().bind(bp.widthProperty());
        bottom.prefHeightProperty().bind(bp.heightProperty().multiply(0.5));

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

        top.setRight(dataset);
        top.setLeft(searchBox);

        Label searchNumbers = new Label();
        searchNumbers.setAlignment(Pos.TOP_LEFT);
        TableView<SearchResult> searchResults = new TableView<>();
        TextArea searchResult = new TextArea();
        searchResult.setWrapText(true);
        searchResult.setEditable(false);
        searchResult.prefWidthProperty().bind(bottom.widthProperty().multiply(0.35));
        searchResults.prefWidthProperty().bind(bottom.widthProperty().multiply(0.65));
        searchResults.setEditable(false);
        searchResults.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<SearchResult, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<SearchResult, String> nameColumn = new TableColumn<>("Name");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        idColumn.setMaxWidth(1000);
        searchResults.getColumns().addAll(idColumn, nameColumn);

        bottom.setTop(searchNumbers);
        bottom.setLeft(searchResults);
        bottom.setRight(searchResult);

        // actionevents (put here for context granted from creation of ui)
        dataButtons[0].setOnAction(actionEvent -> { data.load(); dataLabel.setText(data.size() + " apps loaded."); });
        dataButtons[1].setOnAction(actionEvent -> { data.fetch(); dataLabel.setText(data.size() + " apps loaded."); });
        searchGoButton.setOnAction(actionEvent -> {
            String searchText = searchBar.getText();
            if (searchText.length() != 0) {
                ObservableList<SearchResult> a = FXCollections.observableArrayList(data.nameSearch(searchText.toLowerCase()));
                searchResults.scrollTo(0);
                searchResults.setItems(a);
                searchNumbers.setText(a.size() + " items found.");
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
        bp.setCenter(middle);
        bp.setBottom(bottom);

        group.getChildren().add(bp);

        stage.setTitle("SteamCard");
        stage.setScene(scene);
        stage.show();
    }

    public static void launch() {
        Application.launch();
    }

}
