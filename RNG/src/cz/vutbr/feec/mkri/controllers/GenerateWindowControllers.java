package cz.vutbr.feec.mkri.controllers;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import cz.vutbr.feec.mkri.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * This java class represents project's main generator class. The class
 * implements method for setting random generator application before starting
 * 
 * @author Yehor Safonov; id: 185942
 */

public class GenerateWindowControllers implements Initializable {
	
	/*
	 * Variables for individual GUI components. About applying minimum and
	 * maximum range
	 */
	
	@FXML
	private CheckBox checkBox_ApplyMinimumAndMaximumRange;
	@FXML
	private TextField minNumber;
	@FXML
	private TextField maxNumber;
	@FXML
	private Label minNumber_text;
	@FXML
	private Label maxNumber_text;

	/*
	 * Variables for individual GUI components. About different types of hash
	 * functions.
	 */
	private ToggleGroup toggleGroupTypeOfHash;
	@FXML
	private CheckBox checkBox_UseHashFunctions;
	@FXML
	private RadioButton radioButton_MD5;
	@FXML
	private RadioButton radioButton_SHA1;
	@FXML
	private RadioButton radioButton_SHA256;

	/*
	 * Variables for individual GUI components. About generating a random number
	 * set.
	 */
	@FXML
	private CheckBox checkBox_GenerateRandomSet;
	@FXML
	private TextField textField_numberOfItemsInsideSet;
	@FXML
	private ChoiceBox<String> choiceBox_seperatorInSet;
	@FXML
	private Label countOfItems_text;
	@FXML
	private Label separator_text;

	/*
	 * Variables for specifying advanced options About generating a random
	 * number set. Be PLEASE careful with default values
	 */
	@FXML
	private CheckBox checkBox_AllowAdvancedOptions;
	@FXML
	private CheckBox checkBox_saveOutputInTxt;
	@FXML
	private CheckBox checkBox_useMouseSeed;
	@FXML
	private CheckBox checkBox_useTimeAndDateAsSeed;

	@FXML
	private CheckBox checkBox_UseHardwareSeeds;
	@FXML
	private CheckBox checkBox_useCPUSeed;
	@FXML
	private CheckBox checkBox_useDisksSeed;
	@FXML
	private CheckBox checkBox_useGPUSeed;

	@FXML
	private CheckBox checkBox_UseCustomSeed;
	@FXML
	private TextField textField_customSeed;

	/*
	 * Variables for specifying the length of the input.
	 */
	@FXML
	private CheckBox checkBox_DefineTheLengthOfInput;
	@FXML
	private TextField textField_lengthBytes;
	@FXML
	private Label bytes_text;

	/*
	 * Variables for specifying whether user wants to generate random bytes. The
	 * output format should be defined. Default is Hexadecimal.
	 */
	private ToggleGroup toggleGroupOutPutFormat;
	@FXML
	private CheckBox checkBox_GenerateRandomBytes;
	@FXML
	private RadioButton radioButton_Hexadecimal;
	@FXML
	private RadioButton radioButton_Decimal;
	@FXML
	private RadioButton radioButton_Binary;
	@FXML
	private Label outputFormat_text;

	/*
	 * Variables for specifying if the output is in Doble format. User should
	 * specify the number of digit after comma
	 */
	@FXML
	private CheckBox checkBox_GenerateDoubleOutput;
	@FXML
	private TextField textField_NumberOfDigitsAfterComma;
	@FXML
	private Label numberOfDigitsAfterComma_text;
	@FXML
	private Button generateButton;

	/*
	 * This method is called when the application starts.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.groupHashAlgorithms();
		this.groupOutPutFormats();
		this.setSeparator();
		this.setListenersForMainCheckBox();
	}
	
	/*
	 * This method is used for setting choiseBox and its default values
	 */
	private void setSeparator() {
		choiceBox_seperatorInSet.setItems(FXCollections.observableArrayList(";", "#", "&", "@"));
		choiceBox_seperatorInSet.getSelectionModel().selectFirst();
	}
	
