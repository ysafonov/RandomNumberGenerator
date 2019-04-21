package cz.vutbr.feec.mkri.controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import cz.vutbr.feec.mkri.Main;
import cz.vutbr.feec.mkri.generator.GeneratorJavaRandom;
import cz.vutbr.feec.mkri.generator.GeneratorMath;
import cz.vutbr.feec.mkri.generator.GeneratorSecureRandom;
import cz.vutbr.feec.mkri.tests.Entropy;
import cz.vutbr.feec.mkri.tests.LongRunsTest;
import cz.vutbr.feec.mkri.tests.MonobitTest;
import cz.vutbr.feec.mkri.tests.PokerTest;
import cz.vutbr.feec.mkri.tests.RunsTest;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import ij.ImagePlus;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This java class represents project's Compare Pane controller class.
 * 
 * @author Yehor Safonov; id: 185942
 * @author Pavel Mazanek; id: 185933
 */

public class CompareWindowControllers implements Initializable {

	private int maximumSizeOfArray = Main.generator_configuration.imageSize * Main.generator_configuration.imageSize;
	
	private ArrayList<Integer> arrayForAnalyzing;

	/*********
	 * First test items
	 ************/
	@FXML
	private ImageView firstImageView;
	@FXML
	private TextArea runsFirst;
	@FXML
	private TextArea pokerFirst;
	@FXML
	private TextArea monobitFirst;
	@FXML
	private TextArea longRunsFirst;
	@FXML
	private Text entropyFirst;

	/*
	 * Monobit test summary
	 */
	@FXML
	private ImageView noMonobitFirst;
	@FXML
	private ImageView yesMonobitFirst;
	@FXML
	private Rectangle rectangleMonobitFirst;

	/*
	 * Poker test summary
	 */
	@FXML
	private ImageView noPokerFirst;
	@FXML
	private ImageView yesPokerFirst;
	@FXML
	private Rectangle rectanglePokerFirst;

	/*
	 * Run test summary
	 */
	@FXML
	private ImageView noRunsFirst;
	@FXML
	private ImageView yesRunsFirst;
	@FXML
	private Rectangle rectangleRunsFirst;

	/*
	 * RunLong test summary
	 */
	@FXML
	private ImageView noLongRunsFirst;
	@FXML
	private ImageView yesLongRunsFirst;
	@FXML
	private Rectangle rectangleLongRunsFirst;

	/*
	 * 
	 * 
	 * Second view variables
	 * 
	 * 
	 * 
	 */
	@FXML
	private ChoiceBox<String> generatorType;

	@FXML
	private ImageView secondImageView;
	@FXML
	private TextArea runsSecond;
	@FXML
	private TextArea pokerSecond;
	@FXML
	private TextArea monobitSecond;
	@FXML
	private TextArea longRunsSecond;
	@FXML
	private Text entropySecond;

	/*
	 * Monobit test summary
	 */
	@FXML
	private ImageView noMonobitSecond;
	@FXML
	private ImageView yesMonobitSecond;
	@FXML
	private Rectangle rectangleMonobitSecond;

	/*
	 * Poker test summary
	 */
	@FXML
	private ImageView noPokerSecond;
	@FXML
	private ImageView yesPokerSecond;
	@FXML
	private Rectangle rectanglePokerSecond;

	/*
	 * Run test summary
	 */
	@FXML
	private ImageView noRunsSecond;
	@FXML
	private ImageView yesRunsSecond;
	@FXML
	private Rectangle rectangleRunsSecond;

	/*
	 * RunLong test summary
	 */
	@FXML
	private ImageView noLongRunsSecond;
	@FXML
	private ImageView yesLongRunsSecond;
	@FXML
	private Rectangle rectangleLongRunsSecond;

