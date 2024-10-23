package Interface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import DataStructures.SingleLinkedList;
import algorithim.Graph;
import algorithim.Location;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {

	Graph graph;
	Pane mapPane;
	// array to keep track of location markers
	LocationMarker[] locationMarkers;

	// lists for target and source
	ObservableList<String> targetList;
	ObservableList<String> sourceList;

	Polyline road;
	ComboBox<String> sourceComboBox;
	ComboBox<String> targetComboBox;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {

		/***
		 * set up entry scene
		 */

		mapPane = new Pane();

		Label label = new Label("Dijkstra Gaza");

		Button fileButton = new Button("Upload Locations !");

		fileButton.setOnAction(e -> {

			// create a flag for error and error description to be shown
			boolean errorExist = false;
			String erorInfo = "";

			// read text file and ensure it is chosen correctly
			try {

				File selectedFile = chooseFile(primaryStage);
				Scanner scan;

				scan = new Scanner(selectedFile);

				// get file extension and get the file name
				String fileName = selectedFile.getName();
				// Extract the extension from the file name
				int index = fileName.lastIndexOf('.');
				String extension = fileName.substring(index + 1);

				if (extension.equals("csv"))
					readGraphCSV(scan);
				else
					readGraphtxt(scan);

			System.out.println(graph.toString());

			} catch (FileNotFoundException e1) {
				errorExist = true;
				erorInfo = "File Not Found";

			} catch (NullPointerException e2) {
				errorExist = true;
				erorInfo = "No file Choosen";

			}

			if (errorExist) {
				Alert alert2 = new Alert(AlertType.ERROR);
				alert2.setTitle("File Error info");
				alert2.setHeaderText("There was an error in reading the sequence from the file");
				alert2.setContentText(erorInfo);
				alert2.showAndWait();
			}

			designMapScene(primaryStage);
		});

		VBox box = new VBox(30);
		box.getChildren().addAll(label, fileButton);
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(100));

		Scene scene = new Scene(box, 1000, 700);
		scene.getStylesheets().add("styles.css");

		primaryStage.setScene(scene);

		primaryStage.setTitle("Dijkstra Gaza");

		// Show the stage
		primaryStage.show();

		// designMapScene(primaryStage);

	}

	private void designMapScene(Stage primaryStage) {
		// TODO Auto-generated method stub
		double preferedMapHeight = 850;

		HBox splitPane = new HBox();
		splitPane.setMinHeight(preferedMapHeight);

		// mapPane = new Pane();

		Image image = null;
		try {
			//image = new Image("mymap1.jpg");
			image = new Image("mymap4.jpg");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Calculate aspect ratio of the image
		double ratio = image.getWidth() / image.getHeight();

		// Calculate the width based on the desired heightto preserve aspect ratio
		double imageWidth = preferedMapHeight * ratio;

		// Set the width and height of mapPane same as image
		mapPane.setPrefWidth(imageWidth);
		mapPane.setPrefHeight(preferedMapHeight);
		mapPane.setMaxWidth(imageWidth);
		mapPane.setMaxHeight(preferedMapHeight);

		// Create a BackgroundImage
		BackgroundImage backgroundImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				new BackgroundSize(BackgroundSize.AUTO, preferedMapHeight, false, false, false, false)

		);

		// Create a Background with the image
		Background background = new Background(backgroundImg);

		// Set the background for the Pane
		mapPane.setBackground(background);
		mapPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

		// Wxmax=

		/**
		 * set up controllers
		 */

		// Create a GridPane
		GridPane gridPane = new GridPane();
		gridPane.setHgap(20);
		gridPane.setVgap(30);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(100));

		// First row
		Label sourceLabel = new Label("Source:");
		sourceComboBox = new ComboBox<>();
		sourceComboBox.setPrefWidth(150);
		gridPane.add(sourceLabel, 0, 0);
		gridPane.add(sourceComboBox, 1, 0);

		// Second row
		Label targetLabel = new Label("Target:");
		targetComboBox = new ComboBox<>();
		targetComboBox.setPrefWidth(150);
		gridPane.add(targetLabel, 0, 1);
		gridPane.add(targetComboBox, 1, 1);

		// Third row
		Button runButton = new Button("Run");
		runButton.setPrefWidth(220);
		// runButton.setAlignment(Pos.BOTTOM_CENTER);
		GridPane.setHalignment(runButton, HPos.CENTER);
		GridPane.setColumnSpan(runButton, 2);
		gridPane.add(runButton, 0, 2);

		// Fourth row
		Label distanceLabel = new Label("Distance:");
		TextField distanceTextField = new TextField();
		gridPane.add(distanceLabel, 0, 3);
		gridPane.add(distanceTextField, 1, 3);

		// Fifth row
		Label pathLabel = new Label("Path:");
		TextArea pathTextField = new TextArea();

		// Set the preferred height of the TextArea (optional)
		pathTextField.setPrefRowCount(2);
		pathTextField.setPrefWidth(150);

		GridPane.setRowSpan(pathTextField, 2);
		gridPane.add(pathLabel, 0, 4);
		gridPane.add(pathTextField, 1, 4);

		// Sixth row
		Button resetButton = new Button("Reset");
		resetButton.setPrefWidth(220);
		// runButton.setAlignment(Pos.BOTTOM_CENTER);
		GridPane.setHalignment(resetButton, HPos.CENTER);
		GridPane.setColumnSpan(resetButton, 2);
		gridPane.add(resetButton, 0, 6);

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(50));
		vbox.setSpacing(50);
		vbox.setAlignment(Pos.CENTER);

		splitPane.getChildren().addAll(mapPane, gridPane);

		/**
		 * The Actual Action
		 */

		/**
		 * after reading graph
		 */

		// splitPane.getItems().addAll(mapPane, vbox);

		// vbox.setMinWidth(550);

		System.out.println(image.getHeight());
		Scene mapScene = new Scene(splitPane, 1200, preferedMapHeight);
		mapScene.getStylesheets().add("styles.css");

		// welcomeScene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);

		primaryStage.setScene(mapScene);

		// primaryStage.setFullScreen(true);

		primaryStage.show();

		// reset coordinates of map in pane
		printCornerCoordinates(mapPane);
		// LocationMarker.Wxmax = imageWidth;
		// LocationMarker.Wymax = preferedMapHeight;

		// System.out.println("used these xmax:" + LocationMarker.Wxmax + "," +
		// LocationMarker.Wymax);

		LocationMarker location = new LocationMarker(31.501695, 34.466845);
		// LocationMarker location = new LocationMarker(31.29678,34.243482);

		Button fileBtn = new Button("file");
		fileBtn.setOnAction(e -> {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("File info");
			alert.setHeaderText("This program expects a file of specific format");
			alert.setContentText("The file on the first line should contain the number of light and then "
					+ "followed by the sequence of lights where each integer is written on seperate line."
					+ " An Example for 3 lights :\n3\n2\n1\n3\n");
			alert.showAndWait();

			// create a flag for error and error description to be shown
			boolean errorExist = false;
			String erorInfo = "";
			int temp[] = null;

			// read text file and ensure it is chosen correctly
			// try {

			File selectedFile = chooseFile(primaryStage);
			Scanner scan;
			try {
				scan = new Scanner(selectedFile);
				readGraphCSV(scan);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/*
			 * // check of errors while reading try {
			 * 
			 * 
			 * 
			 * } catch (NumberFormatException e2) { errorExist = true; //erorInfo =
			 * "Element on line " + line + " is not a valid number";
			 * 
			 * } catch (InputMismatchException e4) { errorExist = true; //erorInfo =
			 * "Element on line " + line + " is not a valid number";
			 * 
			 * } finally { scan.close(); }
			 */
			/*
			 * } catch (FileNotFoundException e1) { errorExist = true; erorInfo =
			 * "File Not Found";
			 * 
			 * } catch (NullPointerException e2) { errorExist = true; erorInfo =
			 * "No file Choosen";
			 * 
			 * } // catch (Exception e3) { // errorExist = true; // erorInfo =
			 * e3.toString();
			 * 
			 * // }
			 * 
			 * if (errorExist) { Alert alert2 = new Alert(AlertType.ERROR);
			 * alert2.setTitle("File Error info"); alert2.
			 * setHeaderText("There was an error in reading the sequence from the file");
			 * alert2.setContentText(erorInfo); alert2.showAndWait(); }
			 */
		});
		// File selectedFile2 = new File(
		// "/Users/sarahhassouneh/Documents/BZU/ThirdYear_SemesterOne/info/Test4commacopy.csv");
		// File selectedFile = chooseFile(primaryStage);
		/*
		 * Scanner scan2 = null; try { scan2 = new Scanner(selectedFile2); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * readGraphCSV(scan2);
		 */

		/**
		 * finish reading graph
		 */

		targetComboBox.setItems(targetList);
		sourceComboBox.setItems(sourceList);

		// Polyline road;

		runButton.setOnAction(e -> {

			if (sourceComboBox.getValue() != null && targetComboBox.getValue() != null) {
				String formatted = String.format("%.2f",
						graph.dijkstra(sourceComboBox.getValue(), targetComboBox.getValue()));
				distanceTextField.setText(formatted + " km");

				SingleLinkedList<Location> vertices = graph.dijkstraPath(sourceComboBox.getValue(),
						targetComboBox.getValue());
				double[] points = new double[vertices.size() * 2];

				int i = 0;
				for (Location v : vertices) {
					//points[i] = locationMarkers[v].x;
					points[i]=LocationMarker.calculateX(v.getLongitude());
					//points[++i] = locationMarkers[v].y; 
					
					points[++i]=LocationMarker.calculateY(v.getLatitude());

					i++;
				}

				road = new Polyline(points);
				// road.setStroke(Color.BLACK); // Set line color
				road.setStrokeWidth(4.5); // S
				road.setOpacity(0.5);
				// road.setStroke(Color.rgb(0, 0, 0, 0.3)); // Adjust the alpha value (0.0 to
				// 1.0)
				road.setStyle("-fx-stroke: #014501; -fx-stroke-opacity: 0.1;");

				road.toBack();
				// mapPane.getChildren().add(road);
				mapPane.getChildren().add(0, road); // Add it at the beginning of the list

				// path
				String path = "";
				for (Location v : vertices) {

					if (v.isCity()) 
						path += v.getName() + ">>";

				}
				pathTextField.setText(path);

				runButton.setDisable(true);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Source - Target Error");
				alert.setHeaderText("Please chose a valid source and target to continue");
				alert.showAndWait();
			}

		});

		resetButton.setOnAction(e2 -> {
			runButton.setDisable(false);

			sourceComboBox.setValue(null);
			targetComboBox.setValue(null);
			distanceTextField.clear();
			pathTextField.clear();
			mapPane.getChildren().remove(road);

			click = 0;
		});

		Line line = new Line();
		line.setStartX(locationMarkers[0].x);
		line.setStartY(locationMarkers[0].y);
		line.setEndX(locationMarkers[1].x);
		line.setEndY(locationMarkers[1].y);
		line.setStroke(Color.BLUE);

		// Create a Pane to hold the circles and line
		// mapPane.getChildren().addAll( line);
		line.toBack();

		int test[] = { 0, 1, 2, 3, 4, 5 };

		// drawRoad(locationMarkers,test,0,1);
		// reading graph

		// test
		// graph.dijkstra("Khuza'a", "Rafah");
		// test
		// ArrayList<Integer> test2 = graph.dijkstra("Khuza'a", "Rafah");

		/*
		 * int[] intArray = new int[test2.size()];
		 * 
		 * for (int i = 0; i < test2.size(); i++) { intArray[i] = test2.get(i); }
		 */

		// drawRoad(locationMarkers,intArray,0);

		// System.out.println("look here: "+test2);
		// Integer[] pathTest = (Integer[]) graph.findShortestPath("", "").toArray();
		// System.out

		// graph.addAdjacent("Gaza", "Rafah");// works
		// System.out.println(graph.toString());

		primaryStage.setResizable(false);

	}

	private void designWelcomePane(Stage primaryStage) {
		// TODO Auto-generated method stub
		// Create a label
		Label label = new Label("Hello, JavaFX!");

		// Create a button
		Button button = new Button("Click Me!");

		// Set an action for the button
		button.setOnAction(e -> label.setText("Button Clicked!"));

		// Create a layout and add the label and button to it
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, button);

		// Create a scene with the layout
		Scene scene = new Scene(layout, 300, 150);

		// Set the scene to the stage
		primaryStage.setScene(scene);

		// Set the title of the stage
		primaryStage.setTitle("Simple JavaFX App");

		// Show the stage
		primaryStage.show();

	}

	private void drawRoad(LocationMarker[] locationMarkers, int[] sequence, int count) {
		// TODO Auto-generated method stub

		if (count + 1 >= sequence.length)
			return;

		Line line = new Line();
		line.setStartX(locationMarkers[sequence[count]].x);
		line.setStartY(locationMarkers[sequence[count]].y);
		line.setEndX(locationMarkers[sequence[count + 1]].x);
		line.setEndY(locationMarkers[sequence[count + 1]].y);
		line.setStrokeWidth(4);
		line.setStroke(Color.BROWN);

		// Create a Pane to hold the circles and line
		mapPane.getChildren().addAll(line);
		addArrow(line);
		line.toBack();

		drawRoad(locationMarkers, sequence, count + 1);

	}

	private void addArrow(Line line) {
		double arrowSize = 8;

		double angle = Math.atan2(line.getEndY() - line.getStartY(), line.getEndX() - line.getStartX());

		double x1 = line.getEndX() - arrowSize * Math.cos(angle - Math.toRadians(45));
		double y1 = line.getEndY() - arrowSize * Math.sin(angle - Math.toRadians(45));

		double x2 = line.getEndX() - arrowSize * Math.cos(angle + Math.toRadians(45));
		double y2 = line.getEndY() - arrowSize * Math.sin(angle + Math.toRadians(45));

		Polygon arrowHead = new Polygon(line.getEndX(), line.getEndY(), x1, y1, x2, y2);
		arrowHead.setFill(Color.BROWN);

		((Pane) line.getParent()).getChildren().add(arrowHead);
	}

	private void readGraphCSV(Scanner scan) {

		int numberOfVertices = scan.nextInt();
		int numberofEdges = scan.nextInt();
		scan.nextLine();// skip current line

		System.out.println(numberOfVertices);
		System.out.println(numberofEdges);

		graph = new Graph(numberOfVertices);
		locationMarkers = new LocationMarker[numberOfVertices];

		targetList = FXCollections.observableArrayList();
		sourceList = FXCollections.observableArrayList();

		for (int i = 0; i < numberOfVertices; i++) {
			String line = scan.nextLine();
			String[] fields = line.split(",");

			if (fields.length >= 4) {
				String name = fields[0].trim();
				double latitude = Double.parseDouble(fields[1].trim());
				double longitude = Double.parseDouble(fields[2].trim());
				String city = fields[3].trim();

				boolean isCity = city.equalsIgnoreCase("city");
				// System.out.println("Location [name=" + name + ", longitude=" + longitude + ",
				// latitude=" + latitude + ", isCity=" + isCity + "]");

				Location location = new Location(name, latitude, longitude, isCity);
				graph.addVertex(location);

				locationMarkers[i] = new LocationMarker(location);
				if (isCity) {
					drawLocation(mapPane, locationMarkers[i]);
					targetList.add(name);
					sourceList.add(name);

				}  //else
				 //drawLocation(mapPane, locationMarkers[i], false);

			}
		}

		for (int i = 0; i < numberofEdges; i++) {
			String line = scan.nextLine();
			String[] fields = line.split(",");

			String v1 = fields[0].trim();
			String v2 = fields[1].trim();

			// System.out.println(v1+"-"+v2);
			graph.addAdjacent(v1, v2);

		}

	}

	private void readGraphtxt(Scanner scan) {

		
		/*  while (scan.hasNext()) {
	            if (scan.hasNextInt()) {
	                int nextInt = scan.nextInt();
	                // Process the integer as needed
	            } else {
	                // Handle non-integer content, skip or break loop as appropriate
	                scan.next(); // Skip the non-integer token
	            }}
	            */
		int numberOfVertices = scan.nextInt();
		//System.out.println(scan.nextInt());
		int numberofEdges = scan.nextInt();
		scan.nextLine();// skip current line

		System.out.println(numberOfVertices);
		System.out.println(numberofEdges);

		graph = new Graph(numberOfVertices);
		locationMarkers = new LocationMarker[numberOfVertices];

		targetList = FXCollections.observableArrayList();
		sourceList = FXCollections.observableArrayList();

		for (int i = 0; i < numberOfVertices; i++) {
			String line = scan.nextLine();
			// String[] fields = line.split(",");

			// Split the string based on blank spaces
			String[] fields = line.split("\\s+");

			int k = 0;

			String name ="";
			while (!Character.isDigit(fields[k].charAt(0))) {// starts with number
				name += fields[k++]+" ";
			}

			name= name.trim();
			double latitude = Double.parseDouble(fields[k++].trim());
			double longitude = Double.parseDouble(fields[k++].trim());
			String city = fields[k++].trim();

			boolean isCity = city.equalsIgnoreCase("city");
			// System.out.println("Location [name=" + name + ", longitude=" + longitude + ",
			// latitude=" + latitude + ", isCity=" + isCity + "]");

			Location location = new Location(name, latitude, longitude, isCity);
			graph.addVertex(location);

			locationMarkers[i] = new LocationMarker(location);
			if (isCity) {
				drawLocation(mapPane, locationMarkers[i]);
				targetList.add(name);
				sourceList.add(name);

			} 

		}

		//System.out.println(graph.toString());
		for (int i = 0; i < numberofEdges; i++) {
			String line = scan.nextLine();
			String[] fields = line.split(","); // have commas

			String v1 = fields[0].trim();
			String v2 = fields[1].trim();

			//System.out.println(v1+"-"+v2);
			graph.addAdjacent(v1, v2);

		}

	}
		  
		  
		  

	int click = 0;
	EventHandler<MouseEvent> circleClickHandler = event -> {
		if (click == 0) {
			System.out.println("Circle clicked!");
			sourceComboBox.setValue(((LocationMarker) event.getSource()).location.getName());
			click++;
		} else if (click == 1) {
			targetComboBox.setValue(((LocationMarker) event.getSource()).location.getName());

			click++;

		}

		// Your event handling code here
	};

	private void drawLocation(Pane pane, LocationMarker locationMarker) {
		// Create a text label for city
		Text text = new Text(locationMarker.location.getName());
		locationMarker.setRadius(5);
		locationMarker.toFront();
		text.setFont(new Font(12));
		locationMarker.addEventHandler(MouseEvent.MOUSE_CLICKED, circleClickHandler);

		text.setX(locationMarker.x - text.getLayoutBounds().getWidth() / 2 + 10);
		text.setY(locationMarker.y - 7);

		if (willNodeOverlap(mapPane, text)) {
			text.setX(locationMarker.x - text.getLayoutBounds().getWidth() / 2);
			text.setY(locationMarker.y + 16);

		}

		// Set the fill color
		locationMarker.setFill(Color.web("#690B27"));

		// Set the stroke color and width
		locationMarker.setStroke(Color.WHITE);
		locationMarker.setStrokeWidth(1);

		// Add the circle to the Pane
		pane.getChildren().addAll(locationMarker, text);
	}

	public File chooseFile(Stage fileStage) {
		// create file chooser object
		FileChooser fileChooser = new FileChooser();
		// set initial directory
		fileChooser.setInitialDirectory(new File("/Users/sarahhassouneh"));
		fileChooser.getExtensionFilters().clear();
		// add an extension filter for csv files only or text files
		// fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files",
		// "*.txt"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"),
				new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setTitle("Choose a file to load Sequence");
		File selectedFile = fileChooser.showOpenDialog(fileStage);
		return selectedFile;

	}

	// delete this later
	private void drawLocation(Pane mapPane2, LocationMarker location, boolean b) {
		Circle circle = new Circle(location.x, location.y, 4);

		circle.setFill(Color.GREEN);

		// Set the stroke color and width (optional)
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(2);

		// Add the circle to the Pane
		mapPane.getChildren().addAll(circle);
	}

	/**
	 * helper functions
	 */

	// Function to check if a new node will overlap with existing nodes in the pane
	private boolean willNodeOverlap(Pane pane, Node newNode) {
		for (Node existingNode : pane.getChildren()) {
			if (doNodesOverlap(existingNode, newNode)) {
				return true; // Overlap found, return true
			}
		}
		return false; // No overlaps found
	}

	// Function to check if two nodes overlap
	private boolean doNodesOverlap(Node node1, Node node2) {
		return node1.getBoundsInParent().intersects(node2.getBoundsInParent());
	}

	private void printCornerCoordinates(Pane pane) {
		double layoutX = pane.getLayoutX();
		double layoutY = pane.getLayoutY();
		double width = pane.getWidth();
		double height = pane.getHeight();

		double topLeftX = layoutX;
		double topLeftY = layoutY;

		double topRightX = layoutX + width;
		double topRightY = layoutY;

		double bottomLeftX = layoutX;
		double bottomLeftY = layoutY + height;

		double bottomRightX = layoutX + width;
		double bottomRightY = layoutY + height;

		System.out.println("Top Left Corner: (" + topLeftX + ", " + topLeftY + ")");
		System.out.println("Top Right Corner: (" + topRightX + ", " + topRightY + ")");
		System.out.println("Bottom Left Corner: (" + bottomLeftX + ", " + bottomLeftY + ")");
		System.out.println("Bottom Right Corner: (" + bottomRightX + ", " + bottomRightY + ")");
	}

}