	/*
	 * This function is responsible for grouping existing radioButtom to one
	 * group toggleGroupTypeOfHash and applying internal ids for internal
	 * purposes.
	 */
	private void groupHashAlgorithms() {
		this.toggleGroupTypeOfHash = new ToggleGroup();

		radioButton_MD5.setToggleGroup(toggleGroupTypeOfHash);
		radioButton_SHA1.setToggleGroup(toggleGroupTypeOfHash);
		radioButton_SHA256.setToggleGroup(toggleGroupTypeOfHash);

		// radioButton_MD5.setId("MD5");
		// radioButton_SHA1.setId("SHA1");
		// radioButton_SHA256.setId("SHA256");

		radioButton_MD5.setSelected(true);

	}

	/*
	 * This function is responsible for grouping existing radioButtom to one
	 * group toggleGroupOutPutFormat and applying internal ids for internal
	 * purposes.
	 */
	private void groupOutPutFormats() {
		this.toggleGroupOutPutFormat = new ToggleGroup();

		radioButton_Binary.setToggleGroup(toggleGroupOutPutFormat);
		radioButton_Decimal.setToggleGroup(toggleGroupOutPutFormat);
		radioButton_Hexadecimal.setToggleGroup(toggleGroupOutPutFormat);

		// radioButton_Binary.setId("Binary");
		// radioButton_Decimal.setId("Decimal");
		// radioButton_Hexadecimal.setId("Hexadecimal");

		radioButton_Hexadecimal.setSelected(true);
	}

	/*
	 * This function is responsible for defining listeners for main checkboxes.
	 */
	private void setListenersForMainCheckBox() {

		checkBox_ApplyMinimumAndMaximumRange.setOnAction((ActionEvent e) -> {
			this.activateMinimumAndMaximumRandge(checkBox_ApplyMinimumAndMaximumRange.isSelected());
		});

		checkBox_UseHashFunctions.setOnAction((ActionEvent e) -> {
			activateUseHashFunctions(checkBox_UseHashFunctions.isSelected());
		});

		checkBox_GenerateRandomBytes.setOnAction((ActionEvent e) -> {
			activateGenerateRandomBytes(checkBox_GenerateRandomBytes.isSelected());
		});

		checkBox_GenerateRandomSet.setOnAction((ActionEvent e) -> {
			activateGenerateRandomSet(checkBox_GenerateRandomSet.isSelected());
		});

		checkBox_AllowAdvancedOptions.setOnAction((ActionEvent e) -> {
			activateAllowAdvancedOptions(checkBox_AllowAdvancedOptions.isSelected());

		});

		checkBox_UseCustomSeed.setOnAction((ActionEvent e) -> {
			activateUseCustomSeed(checkBox_UseCustomSeed.isSelected());
		});

		checkBox_UseHardwareSeeds.setOnAction((ActionEvent e) -> {
			activateUseHardwareSeeds(checkBox_UseHardwareSeeds.isSelected());
		});

		checkBox_DefineTheLengthOfInput.setOnAction((ActionEvent e) -> {
			activateDefineTheLengthOfInput(checkBox_DefineTheLengthOfInput.isSelected());
		});

		checkBox_GenerateRandomBytes.setOnAction((ActionEvent e) -> {
			activateGenerateRandomBytes(checkBox_GenerateRandomBytes.isSelected());
		});

		checkBox_GenerateDoubleOutput.setOnAction((ActionEvent e) -> {
			activateGenerateDoubleOutput(checkBox_GenerateDoubleOutput.isSelected());
		});

	}

	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateUseHashFunctions(boolean selected) {
		radioButton_MD5.setSelected(true);
		radioButton_MD5.setDisable(!selected);
		radioButton_SHA1.setDisable(!selected);
		radioButton_SHA256.setDisable(!selected);
	}

	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateGenerateRandomSet(boolean selected) {
		textField_numberOfItemsInsideSet.setText("1");
		textField_numberOfItemsInsideSet.setDisable(!selected);
		choiceBox_seperatorInSet.setDisable(!selected);
		choiceBox_seperatorInSet.getSelectionModel().clearSelection();
		countOfItems_text.setDisable(!selected);
		separator_text.setDisable(!selected);
	}
	
	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateAllowAdvancedOptions(boolean selected) {
		checkBox_saveOutputInTxt.setDisable(!selected);
		checkBox_useMouseSeed.setDisable(!selected);
		checkBox_useTimeAndDateAsSeed.setDisable(!selected);

		checkBox_saveOutputInTxt.setSelected(false);
		checkBox_useMouseSeed.setSelected(true);
		checkBox_useTimeAndDateAsSeed.setSelected(true);
	}
	
	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateUseCustomSeed(boolean selected) {
		textField_customSeed.setDisable(!selected);
		textField_customSeed.setText(null);
	}
	
	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateUseHardwareSeeds(boolean selected) {
		checkBox_useGPUSeed.setDisable(!selected);
		checkBox_useDisksSeed.setDisable(!selected);
		checkBox_useCPUSeed.setDisable(!selected);

		checkBox_useGPUSeed.setSelected(false);
		checkBox_useDisksSeed.setSelected(false);
		checkBox_useCPUSeed.setSelected(false);
	}

	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateDefineTheLengthOfInput(boolean selected) {
		textField_lengthBytes.setText("");
		textField_lengthBytes.setDisable(!selected);
		bytes_text.setDisable(!selected);

	}

	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateGenerateRandomBytes(boolean selected) {
		radioButton_Hexadecimal.setSelected(true);
		radioButton_Hexadecimal.setDisable(!selected);
		radioButton_Decimal.setDisable(!selected);
		radioButton_Binary.setDisable(!selected);
		outputFormat_text.setDisable(!selected);
	}

	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateGenerateDoubleOutput(boolean selected) {
		this.textField_NumberOfDigitsAfterComma.setText("");
		this.numberOfDigitsAfterComma_text.setDisable(!selected);
		this.textField_NumberOfDigitsAfterComma.setDisable(!selected);

	}
	
