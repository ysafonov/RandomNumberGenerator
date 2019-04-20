package cz.vutbr.feec.mkri.controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import cz.vutbr.feec.mkri.generator.GeneratorJavaRandom;
import cz.vutbr.feec.mkri.generator.GeneratorMath;
import cz.vutbr.feec.mkri.generator.GeneratorSecureRandom;
import cz.vutbr.feec.mkri.tests.Entropy;
import cz.vutbr.feec.mkri.tests.LongRunTest;
import cz.vutbr.feec.mkri.tests.MonobitTest;
import cz.vutbr.feec.mkri.tests.PockerTest;
import cz.vutbr.feec.mkri.tests.RunTest;
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
 */

public class CompareWindowControllers implements Initializable {

	private int imageSize = 250;

	private int maximumSizeOfArray = imageSize * imageSize;
	private ArrayList<Integer> arrayForAnalyzing;

	/*********
	 * First test items
	 ************/
	@FXML
	private ImageView firstImageView;
	@FXML
	private TextArea runFirst;
	@FXML
	private TextArea pokerFirst;
	@FXML
	private TextArea monobitFirst;
	@FXML
	private TextArea longRunFirst;
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
	 * Pocker test summary
	 */
	@FXML
	private ImageView noPockerFirst;
	@FXML
	private ImageView yesPockerFirst;
	@FXML
	private Rectangle rectanglePockerFirst;

	/*
	 * Run test summary
	 */
	@FXML
	private ImageView noRunFirst;
	@FXML
	private ImageView yesRunFirst;
	@FXML
	private Rectangle rectangleRunFirst;

	/*
	 * RunLong test summary
	 */
	@FXML
	private ImageView noRunLongFirst;
	@FXML
	private ImageView yesRunLongFirst;
	@FXML
	private Rectangle rectangleRunLongFirst;

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
	private TextArea runSecond;
	@FXML
	private TextArea pokerSecond;
	@FXML
	private TextArea monobitSecond;
	@FXML
	private TextArea longRunSecond;
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
	 * Pocker test summary
	 */
	@FXML
	private ImageView noPockerSecond;
	@FXML
	private ImageView yesPockerSecond;
	@FXML
	private Rectangle rectanglePockerSecond;

	/*
	 * Run test summary
	 */
	@FXML
	private ImageView noRunSecond;
	@FXML
	private ImageView yesRunSecond;
	@FXML
	private Rectangle rectangleRunSecond;

	/*
	 * RunLong test summary
	 */
	@FXML
	private ImageView noRunLongSecond;
	@FXML
	private ImageView yesRunLongSecond;
	@FXML
	private Rectangle rectangleRunLongSecond;
	
