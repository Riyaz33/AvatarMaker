import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    public Model model;
    @FXML
    public Accordion accordion;

    @FXML
    public ImageView hairLong;
    @FXML
    public ImageView hairCurly;
    @FXML
    public ImageView hairShort;
    @FXML
    public ImageView hairWavy;
    @FXML
    public StackPane avatarStackPane;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Pane controlsPane;
    @FXML
    public ImageView brownSkin;
    @FXML
    public ImageView lightSkin;
    @FXML
    public ImageView lighterSkin;
    @FXML
    public ImageView browsDefault;
    @FXML
    public ImageView browsAngry;
    @FXML
    public ImageView browsSad;
    @FXML
    public Spinner<Integer> spinnerBrows;
    @FXML
    public Spinner<Integer> spinnerEyes;
    @FXML
    public ImageView eyesDefault;
    @FXML
    public ImageView eyesWide;
    @FXML
    public ImageView eyesClosed;
    @FXML
    public ImageView mouthDefault;
    @FXML
    public ImageView mouthSad;
    @FXML
    public ImageView mouthSerious;
    @FXML
    public Button saveButton;
    @FXML
    public Rectangle backgroundRect;
    @FXML
    public ColorPicker bgColorPicker;
    @FXML
    public ImageView brows_default;
    @FXML
    public ImageView eyes_default;
    @FXML
    public Button saveAvatarButton;
    @FXML
    public ColorPicker clothesColorPicker;

    public String cloth;

    public SVGLoader svgLoader;

    public Controller(){
         this.svgLoader = new SVGLoader();
    }

    public void updateAvatar(){
        // grab all the data from the model and update the view


    }

    public void highlight(Event event){
        ImageView x = (ImageView) event.getSource();
        InnerShadow ds = new InnerShadow();
         ds.setColor(Color.BLUE);
         ds.setRadius(10);
         x.setEffect(ds);
    }

    public void stopHighlight(Event event){
        ImageView x = (ImageView) event.getSource();
        x.setEffect(null);
    }

    public void highlightRect(Event event){
        Rectangle x = (Rectangle) event.getSource();
        InnerShadow ds = new InnerShadow();
        ds.setColor(Color.BLUE);
        ds.setRadius(10);
        x.setEffect(ds);
    }

    public void stopHighlightRect(Event event){
        Rectangle x = (Rectangle) event.getSource();
        x.setEffect(null);
    }

    public void highlightSVG(Event event){
        SVGPath x = (SVGPath) event.getSource();
        InnerShadow ds = new InnerShadow();
        ds.setColor(Color.BLUE);
        ds.setRadius(10);
        x.setEffect(ds);
    }

    public void stopHighlightSVG(Event event){
        SVGPath x = (SVGPath) event.getSource();
        x.setEffect(null);
    }

    public void removeAllWidgets(){
        System.out.println("remove");
        if(controlsPane.getChildren().contains(spinnerBrows)) controlsPane.getChildren().remove(spinnerBrows);
        if(controlsPane.getChildren().contains(spinnerEyes)) controlsPane.getChildren().remove(spinnerEyes);
        if(controlsPane.getChildren().contains(colorPicker)) controlsPane.getChildren().remove(colorPicker);
        if(controlsPane.getChildren().contains(bgColorPicker)) controlsPane.getChildren().remove(bgColorPicker);
        if(controlsPane.getChildren().contains(clothesColorPicker)) controlsPane.getChildren().remove(clothesColorPicker);
    }

    public static String toHex(Color color)
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getBlue() * 255 ) ,
                (int)( color.getGreen() * 255 ),
                (int)( color.getRed() * 255 ));
    }
    /// ****** save file ********* //
    public void saveAttributesToFile(){
        removeAllWidgets();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(avatarStackPane.getScene().getWindow());
        if(!file.getName().contains(".")){
            file = new File(file.getAbsolutePath() + ".txt");
        }

        if(file != null){
            try {
                // from w3schools
                FileWriter myWriter = new FileWriter(file.getAbsolutePath());
                myWriter.write("This file saves your Avatar details \n" +
                        "Colours are provided in Hex\n\n" +
                        "Hair Colour: " + toHex(model.hair.getColor())
                + "\nHair Style: "+model.hair.getStyle() +
                        "\nEyes: " + model.eyes +
                        "\nSkin Colour: " +model.skin +
                        "\nEyebrows: " + model.eyebrows +
                        "\nMouth: " + model.mouth +
                        "\nJacket Colour: " +toHex(model.clothes.getJacket() )+
                        "\nRight Lapel Colour: " +toHex( model.clothes.getRightLapel())+
                        "\nLeft Lapel Colour: " + toHex(model.clothes.getLeftLapel()) +
                        "\nTshirt Colour: " + toHex(model.clothes.getTshirt())+
                        "\nTshirt Neck Colour: " + toHex(model.clothes.getTshirtNeck()) +
                        "\nBackground Colour: " + toHex(model.background));
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clothesColourPick(Event event){
        System.out.println(event.getSource().toString());
    }

    public void showClothesColourPicker(SVGPath clothObject){
        removeAllWidgets();
        controlsPane.getChildren().add(clothesColorPicker);
        clothesColorPicker.setValue((Color) clothObject.getFill());
        clothesColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(cloth.equals("jacket")){
                   // update colours
                    clothObject.setFill(clothesColorPicker.getValue());
                    model.clothes.setJacket(clothesColorPicker.getValue());

                } else if(cloth.equals("leftLapel")) {
                    clothObject.setFill(clothesColorPicker.getValue());
                    model.clothes.setLeftLapel(clothesColorPicker.getValue());

                }else if(cloth.equals("rightLapel")){
                    clothObject.setFill(clothesColorPicker.getValue());
                    model.clothes.setRightLapel(clothesColorPicker.getValue());

                } else if(cloth.equals("tshirt")){
                    clothObject.setFill(clothesColorPicker.getValue());
                    model.clothes.setTshirt(clothesColorPicker.getValue());

                } else if (cloth.equals("tshirtNeck")){
                    clothObject.setFill(clothesColorPicker.getValue());
                    model.clothes.setTshirtNeck(clothesColorPicker.getValue());
                } else {
                    System.out.println("show clothes colour picker RIP");
                }
            }
        });
    }

    public void initHandlers(){

       /* avatarStackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               // if(mouseEvent.getPickResult().getIntersectedNode().getId().toString().equals("StackPane")){
                System.out.println(mouseEvent.getPickResult().getIntersectedNode().getId());
                if(controlsPane.getChildren().contains(colorPicker)) controlsPane.getChildren().remove(1);
                //}
            }
        }); */

        backgroundRect.setPickOnBounds(false);
        avatarStackPane.lookup("#"+ model.skin).setPickOnBounds(false);
        avatarStackPane.lookup("#"+ model.hair.getStyle()).setPickOnBounds(false);
        avatarStackPane.lookup("#"+ model.eyebrows).setPickOnBounds(false);
        avatarStackPane.lookup("#"+ model.mouth).setPickOnBounds(false);
        avatarStackPane.lookup("#"+ model.eyes).setPickOnBounds(false);
        avatarStackPane.lookup("#nose").setPickOnBounds(false);
        backgroundRect.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("click rect");
                removeAllWidgets();
                controlsPane.getChildren().add(bgColorPicker);
                bgColorPicker.setValue(model.background);
                bgColorPicker.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        model.background = bgColorPicker.getValue();
                        backgroundRect.setFill(bgColorPicker.getValue());
                    }
                });
            }
        });

        backgroundRect.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(backgroundRect.getFill() != Color.TRANSPARENT)
                    highlightRect(mouseEvent);
            }
        });
        backgroundRect.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopHighlightRect(mouseEvent);
            }
        });

        // ******** ACCORDION *******/
        accordion.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                System.out.println("click accordion");
            }
        });

        controlsPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
            }
        });

        /// ****** save file Button ********* //
        saveAvatarButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                saveAttributesToFile();
            }
        });

        // *********** SAVE BUTTON ******//
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                WritableImage export = avatarStackPane.snapshot(new SnapshotParameters(), null);

                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showSaveDialog(avatarStackPane.getScene().getWindow());

                if(file != null){
                    if(!file.getName().contains(".")){
                        file = new File(file.getAbsolutePath() + ".png");
                    }
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(export, null), "png", file);

                    }catch (IOException ex){
                        System.out.println("RIP");
                    }
                }


            }
        });

        // ****************** MOUTH *****************///
        mouthSad.setPickOnBounds(true);
        mouthSad.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove((avatarStackPane.lookup("#" + model.mouth)));
                ImageView mouthSadImageView = new ImageView(new Image(getClass().getResource("resources/mouth/mouth_sad.png").toString()));
                mouthSadImageView.setFitHeight(150);
                mouthSadImageView.setFitWidth(200);
                mouthSadImageView.setTranslateY(17);
                mouthSadImageView.setScaleX(1.8);
                mouthSadImageView.setScaleY(1.5);
                model.mouth = "mouth_sad";
                mouthSadImageView.setId(model.mouth);
                avatarStackPane.getChildren().add(2, mouthSadImageView);

            }
        });


        mouthDefault.setPickOnBounds(true);
        mouthDefault.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove((avatarStackPane.lookup("#" + model.mouth)));
                ImageView mouthDefaultImageView = new ImageView(new Image(getClass().getResource("resources/mouth/mouth_default.png").toString()));
                mouthDefaultImageView.setFitHeight(150);
                mouthDefaultImageView.setFitWidth(200);
                mouthDefaultImageView.setTranslateY(17);
                mouthDefaultImageView.setScaleX(1.8);
                mouthDefaultImageView.setScaleY(1.5);
                model.mouth = "mouth_default";
                mouthDefaultImageView.setId(model.mouth);
                avatarStackPane.getChildren().add(2, mouthDefaultImageView);
            }
        });

        mouthSerious.setPickOnBounds(true);
        mouthSerious.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                avatarStackPane.getChildren().remove((avatarStackPane.lookup("#" + model.mouth)));
                ImageView mouthSeriousImageView = new ImageView(new Image(getClass().getResource("resources/mouth/mouth_serious.png").toString()));
                mouthSeriousImageView.setFitHeight(150);
                mouthSeriousImageView.setFitWidth(200);
                mouthSeriousImageView.setTranslateY(17);
                mouthSeriousImageView.setScaleX(1.8);
                mouthSeriousImageView.setScaleY(1.5);
                model.mouth = "mouth_serious";
                mouthSeriousImageView.setId(model.mouth);
                avatarStackPane.getChildren().add(2, mouthSeriousImageView);
            }
        });

        // *************** EYES ******************//////
        eyesClosed.setPickOnBounds(true);
        eyesClosed.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.eyes));
                ImageView eyesClosedImageView = new ImageView(new Image(getClass().getResource("resources/eyes/eyes_closed.png").toString()));
                eyesClosedImageView.setFitHeight(150);
                eyesClosedImageView.setFitWidth(200);
                eyesClosedImageView.setScaleX(1.5);
                eyesClosedImageView.setScaleY(2);
                model.eyes = "eyes_closed";
                eyesClosedImageView.setId("eyes_closed");
                avatarStackPane.getChildren().add(5, eyesClosedImageView);

                eyesClosedImageView.setPickOnBounds(false);
                eyesClosedImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        removeAllWidgets();
                        System.out.println("clicked eyes closed");
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 2);
                        spinnerEyes.setValueFactory(valueFactory);
                        controlsPane.getChildren().add(spinnerEyes);

                        double currScale = 2;

                        spinnerEyes.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                                eyesClosedImageView.setScaleX((float) ( (float) (t1+2)/2) * .75);
                                eyesClosedImageView.setScaleY((float) (t1+2)/2);

                            }
                        });

                    }
                });

                eyesClosedImageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlight(mouseEvent);
                    }
                });

                eyesClosedImageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlight(mouseEvent);
                    }
                });


            }
        });

        eyesWide.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.eyes));
                ImageView eyesWideImageView = new ImageView(new Image(getClass().getResource("resources/eyes/eyes_wide.png").toString()));
                eyesWideImageView.setFitHeight(150);
                eyesWideImageView.setFitWidth(200);
                eyesWideImageView.setScaleX(1.3);
                eyesWideImageView.setScaleY(1.5);
                model.eyes = "eyes_wide";
                eyesWideImageView.setId("eyes_wide");
                avatarStackPane.getChildren().add(5, eyesWideImageView);

               // eyesWideImageView.setPickOnBounds(true);
                eyesWideImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        removeAllWidgets();
                        System.out.println("clicked eyes wide");
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 2);
                        spinnerEyes.setValueFactory(valueFactory);
                        controlsPane.getChildren().add(spinnerEyes);

                        spinnerEyes.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                                eyesWideImageView.setScaleX((float) ((float) (t1+2)/2) * .6);
                                eyesWideImageView.setScaleY((float) ((float) (t1+2)/2) * .7);

                            }
                        });

                    }
                });
                eyesWideImageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlight(mouseEvent);
                    }
                });

                eyesWideImageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlight(mouseEvent);
                    }
                });

            }
        });

        eyesDefault.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.eyes));
                ImageView eyesDefaultImageView = new ImageView(new Image(getClass().getResource("resources/eyes/eyes_default.png").toString()));
                eyesDefaultImageView.setFitHeight(150);
                eyesDefaultImageView.setFitWidth(200);
                eyesDefaultImageView.setScaleX(1.3);
                eyesDefaultImageView.setScaleY(1.5);
                model.eyes = "eyes_default";
                eyesDefaultImageView.setId("eyes_default");
                avatarStackPane.getChildren().add(5, eyesDefaultImageView);

                // eyesWideImageView.setPickOnBounds(true);
                eyesDefaultImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        removeAllWidgets();
                        System.out.println("clicked eyes default");
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 2);
                        spinnerEyes.setValueFactory(valueFactory);
                        controlsPane.getChildren().add(spinnerEyes);

                        spinnerEyes.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                                eyesDefaultImageView.setScaleX((float) ((float) (t1+2)/2) * .6);
                                eyesDefaultImageView.setScaleY((float) ((float) (t1+2)/2) * .7);

                            }
                        });

                    }
                });

                eyesDefaultImageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlight(mouseEvent);
                    }
                });

                eyesDefaultImageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlight(mouseEvent);
                    }
                });
            }
        });

        // ************************ EYEBROWS ***********************//
        browsAngry.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.eyebrows));
                ImageView browsAngryImageView = new ImageView(new Image(getClass().getResource("resources/brows/brows_angry.png").toString()));
                browsAngryImageView.setFitWidth(200);
                browsAngryImageView.setFitHeight(150);
                browsAngryImageView.setScaleX(2);
                browsAngryImageView.setScaleY(2);
                model.eyebrows = "brows_angry";
                browsAngryImageView.setId("brows_angry");
                avatarStackPane.getChildren().add(5,browsAngryImageView);


                browsAngryImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        removeAllWidgets();
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-8, 8, 1);
                        spinnerBrows.setValueFactory(valueFactory);
                        controlsPane.getChildren().add(spinnerBrows);

                        double browY = browsAngryImageView.getY();

                        spinnerBrows.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                                browsAngryImageView.setTranslateY(browY + t1);
                            }
                        });
                    }
                });

                browsAngryImageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlight(mouseEvent);
                    }
                });

                browsAngryImageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlight(mouseEvent);
                    }
                });

            }
        });

        browsDefault.setPickOnBounds(true);
        browsDefault.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.eyebrows));
                ImageView browsDefaultImageView = new ImageView(new Image(getClass().getResource("resources/brows/brows_default.png").toString()));
                browsDefaultImageView.setFitWidth(200);
                browsDefaultImageView.setFitHeight(150);
                browsDefaultImageView.setScaleX(1.5);
                browsDefaultImageView.setScaleY(2);
                model.eyebrows = "brows_default";
                browsDefaultImageView.setId("brows_default");
                avatarStackPane.getChildren().add(5,browsDefaultImageView);

                browsDefaultImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        removeAllWidgets();
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-8, 8, 1);
                        spinnerBrows.setValueFactory(valueFactory);
                        controlsPane.getChildren().add(spinnerBrows);

                        double browY = browsDefaultImageView.getY();

                        spinnerBrows.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                                browsDefaultImageView.setTranslateY(browY + t1);
                            }
                        });
                    }
                });
                browsDefaultImageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlight(mouseEvent);
                    }
                });

                browsDefaultImageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlight(mouseEvent);
                    }
                });
            }
        });

        browsSad.setPickOnBounds(true);
        browsSad.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.eyebrows));
                ImageView browsSadImageView = new ImageView(new Image(getClass().getResource("resources/brows/brows_sad.png").toString()));
                browsSadImageView.setFitWidth(200);
                browsSadImageView.setFitHeight(150);
                browsSadImageView.setScaleX(2);
                browsSadImageView.setScaleY(2);
                model.eyebrows = "brows_sad";
                browsSadImageView.setId("brows_sad");
                avatarStackPane.getChildren().add(5,browsSadImageView);

                browsSadImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        removeAllWidgets();
                        System.out.println("clicked sad");
                        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-8, 8, 1);
                        spinnerBrows.setValueFactory(valueFactory);
                        controlsPane.getChildren().add(spinnerBrows);

                        double browY = browsSadImageView.getY();

                        spinnerBrows.valueProperty().addListener(new ChangeListener<Integer>() {
                            @Override
                            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                                browsSadImageView.setTranslateY(browY + t1);
                            }
                        });
                    }
                });
                browsSadImageView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlight(mouseEvent);
                    }
                });

                browsSadImageView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlight(mouseEvent);
                    }
                });
            }
        });

        // ********************* SKIN ****************************** //
        lighterSkin.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#"+model.skin));
                ImageView lighterSkinImageView = new ImageView(new Image(getClass().getResource("resources/skin/skin_lighter.png").toString()));
                avatarStackPane.getChildren().add(1, lighterSkinImageView);
                lighterSkinImageView.setFitWidth(300);
                lighterSkinImageView.setFitHeight(300);
                lighterSkinImageView.setId("lighter");
                model.skin = "lighter";
            }
        });

        brownSkin.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#"+model.skin));
                ImageView brownSkinImageView = new ImageView(new Image(getClass().getResource("resources/skin/skin_brown.png").toString()));
                avatarStackPane.getChildren().add(1, brownSkinImageView);
                brownSkinImageView.setFitWidth(300);
                brownSkinImageView.setFitHeight(300);
                brownSkinImageView.setId("brown");
                model.skin = "brown";
            }
        });
        lightSkin.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                // chaotic line of interest
               // if(controlsPane.getChildren().contains(colorPicker)) controlsPane.getChildren().remove(1);

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#"+model.skin));
                ImageView lightSkinImageView = new ImageView(new Image(getClass().getResource("resources/skin/skin_light.png").toString()));
                avatarStackPane.getChildren().add(1, lightSkinImageView);
                lightSkinImageView.setFitWidth(300);
                lightSkinImageView.setFitHeight(300);
                lightSkinImageView.setId("light");
                model.skin = "light";
            }
        });

        // ****** HAIR ***/
        hairLong.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.hair.getStyle()));
                Group hairLongSvg = svgLoader.loadSVG("resources/hair/hair_long.svg");
                hairLongSvg.setId("long");
                SVGPath hairLongSvgPath = (SVGPath) hairLongSvg.getChildren().get(0);
                hairLongSvgPath.setFill(Color.BLACK);
                hairLongSvg.setTranslateY(10);
                hairLongSvg.setScaleY(1.3);
                hairLongSvg.setScaleX(1.3);
                avatarStackPane.getChildren().add(hairLongSvg);
                model.hair.setColor(Color.BLACK);
                model.hair.setStyle("long");

                hairLongSvg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // show colour picker
                        controlsPane.getChildren().add(colorPicker);
                        colorPicker.setValue(model.hair.getColor());
                        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                model.hair.setColor(colorPicker.getValue());
                                hairLongSvgPath.setFill(colorPicker.getValue());
                            }
                        });
                    }
                });

                hairLongSvgPath.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlightSVG(mouseEvent);
                    }
                });

                hairLongSvgPath.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlightSVG(mouseEvent);
                    }
                });

            }
        });

        hairCurly.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.hair.getStyle()));
                Group hairCurlySvg = svgLoader.loadSVG("resources/hair/hair_curly.svg");
                hairCurlySvg.setId("curly");
                SVGPath hairCurlySvgPath = (SVGPath) hairCurlySvg.getChildren().get(0);
                hairCurlySvgPath.setFill(Color.BLACK);
                hairCurlySvg.setTranslateY(10);
                hairCurlySvg.setScaleY(1.3);
                hairCurlySvg.setScaleX(1.3);
                avatarStackPane.getChildren().add(hairCurlySvg);
                model.hair.setColor(Color.BLACK);
                model.hair.setStyle("curly");

                hairCurlySvgPath.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // show colour picker
                        removeAllWidgets();
                        controlsPane.getChildren().add(colorPicker);
                        colorPicker.setValue(model.hair.getColor());
                        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                model.hair.setColor(colorPicker.getValue());
                                hairCurlySvgPath.setFill(colorPicker.getValue());
                            }
                        });
                    }
                });
                hairCurlySvgPath.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlightSVG(mouseEvent);
                    }
                });

                hairCurlySvgPath.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlightSVG(mouseEvent);
                    }
                });
            }
        });

        hairShort.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.hair.getStyle()));
                Group hairShortSvg = svgLoader.loadSVG("resources/hair/hair_short.svg");
                hairShortSvg.setId("short");
                SVGPath hairShortSvgPath = (SVGPath) hairShortSvg.getChildren().get(0);
                hairShortSvgPath.setFill(Color.BLACK);
                hairShortSvg.setTranslateY(-40);
                hairShortSvg.setScaleY(1.3);
                hairShortSvg.setScaleX(1.4);
                avatarStackPane.getChildren().add(hairShortSvg);
                model.hair.setColor(Color.BLACK);
                model.hair.setStyle("short");

                hairShortSvgPath.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        removeAllWidgets();
                        // show colour picker
                        controlsPane.getChildren().add(colorPicker);
                        colorPicker.setValue(model.hair.getColor());

                        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                model.hair.setColor(colorPicker.getValue());
                                hairShortSvgPath.setFill(colorPicker.getValue());
                            }
                        });
                    }
                });
                hairShortSvgPath.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlightSVG(mouseEvent);
                    }
                });

                hairShortSvgPath.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlightSVG(mouseEvent);
                    }
                });
            }
        });

        hairWavy.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();

                avatarStackPane.getChildren().remove(avatarStackPane.lookup("#" + model.hair.getStyle()));
                Group hairWavySvg = svgLoader.loadSVG("resources/hair/hair_wavy.svg");
                hairWavySvg.setId("wavy");
                SVGPath hairWavySvgPath = (SVGPath) hairWavySvg.getChildren().get(0);
                hairWavySvgPath.setFill(Color.BLACK);
                hairWavySvg.setTranslateY(-35);
                hairWavySvg.setScaleY(1.3);
                hairWavySvg.setScaleX(1.5);
                avatarStackPane.getChildren().add(hairWavySvg);
                model.hair.setColor(Color.BLACK);
                model.hair.setStyle("wavy");

                hairWavySvgPath.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // show colour picker
                        removeAllWidgets();
                        controlsPane.getChildren().add(colorPicker);
                        colorPicker.setValue(model.hair.getColor());

                        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                model.hair.setColor(colorPicker.getValue());
                                hairWavySvgPath.setFill(colorPicker.getValue());
                            }
                        });
                    }
                });
                hairWavySvgPath.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        highlightSVG(mouseEvent);
                    }
                });

                hairWavySvgPath.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stopHighlightSVG(mouseEvent);
                    }
                });
            }
        });
    }

    // avatar when app opens
    public void initAvatar(){
        Group hairLongSvg = svgLoader.loadSVG("resources/hair/hair_long.svg");
        hairLongSvg.setId("long");
        SVGPath hairLongSvgPath = (SVGPath) hairLongSvg.getChildren().get(0);
        hairLongSvgPath.setFill(model.hair.getColor());
        hairLongSvg.setTranslateY(10);
        hairLongSvg.setScaleY(1.3);
        hairLongSvg.setScaleX(1.3);
        avatarStackPane.getChildren().add(hairLongSvg);

        hairLongSvg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // show colour picker
                removeAllWidgets();

                controlsPane.getChildren().add(colorPicker);
                colorPicker.setValue(model.hair.getColor());
                colorPicker.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        model.hair.setColor(colorPicker.getValue());
                        hairLongSvgPath.setFill(colorPicker.getValue());
                    }
                });
            }
        });

        hairLongSvgPath.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                highlightSVG(mouseEvent);
            }
        });

        hairLongSvgPath.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopHighlightSVG(mouseEvent);
            }
        });

        Group clothesSvg = svgLoader.loadSVG("resources/clothes.svg");

        SVGPath tshirt = (SVGPath) clothesSvg.getChildren().get(0);
        tshirt.setFill(model.clothes.getTshirt());
        tshirt.setId("tshirt");
        tshirt.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                cloth = "tshirt";
                showClothesColourPicker(tshirt);
                System.out.println("tshirt CLICKED" + mouseEvent.getPickResult().getIntersectedNode().getId());
            }
        });

        tshirt.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                highlightSVG(mouseEvent);
            }
        });

        tshirt.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopHighlightSVG(mouseEvent);
            }
        });

        SVGPath jacket = (SVGPath) clothesSvg.getChildren().get(1);
        jacket.setFill(model.clothes.getJacket());
        jacket.setId("jacket");
        jacket.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                cloth = "jacket";
                showClothesColourPicker(jacket);
                System.out.println("JACKET CLICKED" + mouseEvent.getPickResult().getIntersectedNode().getId());
            }
        });

        jacket.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                highlightSVG(mouseEvent);
            }
        });

        jacket.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopHighlightSVG(mouseEvent);
            }
        });

        SVGPath tshirtNeck = (SVGPath) clothesSvg.getChildren().get(2);
        tshirtNeck.setFill(model.clothes.getTshirtNeck());
        tshirtNeck.setId("tshirtNeck");
        tshirtNeck.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                cloth = "tshirtNeck";
                showClothesColourPicker(tshirtNeck);
                System.out.println("tshirtNeck CLICKED" + mouseEvent.getPickResult().getIntersectedNode().getId());
            }
        });

        tshirtNeck.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                highlightSVG(mouseEvent);
            }
        });

        tshirtNeck.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopHighlightSVG(mouseEvent);
            }
        });

        SVGPath leftLapel = (SVGPath) clothesSvg.getChildren().get(3);
        leftLapel.setFill(model.clothes.getLeftLapel());

        leftLapel.setId("leftLapel");
        leftLapel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                cloth = "leftLapel";
                showClothesColourPicker(leftLapel);
                System.out.println("leftLapel CLICKED" + mouseEvent.getPickResult().getIntersectedNode().getId());
            }
        });

        leftLapel.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                highlightSVG(mouseEvent);
            }
        });

        leftLapel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopHighlightSVG(mouseEvent);
            }
        });

        SVGPath rightLapel = (SVGPath) clothesSvg.getChildren().get(4);
        rightLapel.setFill(model.clothes.getRightLapel());

        rightLapel.setId("rightLapel");
        rightLapel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                cloth = "rightLapel";
                showClothesColourPicker(rightLapel);
                System.out.println("rightLapel CLICKED" + mouseEvent.getPickResult().getIntersectedNode().getId());
            }
        });

        rightLapel.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                highlightSVG(mouseEvent);
            }
        });

        rightLapel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopHighlightSVG(mouseEvent);
            }
        });
        clothesSvg.setTranslateY(125);
        clothesSvg.setScaleY(1.33);
        clothesSvg.setScaleX(1.43);
        avatarStackPane.getChildren().add(clothesSvg);


        // add default colour pickers
        eyes_default.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                System.out.println("clicked eyes default");
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 2);
                spinnerEyes.setValueFactory(valueFactory);
                controlsPane.getChildren().add(spinnerEyes);

                spinnerEyes.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                        eyes_default.setScaleX((float) ((float) (t1+2)/2)*.85);
                        eyes_default.setScaleY((float) ((float) (t1+2)/2)*.9);

                    }
                });

            }
        });

        brows_default.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                removeAllWidgets();
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-8, 8, 1);
                spinnerBrows.setValueFactory(valueFactory);
                controlsPane.getChildren().add(spinnerBrows);

                double browY = brows_default.getY();

                spinnerBrows.valueProperty().addListener(new ChangeListener<Integer>() {
                    @Override
                    public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                        brows_default.setTranslateY(browY + t1);
                    }
                });

            }
        });



    }

}