	/*
	 * This function is responsible for allowing to set group's parametrs. In
	 * case the main checkbox is selected.
	 */
	private void activateMinimumAndMaximumRandge(boolean selected) {
		minNumber.setText("");
		maxNumber.setText("");
		minNumber.setDisable(!selected);
		maxNumber.setDisable(!selected);
		minNumber_text.setDisable(!selected);
		maxNumber_text.setDisable(!selected);
	}

	/*
	 * This function is responsible for generating random number. Is active in
	 * case a user presses the bottom Generate random number.
	 */
	@SuppressWarnings("static-access")
	public void generateButtonPressed(ActionEvent evt) throws IOException {
		this.saveSettings(null);
		if(this.checkBox_useMouseSeed.isSelected()){
			showMouseArea(evt);
		}else {
			Main.RNG.doMyThing("-1", 0, 0, 0, 0, 0, 0, 0);
			showResult(evt);
		}
	}
	
	public void saveSettings(ActionEvent evt) {
		if(evt != null) {
			System.out.println("Save setting button pressed");
			generateButton.setDisable(true);
		}
		/*
		 * Range
		 */
		// Use range min-max
		if(this.checkBox_ApplyMinimumAndMaximumRange.isSelected()) {
			// Set minimum
			if (!this.minNumber.getText().equals(""))
				try { Main.generator_configuration.range_min = Integer.parseInt(this.minNumber.getText()); } catch (Exception e) { e.printStackTrace(); }
			// Set maximum
			if (!this.maxNumber.getText().equals(""))
				try { Main.generator_configuration.range_max = Integer.parseInt(this.maxNumber.getText()); } catch (Exception e) { e.printStackTrace(); }
		}
		else {
			Main.generator_configuration.range_min = Integer.MIN_VALUE;
			Main.generator_configuration.range_max = Integer.MAX_VALUE;
		}
			
		if(Main.generator_configuration.range_max<Main.generator_configuration.range_min) {
			int tmp = Main.generator_configuration.range_max;
			Main.generator_configuration.range_max = Main.generator_configuration.range_min;
			Main.generator_configuration.range_min = tmp;
		}
		
		/*
		 * Hash Function
		 */
		// Use hash function
		Main.generator_configuration.use_hash = this.checkBox_UseHashFunctions.isSelected();
		// Set the hash function
		Main.generator_configuration.hash_function = ((RadioButton) this.toggleGroupTypeOfHash.getSelectedToggle()).getText();
		
		/*
		 * Set
		 */
		// Use Set
		Main.generator_configuration.output_sets = this.checkBox_GenerateRandomSet.isSelected();
		// Items in set
		if(!this.textField_numberOfItemsInsideSet.getText().equals(""))
			Main.generator_configuration.array_size = Integer.parseInt(this.textField_numberOfItemsInsideSet.getText());
		// Set separator
		Main.generator_configuration.set_separator = this.choiceBox_seperatorInSet.getValue();

		/*
		 * Advanced Options
		 */
		// Use saving output into file
		Main.generator_configuration.output_file = this.checkBox_saveOutputInTxt.isSelected();
		// Use mouse input
		//Main.generator_configuration.use_mouse = this.checkBox_useMouseSeed.isSelected();
		// Use time
		Main.generator_configuration.use_time_seed = this.checkBox_useTimeAndDateAsSeed.isSelected();
		// Use hardware sensors
		Main.generator_configuration.use_hw_sensors = this.checkBox_UseHardwareSeeds.isSelected();
		// Use CPU sensors
		Main.generator_configuration.use_cpu_sensors = this.checkBox_useCPUSeed.isSelected();
		// Use GPU sensors
		Main.generator_configuration.use_gpu_sensors = this.checkBox_useGPUSeed.isSelected();
		// Use Disks sensors
		Main.generator_configuration.use_disk_sensors = this.checkBox_useDisksSeed.isSelected();
		// Use custom seed
		Main.generator_configuration.use_custom_seed = this.checkBox_UseCustomSeed.isSelected();
		// Set the custom seed
		if (this.textField_customSeed.getText() != null && !this.textField_customSeed.getText().equals(""))
			Main.generator_configuration.custom_seed = Integer.parseInt(this.textField_customSeed.getText());
		else
			Main.generator_configuration.custom_seed = Integer.MIN_VALUE;
		
		System.out.println(Main.generator_configuration.custom_seed);
		
		/*
		 * Byte
		 */
		// Use bytes output
		Main.generator_configuration.output_bytes = this.checkBox_DefineTheLengthOfInput.isSelected();
		Main.generator_configuration.use_bytes_format = this.checkBox_GenerateRandomBytes.isSelected();
		// Set byte length
		if (!this.textField_lengthBytes.getText().equals(""))
			Main.generator_configuration.bytes_length = Integer.parseInt(this.textField_lengthBytes.getText());
		else
			Main.generator_configuration.bytes_length = Integer.MAX_VALUE;
		// Set bytes output format
		Main.generator_configuration.bytes_format = ((RadioButton) this.toggleGroupOutPutFormat.getSelectedToggle()).getText();
		
		/*
		 * Double
		 */
		// Use double output
		Main.generator_configuration.output_double = this.checkBox_GenerateDoubleOutput.isSelected();
		// Set the number of digits after comma
		if(!this.textField_NumberOfDigitsAfterComma.getText().equals(""))
			Main.generator_configuration.digits_after_comma = Integer.parseInt(this.textField_NumberOfDigitsAfterComma.getText());
		
	}
	