	/*
	 * Tab variables
	 */
	@FXML
	private Tab summaryTabSecond;
	@FXML
	private Tab runTabSecond;
	@FXML
	private Tab pockerTabSecond;
	@FXML
	private Tab longRunTabSecond;
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
		setComparePane("Generator Mouse Area", "First", firstImageView, monobitFirst, pokerFirst, runFirst, longRunFirst, entropyFirst);
		setGenerators();
	}

	private void generateArrayListFirstGenerator() {
		Random rand = new Random();
		arrayForAnalyzing = new ArrayList<>();
		for (int i = 0; i < maximumSizeOfArray; i++) {
			arrayForAnalyzing.add(rand.nextInt(50));
		}
	}

	private void setComparePane(String rnd, String page, ImageView imageViewToSet, TextArea monobitText, TextArea pockerText, TextArea runText, TextArea longRunText, Text entropy) {
		// Visual comparation
		setImageView(generateRandomImage(imageSize), imageViewToSet);

		// Monobit test
		double monobit = MonobitTest.applyMonobitTest(arrayForAnalyzing);
		setMonobitTestImage(MonobitTest.isMonobitTestPassed(monobit), page);
		showText(monobitText,
				"Output of Monobit test for generator " + rnd + "\n is X = " + monobit
						+ "\n which should meet the requirements 9725 < X < 10275\n therefore the output of the test is: "
						+ MonobitTest.isMonobitTestPassed(monobit));

		// Pocker test
		double pocker = PockerTest.pokerTest(arrayForAnalyzing);
		setPockerTestImage(PockerTest.isPokerTestPassed(pocker), page);
		showText(pockerText,
				"Output of Poker test for generator " + rnd + "\n is X = " + pocker
						+ "\n which should meet the requirements 2.16 < X < 46.17"
						+ "\n therefore the output of the test is: " + PockerTest.isPokerTestPassed(pocker));

		// Run test
		int[] runsTestOutput = RunTest.runsTest(arrayForAnalyzing);
		setRunTestImage(RunTest.isRunsTestPassed(runsTestOutput), page);
		showText(runText,
				"Outputs of Runs test for generator " + rnd + "\n NO of size 1 gaps and blocks: " + runsTestOutput[0]
						+ " and " + runsTestOutput[6] + "\n which should meet the requirements 2343 < X < 2657"
						+ "\n \n NO of size 2 gaps and blocks: " + runsTestOutput[1] + " and " + runsTestOutput[7]
						+ "\n which should meet the requirements 1135 < X < 1365"
						+ "\n \n NO of size 3 gaps and blocks: " + runsTestOutput[2] + " and " + runsTestOutput[8]
						+ "\n which should meet the requirements 542 < X < 708" + "\n \n NO of size 4 gaps and blocks: "
						+ runsTestOutput[3] + " and " + runsTestOutput[9]
						+ "\n which should meet the requirements 251 < X < 373" + "\n \n NO of size 5 gaps and blocks: "
						+ runsTestOutput[4] + " and " + runsTestOutput[10]
						+ "\n which should meet the requirements 111 < X < 201"
						+ "\n \n NO of size 6+ gaps and blocks: " + runsTestOutput[5] + " and " + runsTestOutput[11]
						+ "\n which should meet the requirements 111 < X < 201"
						+ "\n \n therefore the output of the test is: " + RunTest.isRunsTestPassed(runsTestOutput));

		// Long run test
		double outputofRNGglobal = LongRunTest.longRunsTest(arrayForAnalyzing);
		setRunLongTestImage(LongRunTest.islongRunsTestPassed(outputofRNGglobal), page);
		showText(longRunText,
				"Output of Long Runs test for generator " + rnd + "\n is X = " + outputofRNGglobal
						+ "\n which should meet the requirements  X < 26" + "\n therefore the output of the test is: "
						+ LongRunTest.islongRunsTestPassed(outputofRNGglobal));

		// Entropy
		entropy.setText(Double.toString(Entropy.chanceOfOneOrZero(arrayForAnalyzing)));
	}

	private void setGenerators() {
		generatorType.setItems(FXCollections.observableArrayList("JavaRandom RNG", "JavaMath RNG", "JavaSecureRandom RNG"));
		
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
		setComparePane("Generator Mouse Area", "Second", secondImageView, monobitSecond, pokerSecond, runSecond, longRunSecond, entropySecond);
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

	private void setPockerTestImage(boolean state, String page) {
		if (state && page.equals("First")) {
			yesPockerFirst.setVisible(state);
			noPockerFirst.setVisible(!state);
			rectanglePockerFirst.setStroke(javafx.scene.paint.Color.GREEN);
		} else if (state && page.equals("Second")) {
			yesPockerSecond.setVisible(state);
			noPockerSecond.setVisible(!state);
			rectanglePockerSecond.setStroke(javafx.scene.paint.Color.GREEN);
		}
	}

	private void setRunTestImage(boolean state, String page) {
		if (state && page.equals("First")) {
			yesRunFirst.setVisible(state);
			noRunFirst.setVisible(!state);
			rectangleRunFirst.setStroke(javafx.scene.paint.Color.GREEN);
		} else if (state && page.equals("Second")) {
			yesRunSecond.setVisible(state);
			noRunSecond.setVisible(!state);
			rectangleRunSecond.setStroke(javafx.scene.paint.Color.GREEN);
		}
	}

	private void setRunLongTestImage(boolean state, String page) {
		if (state && page.equals("First")) {
			yesRunLongFirst.setVisible(state);
			noRunLongFirst.setVisible(!state);
			rectangleRunLongFirst.setStroke(javafx.scene.paint.Color.GREEN);
		} else if (state && page.equals("Second")) {
			yesRunLongSecond.setVisible(state);
			noRunLongSecond.setVisible(!state);
			rectangleRunLongSecond.setStroke(javafx.scene.paint.Color.GREEN);
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
		runTabSecond.setDisable(false);
		pockerTabSecond.setDisable(false);
		longRunTabSecond.setDisable(false);
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