	/*
	 * Tab variables
	 */
	@FXML
	private Tab summaryTabSecond;
	@FXML
	private Tab runsTabSecond;
	@FXML
	private Tab pokerTabSecond;
	@FXML
	private Tab longRunsTabSecond;
	@FXML
	private Tab monobitTabSecond;
	@FXML
	private Tab entropyTabSecond;

	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		generateArrayListFirstGenerator();
		setComparePane("Generator Mouse Area", "First", firstImageView, monobitFirst, pokerFirst, runsFirst,
				longRunsFirst, entropyFirst);
		setGenerators();
	}

	private void generateArrayListFirstGenerator() {
		arrayForAnalyzing = new ArrayList<>();
		for (int i = 0; i < maximumSizeOfArray; i++) {
			arrayForAnalyzing.add(Integer.parseInt(Main.generator_configuration.COMPARE.get(i))%2);
		}
	}

	private void setComparePane(String rnd, String page, ImageView imageViewToSet, TextArea monobitText,
			TextArea pokerText, TextArea runsText, TextArea longRunsText, Text entropy) {
		// Visual comparation
		setImageView(generateRandomImage(Main.generator_configuration.imageSize), imageViewToSet);

		// Monobit test
		double monobit = MonobitTest.applyMonobitTest(arrayForAnalyzing);
		setMonobitTestImage(MonobitTest.isMonobitTestPassed(monobit), page);
		showText(monobitText,
				"Output of the Monobit test for\n" + rnd + " is:\n\n"+"X = " + monobit + "\n"
						+ "which should meet the requirements\n" + "9725 < X < 10275\n"
						+ "therefore the output of the test is\n" + MonobitTest.isMonobitTestPassed(monobit));

		// Poker test
		double poker = PokerTest.pokerTest(arrayForAnalyzing);
		setPokerTestImage(PokerTest.isPokerTestPassed(poker), page);
		showText(pokerText,
				"Output of the Poker test for\n" + rnd + " is:\n\n"+"X = " + poker + "\n"
						+ "which should meet the requirements\n" + "2.16 < X < 46.17\n"
						+ "therefore the output of the test is\n" + PokerTest.isPokerTestPassed(poker));

		// Run test
		int[] runsTestOutput = RunsTest.runsTest(arrayForAnalyzing);
		setRunTestImage(RunsTest.isRunsTestPassed(runsTestOutput), page);
		showText(runsText, "Output of the Runs test for\n" + rnd +" is:\n \n" + "NO of size 1 gaps and blocks:\n"
				+ runsTestOutput[0] + " and " + runsTestOutput[6] + "\n" + "which should meet the requirements\n"
				+ "2343 < X < 2657" + "\n \n"+"NO of size 2 gaps and blocks:\n" + runsTestOutput[1] + " and "
				+ runsTestOutput[7] + "\n" + "which should meet the requirements\n" + "1135 < X < 1365"
				+ "\n \n"+"NO of size 3 gaps and blocks:\n" + runsTestOutput[2] + " and " + runsTestOutput[8] + "\n"
				+ "which should meet the requirements\n" + "542 < X < 708" + "\n \n"+"NO of size 4 gaps and blocks:\n"
				+ runsTestOutput[3] + " and " + runsTestOutput[9]
				+ "\n"+"which should meet the requirements\n"+"251 < X < 373" + "\n \n"+"NO of size 5 gaps and blocks:\n"
				+ runsTestOutput[4] + " and " + runsTestOutput[10]
				+ "\n"+"which should meet the requirements\n"+"111 < X < 201" + "\n \n"+"NO of size 6+ gaps and blocks:\n"
				+ runsTestOutput[5] + " and " + runsTestOutput[11]
				+ "\n"+"which should meet the requirements\n"+"111 < X < 201" + "\n \n"+"therefore the output of the test is\n"
				+ RunsTest.isRunsTestPassed(runsTestOutput));

		// Long run test
		double outputofRNGglobal = LongRunsTest.longRunsTest(arrayForAnalyzing);
		setRunLongTestImage(LongRunsTest.islongRunsTestPassed(outputofRNGglobal), page);
		showText(longRunsText,
				"Output of the Long Runs test for\n" + rnd + " is:\n\n"+"X = " +  outputofRNGglobal + "\n"
						+ "which should meet the requirements\n" + "X < 26\n" + "therefore the output of the test is\n"
						+ LongRunsTest.islongRunsTestPassed(outputofRNGglobal));

		// Entropy
		entropy.setText(Double.toString(Entropy.chanceOfOneOrZero(arrayForAnalyzing)));
	}

	private void setGenerators() {
		generatorType
				.setItems(FXCollections.observableArrayList("JavaRandom RNG", "JavaMath RNG", "JavaSecureRandom RNG"));

		generatorType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				try {
					applyCustomGenerator(generatorType.getItems().get((Integer) number2));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchProviderException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}

	private void applyCustomGenerator(String string) throws Exception {
		arrayForAnalyzing = new ArrayList<>();
		switch (string) {
		case "JavaRandom RNG":
			GeneratorJavaRandom.setArray(arrayForAnalyzing, maximumSizeOfArray);
			break;
		case "JavaMath RNG":
			GeneratorMath.setArray(arrayForAnalyzing, maximumSizeOfArray);
			break;
		case "JavaSecureRandom RNG":
			GeneratorSecureRandom.setArray(arrayForAnalyzing, maximumSizeOfArray);
			break;
		default:
			throw new Exception("There is no correct id provided!");
		}
		setComparePane("Generator Mouse Area", "Second", secondImageView, monobitSecond, pokerSecond, runsSecond,
				longRunsSecond, entropySecond);
		enableSecondTabs();
	}

	private void setMonobitTestImage(boolean state, String page) {
		if (state && page.equals("First")) {
			yesMonobitFirst.setVisible(state);
			noMonobitFirst.setVisible(!state);
			rectangleMonobitFirst.setStroke(javafx.scene.paint.Color.GREEN);
		} else if (state && page.equals("Second")) {
			yesMonobitSecond.setVisible(state);
			noMonobitSecond.setVisible(!state);
			rectangleMonobitSecond.setStroke(javafx.scene.paint.Color.GREEN);
		}
	}

	private void setPokerTestImage(boolean state, String page) {
		if (state && page.equals("First")) {
			yesPokerFirst.setVisible(state);
			noPokerFirst.setVisible(!state);
			rectanglePokerFirst.setStroke(javafx.scene.paint.Color.GREEN);
		} else if (state && page.equals("Second")) {
			yesPokerSecond.setVisible(state);
			noPokerSecond.setVisible(!state);
			rectanglePokerSecond.setStroke(javafx.scene.paint.Color.GREEN);
		}
	}

	private void setRunTestImage(boolean state, String page) {
		if (state && page.equals("First")) {
			yesRunsFirst.setVisible(state);
			noRunsFirst.setVisible(!state);
			rectangleRunsFirst.setStroke(javafx.scene.paint.Color.GREEN);
		} else if (state && page.equals("Second")) {
			yesRunsSecond.setVisible(state);
			noRunsSecond.setVisible(!state);
			rectangleRunsSecond.setStroke(javafx.scene.paint.Color.GREEN);
		}
	}

	private void setRunLongTestImage(boolean state, String page) {
		if (state && page.equals("First")) {
			yesLongRunsFirst.setVisible(state);
			noLongRunsFirst.setVisible(!state);
			rectangleLongRunsFirst.setStroke(javafx.scene.paint.Color.GREEN);
		} else if (state && page.equals("Second")) {
			yesLongRunsSecond.setVisible(state);
			noLongRunsSecond.setVisible(!state);
			rectangleLongRunsSecond.setStroke(javafx.scene.paint.Color.GREEN);
		}
	}

	private void setImageView(ImagePlus imagePlus, ImageView imageView) {
		BufferedImage bf = imagePlus.getBufferedImage();
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					pw.setArgb(x, y, bf.getRGB(x, y));
				}
			}
		}
		imageView.setImage(wr);
	}

	private ImagePlus generateRandomImage(int size) {
		BufferedImage bImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		int[][] rgb = new int[size][size];
		int counter = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rgb[i][j] = isWhite(arrayForAnalyzing.get(counter++)) ? drawWhite() : drawBlack();
				bImage.setRGB(j, i, rgb[i][j]);
			}
		}
		return new ImagePlus("RGB", bImage);
	}

	private boolean isWhite(int value) {
		if (value % 2 == 0)
			return true;
		return false;
	}

	private void enableSecondTabs() {
		summaryTabSecond.setDisable(false);
		runsTabSecond.setDisable(false);
		pokerTabSecond.setDisable(false);
		longRunsTabSecond.setDisable(false);
		monobitTabSecond.setDisable(false);
		entropyTabSecond.setDisable(false);
	}

	private int drawBlack() {
		return new Color(0, 0, 0).getRGB();
	}

	private int drawWhite() {
		return new Color(255, 255, 255).getRGB();
	}

	private void showText(TextArea textArea, String text) {
		textArea.setText(text);
	}
}