	/*
	 * Finding and changing scene
	 */
	public void showMouseArea(ActionEvent event) throws IOException {
		Parent tmp = ((Node) event.getTarget()).getScene().getRoot();
		AnchorPane ap = (AnchorPane) tmp.lookup("#rootWindow");
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/MouseArea.fxml"));
		ap.getChildren().clear();
		ap.getChildren().setAll(pane);
	}
	
	/*
	 * Finding and changing scene 
	 */
	private void showResult(ActionEvent event) throws IOException {
		
		if(Main.generator_configuration.output_file)
			this.writeFile();
		
		Parent tmp = ((Node) event.getTarget()).getScene().getRoot();
		AnchorPane ap = (AnchorPane) tmp.lookup("#rootWindow");
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/cz/vutbr/feec/mkri/views/RandomResult.fxml"));
		ap.getChildren().clear();
		ap.getChildren().setAll(pane);
	}
	
	/*
	 * Write the RNGs into a file
	 */
	private void writeFile() {
		try {
			
			String output = "";
			if(Main.generator_configuration.array_size > 1) {
				for(String s:Main.generator_configuration.OUTPUT)
					output += s + Main.generator_configuration.set_separator;
			}
			else
				output = Main.generator_configuration.OUTPUT.get(0);
			
            Files.write(Paths.get(Main.generator_configuration.file_path), output.getBytes());
            
    		Main.generator_configuration.OUTPUT.clear();
    		Main.generator_configuration.OUTPUT.add("Data writen to file.");
    		
        } catch (Exception e) { e.printStackTrace(); }
	}
}