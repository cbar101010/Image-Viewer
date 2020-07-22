import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 * This is my JIMachine. It extends the Application class.
 */
public final class JIMachine extends Application {
    /**
     * This is an image, it stores the original Picture.
     */
    private static Image originalPicture = null;
    /**
     * This is the spacing of the HBox.
     * /
     * private static final int SPACING = 20;
     * /**
     * This is the multiplier for growing the image.
     */
    private static final double GROWING = 1.25;
    /**
     * This is the multiplier for shrinking the image.
     */
    private static final double SHRINKING = 0.75;
    /**
     * IO Buffer.
     */
    private static final int BUFFERAMMOUNT = 1024;
    /**
     * This is the width of the scene.
     */
    private static final int WIDTHSCENE = 1200;
    /**
     * This is the height of the scene.
     */
    private static final int HEIGHTSCENE = 800;
    /**
     * Negative amount allowed.
     */
    private static final int NEGATIVEAMOUNT = -4;
    /**
     * Positive Images amount allowed.
     */
    private static final int POSITIVEAMOUNT = 4;
    /**
     * positve back amoung.
     */
    private static final int POSITIVEBACK = 3;
    /**
     * positve back amounT ALLOWED.
     */
    private static final int POSITIVEBFORWARD = 5;
    /**
     * Hbox size.
     */
    private static final int HBOXSIZE = 25;
    /**
     * This is for the size of thumb.
     */
    private static final int OURSIZE = 60;
    /**
     * This is my Image view for the picture.
     */
    private static ImageView picture = new ImageView();
    /**
     * This is a Double Property for the height of the image.
     */
    private static DoubleProperty height;
    /**
     * This is a Double Property for the width of the image.
     */
    private static DoubleProperty width;
    /**
     * String for if it is in thumbnail or not.
     * */
    private static String isNormal = "0";
    /**
     * String for what directory we are in.
     * */
    private static String currentDiretory = null;
    /**
     * index for what image we are on.
     * */
    private static int index = -1;
    /**
     * Map for the captions.
     * */
    private static Map<String, String> captions = new HashMap<>();
    /**
     *Array list for the directory we have saved.
     * */
    private static ArrayList<String> savedDirectories = new ArrayList<>();

    /**
     * This is my main, all it does is launch the application.
     *
     * @param args this is just part of the main.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
    /**
     * This is my file list.
     * */
    private static ArrayList<File> fList = new ArrayList<>();

