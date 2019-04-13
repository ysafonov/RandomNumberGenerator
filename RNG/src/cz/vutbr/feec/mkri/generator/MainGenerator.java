package cz.vutbr.feec.mkri.generator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;

import javax.xml.bind.DatatypeConverter;

import cz.vutbr.feec.mkri.Main;

public class MainGenerator {
	
	/*
	 * Variable used for operating the hardware sensors
	 * Objects for using the mouse and components generators
	 */
	private ComponentGenerator componentGenerator;
	private MouseGenerator mouseGenerator;
	
	/*
	 * Java.Security class for Cryptographic algorithms
	 */
	private MessageDigest md;
	
	public MainGenerator() {
		// Initialization of the Hardware sensor operating class
		this.componentGenerator = new ComponentGenerator();
		// Starting a new thread that operates a method for data retrieval from hardware sensors.
		this.componentGenerator.start();
		
		// Initialization of the Mouse generator and setting the retention of the mouse movement data
		this.mouseGenerator = new MouseGenerator(25);
	}
	
	public String calculate_RNG(String type, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int duration, int mouseButton) {
		/*
		 * Calculating the random number
		 */
		String rng = this.generate(type, areaX, areaY, screenX, screenY, mouseClicks, duration, mouseButton);
		
		String output = null;
		
		/*
		 * Deciding if the number is double or integer
		 */
		if(rng.split(".").length == 2) {
			
			byte[] before_comma = rng.split(".")[0].getBytes();
			byte[] after_comma = rng.split(".")[1].getBytes();
			
			/*
			 * Hashing random number from the this.generate method if user wants to
			 */
			if(Main.generator_configuration.use_hash) {
				this.setHashfunction();
				before_comma = this.calculate_hash(before_comma);
				after_comma = this.calculate_hash(after_comma);
			}
			
			/*
			 * Bytes output format
			 */
			if(Main.generator_configuration.output_bytes) {
				switch(Main.generator_configuration.bytes_format) {
				case "Hexadecimal":
					output = DatatypeConverter.printHexBinary(before_comma) + "." + DatatypeConverter.printHexBinary(after_comma);
					break;
				case "Decimal":
					output = (new BigInteger(before_comma)).toString() + "." + (new BigInteger(after_comma)).toString();
					break;
				case "Binary":
					output = BitSet.valueOf(before_comma).toString() + "." + BitSet.valueOf(after_comma).toString();
					break;
				default:
					System.out.println("Byte output failed!");
					break;
				}
			}
		}
		else {
			// TODO: if the output is not a double but a integer
			byte[] bytes = rng.getBytes();
			
			/*
			 * Hashing random number from the this.generate method if user wants to
			 */
			if(Main.generator_configuration.use_hash) {
				this.setHashfunction();
				bytes = this.calculate_hash(bytes);
			}
			
			/*
			 * Bytes output format
			 */
			if(Main.generator_configuration.output_bytes) {
				switch(Main.generator_configuration.bytes_format) {
				case "Hexadecimal":
					output = DatatypeConverter.printHexBinary(bytes);
					break;
				case "Decimal":
					output = (new BigInteger(bytes)).toString();
					break;
				case "Binary":
					output = BitSet.valueOf(bytes).toString();
					break;
				default:
					System.out.println("Byte output failed!");
					break;
				}
			}
			
		}
		
		return output;
	}
	
	private String generate(String type, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int duration, int mouseButton) {
		
		double sum = 1;
		
		if(Main.generator_configuration.use_mouse)
			sum *= this.mouseGenerator.getRandomNumber(type, areaX, areaY, screenX, screenY, mouseClicks, duration, mouseButton);
		if(Main.generator_configuration.use_hw_sensors)
			sum *= this.componentGenerator.getRandom();
		if(Main.generator_configuration.use_time_seed)
			sum *= this.randomizeTime();
		if(Main.generator_configuration.use_custom_seed) {
			if(Main.generator_configuration.custom_seed != 0)
				sum *= Main.generator_configuration.custom_seed;
			else if(Main.generator_configuration.custom_seed == Integer.MIN_VALUE)
				sum *= this.mouseGenerator.getRandomNumber(type, areaY, areaX, screenY, screenX, mouseClicks+1, duration+1, mouseButton+1);
		}
		
		return this.normalize(sum);
	}
	
	private String normalize(double input) {
		
		if(Main.generator_configuration.output_range) {
			if(Main.generator_configuration.range_min != 0) {
				if(input > Main.generator_configuration.range_min)
					input = input % Main.generator_configuration.range_max;
				if(input < Main.generator_configuration.range_min)
					while(input < Main.generator_configuration.range_min)
						input += Math.abs(Main.generator_configuration.range_min);
			}
			else
				input = Math.abs(input) % Main.generator_configuration.range_max;
		}
		
		if(Main.generator_configuration.output_double)
			return String.valueOf(input*Math.pow(10, -Main.generator_configuration.digits_after_comma));
		else
			return String.valueOf((int)input);
		
	}
	
	private double randomizeTime() {
		return 0;
	}
	
	private void setHashfunction() {
		// Setting the hashing algorithm
		try { this.md = MessageDigest.getInstance(Main.generator_configuration.hash_function); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	/*
	 * This method calculates the hash from the input and returns its Byte Array
	 */
	private byte[] calculate_hash(byte[] input) {
		try { return this.md.digest(input); }
		catch(Exception e) { e.printStackTrace(); }
		return null;
	}
}