    /**
     * This is my start function.
     * It does pretty much everything for the program.
     *
     * @param primaryStage this is the Stage of the program.
     */
    @Override
    public void start(final Stage primaryStage) {
        HBox myHBox = new HBox(HBOXSIZE);
        Button open = new Button("Open");
        Button zoomIn = new Button("Zoom in");
        Button zoomOut = new Button("Zoom Out");
        Button normal = new Button("100%");
        Button quit = new Button("Quit Program");
        Button clearImage = new Button("Clear Image");
        Button next = new Button("Next");
        Button back = new Button("Back");
        Button capture = new Button("Caption Photo");
        Button save = new Button("Save");
        Button thumb = new Button("Thumbnail");
        Text captionText = new Text("Captions Below: ");
        captionText.setStyle("-fx-font: 24 arial;");
        myHBox.getChildren().addAll(captionText,
                open, zoomIn, zoomOut, normal,
                quit, clearImage, next, back, capture, save, thumb);
        FlowPane multiImage = new FlowPane();
        VBox multiCaptions = new VBox();
        BorderPane pane = new BorderPane();
        Text startText = new Text();
        startText.setText(
                "Press Next for First image Once you have selected your File");
        startText.setStyle("-fx-font: 24 arial;");
        pane.setCenter(startText);
        pane.setTop(myHBox);
        thumb.setOnAction(e -> {
            if (isNormal == "0") {
                isNormal = "1";
                ImageView temp = new ImageView();
                multiImage.getChildren().clear();
                Text t = new Text();
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                temp = showImage(-1);
                t.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()));
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                Text u = new Text();
                temp = showImage(0);
                u.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()));
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                Text v = new Text();
                temp = showImage(0);
                v.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()));
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                Text w = new Text();
                temp = showImage(0);
                w.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()));
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                t.setStyle("-fx-font: 24 arial;");
                u.setStyle("-fx-font: 24 arial;");
                v.setStyle("-fx-font: 24 arial;");
                w.setStyle("-fx-font: 24 arial;");
                multiCaptions.getChildren().setAll(t, u, v, w);
                pane.setLeft(multiCaptions);
                multiImage.getChildren().add(temp);
                index += NEGATIVEAMOUNT;
                pane.setCenter(multiImage);
            } else {
                isNormal = "0";
                index++;
                multiImage.getChildren().clear();
                multiCaptions.getChildren().clear();
                Image image = new Image(fList.get(index).toURI().toString());
                picture.setImage(image);
                pane.setCenter(picture);
                Text w = new Text();
                w.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()));
                w.setStyle("-fx-font: 24 arial;");
                multiCaptions.getChildren().add(w);
                pane.setLeft(multiCaptions);
            }
        });
        save.setOnAction(e -> {
            if (!savedDirectories.contains(currentDiretory)) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Directory...");
                File file = fileChooser.showSaveDialog(null);
                file.mkdir();
                for (File x : fList) {
                    InputStream is = null;
                    OutputStream os = null;

                    try {
                        is = new FileInputStream(new File(x.getPath()));
                        os = new FileOutputStream(new File(
                                file + "/" + x.getName()));
                        byte[] buffer = new byte[BUFFERAMMOUNT];
                        int length;
                        while ((length = is.read(buffer)) > 0) {
                            os.write(buffer, 0, length);
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            os.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(
                            "SaveState.txt", "UTF-8");
                } catch (FileNotFoundException
                        | UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                writer.println(normalMode());
                writer.println(index);
                writer.close();
            }
            Alert fileSavedCorrect = new Alert(Alert.AlertType.INFORMATION);
            fileSavedCorrect.setTitle("Saving");
            fileSavedCorrect.setHeaderText("Congrats!");
            fileSavedCorrect.setContentText("FIle Saved");
            fileSavedCorrect.showAndWait();
        });
        capture.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog("Caption");

            dialog.setHeaderText("Enter Caption:");

            String myCaption = JOptionPane.
                    showInputDialog("enter caption");

            captions.put(fList.get(index).
                    toURI().toString(), myCaption);
            Text t = new Text();
            t.setText(fList.get(index).getName().
                    replaceFirst("[.][^.]+$", "") + ": " + myCaption);
            pane.setLeft(t);
        });
        open.setOnAction(e -> {
            index = -1;
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File directory = directoryChooser.showDialog(primaryStage);
            currentDiretory = directory.getAbsolutePath();
            fList = new ArrayList<>(Arrays.
                    asList(Objects.requireNonNull(directory.listFiles())));
            ArrayList<File> resultList = new ArrayList<>();
            File[] f = directory.listFiles();
            for (File file : f) {
                if (file != null && (file.getName().
                        toLowerCase().endsWith(".jpg")
                        || file.getName().toLowerCase().endsWith(".PNG")
                        || file.getName().toLowerCase().endsWith(".GIF"))) {
                    resultList.add(file);
                }
            }
            fList = resultList;
        });
        next.setOnAction(e -> {
            if (normalMode() == "1") {
                if (fList.size() - 1 - index - POSITIVEAMOUNT
                        < POSITIVEAMOUNT) {
                    index = fList.size() - POSITIVEAMOUNT;
                } else {
                    index += POSITIVEBFORWARD;
                }
                multiCaptions.getChildren().clear();
                multiImage.getChildren().clear();
                ImageView temp = new ImageView();
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                temp = showImage(-1);
                Text t = new Text();
                t.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                temp = showImage(0);
                Text u = new Text();
                u.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                temp = showImage(0);
                Text v = new Text();
                v.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                temp = showImage(0);
                Text w = new Text();
                w.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                index += NEGATIVEAMOUNT;
                t.setStyle("-fx-font: 24 arial;");
                u.setStyle("-fx-font: 24 arial;");
                v.setStyle("-fx-font: 24 arial;");
                w.setStyle("-fx-font: 24 arial;");
                multiCaptions.getChildren().addAll(t, u, v, w);
                pane.setLeft(multiCaptions);
                pane.setCenter(multiImage);
            } else {
                for (File x : fList) {
                    if (x.toString().contains(".ini")) {
                        fList.remove(x);
                    }
                }
                pane.setCenter(showImage(0));
                Text w = new Text();
                w.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                multiCaptions.getChildren().clear();
                w.setStyle("-fx-font: 24 arial;");
                multiCaptions.getChildren().add(w);
                pane.setLeft(multiCaptions);

            }
        });
        back.setOnAction(e -> {
            if (normalMode() == "1") {
                if (index >= POSITIVEBACK) {
                    index -= POSITIVEBACK;
                } else {
                    index -= index;
                }
                multiCaptions.getChildren().clear();
                multiImage.getChildren().clear();
                ImageView temp = new ImageView();
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                temp = showImage(-1);
                Text t = new Text();
                t.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                temp = showImage(0);
                Text u = new Text();
                u.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                temp = showImage(0);
                Text v = new Text();
                v.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                temp = showImage(0);
                Text w = new Text();
                w.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                temp.setFitWidth(OURSIZE);
                temp.setFitHeight(OURSIZE);
                multiImage.getChildren().add(temp);
                index += NEGATIVEAMOUNT;
                t.setStyle("-fx-font: 24 arial;");
                u.setStyle("-fx-font: 24 arial;");
                v.setStyle("-fx-font: 24 arial;");
                w.setStyle("-fx-font: 24 arial;");
                multiCaptions.getChildren().setAll(t, u, v, w);
                pane.setLeft(multiCaptions);
                pane.setCenter(multiImage);
            } else {
                pane.setCenter(showImage(1));
                Text w = new Text();
                w.setText(fList.get(index).getName().
                        replaceFirst("[.][^.]+$", "") + ":   " + captions.
                        get(fList.get(index).toURI().toString()) + "\n");
                multiCaptions.getChildren().clear();
                w.setStyle("-fx-font: 24 arial;");
                multiCaptions.getChildren().add(w);
                pane.setLeft(multiCaptions);

            }

        });
        zooming(zoomIn, GROWING);
        zooming(zoomOut, SHRINKING);
        clearImage.setOnAction(e -> {
            Text clearMyImage = new Text();
            clearMyImage.setText("Image Cleared");
            clearMyImage.setStyle("-fx-font: 24 arial;");
            pane.setCenter(clearMyImage);
            index = -1;
        });
        quit.setOnAction(e -> {
            primaryStage.close();
        });
        normal.setOnAction(e -> {
            height = new SimpleDoubleProperty(originalPicture.getHeight());
            width = new SimpleDoubleProperty(originalPicture.getWidth());
            picture.setFitHeight(originalPicture.getHeight());
            picture.setFitWidth(originalPicture.getWidth());
        });
        Scene scene = new Scene(pane, WIDTHSCENE, HEIGHTSCENE); // w x h

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    back.fire();
                    break;
                case RIGHT:
                    next.fire();
                    break;
                default:
                    pane.setCenter(showImage(1));
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param zoomOut this is what button is being called.
     * @param shrinkOrGrow this is if we are growing or shrinking.
     * This function zooms and zooms out.
     * */
    private void zooming(final Button zoomOut, final double shrinkOrGrow) {
        zoomOut.setOnAction(e -> {
            if (height != null && width != null) {
                height = new SimpleDoubleProperty(height.get() * shrinkOrGrow);
                width = new SimpleDoubleProperty(width.get() * shrinkOrGrow);
                picture.setFitWidth(width.get());
                picture.setFitHeight(height.get());
            } else {
                Alert outAlert = new Alert(Alert.AlertType.INFORMATION);
                outAlert.setTitle("Important Message for you from Colby");
                outAlert.setHeaderText("Something's wrong");
                outAlert.setContentText("Make sure you select an image first!");

                outAlert.showAndWait();
            }
        });
    }
    /**
     * @param n this takes what image it should show.
     * @return this returns a ImageView.
     * This will return what image to show.
     * */
    ImageView showImage(final int n) {
        if (index != -1 && fList.get(index).isDirectory()) {
            index = -1;
        }
        if (n == -1) {
            Image image = new Image(fList.get(index).toURI().toString());
            ImageView myImage = new ImageView();
            myImage.setImage(image);
            picture.setImage(image);
            width = new SimpleDoubleProperty(image.getWidth());
            height = new SimpleDoubleProperty(image.getHeight());
            return myImage;
        }
        if (n == 1 && index - 1 > -1) {
            index--;
        } else if (n == 0 && index < fList.size() - 1) {
            index++;
        }
        Image image = new Image(fList.get(index).toURI().toString());
        ImageView myImage = new ImageView();
        myImage.setImage(image);
        picture.setImage(image);
        width = new SimpleDoubleProperty(image.getWidth());
        height = new SimpleDoubleProperty(image.getHeight());
        return myImage;
    }
    /**
     * @return returns if its in normal mode or not.
     * This tells us if we are in thumbnail mode.
     * */
    String normalMode() {
        if (isNormal == "1") {
            return "1";
        } else {
            return "0";
        }
    }
}
