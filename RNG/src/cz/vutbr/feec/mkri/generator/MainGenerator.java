package cz.vutbr.feec.mkri.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.BitSet;

import javax.xml.bind.DatatypeConverter;

import cz.vutbr.feec.mkri.Main;

public class MainGenerator {
	
	// Retention of the mouse generator
	private final int RETENTION = 50;
	
	// rng number for combining
	private double rng = 0;
	private int combined = 1;
	
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
		// Initialization of the Mouse generator and setting the retention of the mouse movement data
		this.mouseGenerator = new MouseGenerator(this.RETENTION);
		
		// Initialization of the Hardware sensor operating class
		this.componentGenerator = new ComponentGenerator();
		// Starting a new thread that operates a method for data retrieval from hardware sensors.
		this.componentGenerator.start();
	}
	
	public void killThread() { this.componentGenerator.run = false; }
	
	/*
	 * Calculating the random number based on mouse input
	 */
	public void doMyThing(String type, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int duration, int mouseButton) {
		Double tmp = this.generate(type, areaX, areaY, screenX, screenY, mouseClicks, duration, mouseButton);
		this.combined++;
		if(Main.generator_configuration.combine_count > 1) {
			if(this.combined < Main.generator_configuration.combine_count)
			this.rng *= tmp;
		}
		else {
			this.combined=1;
			this.rng = tmp;
		}
		
		if(Main.generator_configuration.combine_count == this.combined) {
			if(Main.generator_configuration.set_items > 1)
				Main.generator_configuration.OUTPUT.add(this.outputFormat(this.normalize(this.rng)));
			else {
				if(Main.generator_configuration.OUTPUT.size()==0)
					Main.generator_configuration.OUTPUT.add(this.outputFormat(this.normalize(this.rng)));
				else
					Main.generator_configuration.OUTPUT.set(0,this.outputFormat(this.normalize(this.rng)));
			}
		}
	}
	
	private Double generate(String type, int areaX, int areaY, int screenX, int screenY, int mouseClicks, int duration, int mouseButton) {
		
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
				sum *= this.mouseGenerator.getRandomNumber(type, areaY, areaX, screenY, screenX, mouseClicks+1, duration, mouseButton);
		}
		
		return sum;
	}
	
	private double normalize(double input) {
		
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
			return input*Math.pow(10, -Main.generator_configuration.digits_after_comma);
		else
			return input;
		
	}
	
	private String outputFormat(double input) {
		try {
			String output = "";
			if(Main.generator_configuration.output_double) {
				double tmp = input;
				
				output = Double.toString(tmp);
				// Converting double to an byte[]
				byte[] bytes = this.doubleToByteArray(tmp);
				
				// Applying hash function
				if(Main.generator_configuration.use_hash) {
					this.setHashfunction();
					bytes = this.calculate_hash(bytes);
				}
				
				// Converting byte array back to double
				tmp = this.convertByteArrayToDouble(bytes);
				
				if(Main.generator_configuration.digits_after_comma>0) {
					tmp = Double.parseDouble(String.format("%."+ Main.generator_configuration.digits_after_comma +"f", tmp));
					if(Main.generator_configuration.use_bytes_format)
						output = this.bytesFormatDouble(tmp);
				}
				else if (Main.generator_configuration.digits_after_comma==0) {
					if(Main.generator_configuration.use_bytes_format)
						output = this.bytesFormatInt((int)tmp);
				}
			}
			else {
				int tmp = (int) input;
				output = Integer.toString(tmp);
				
				// Converting double to an byte[]
				byte[] bytes = this.intToBytes(tmp);
				
				// Applying hash function
				if(Main.generator_configuration.use_hash) {
					this.setHashfunction();
					bytes = this.calculate_hash(bytes);
				}
				
				// Converting bytearray back to double
				tmp = this.bytesToInt(bytes);
				
				if(Main.generator_configuration.use_bytes_format)
					output = this.bytesFormatInt(tmp);
			}
			
			return output;
			
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	// Format the output
	private String bytesFormatDouble(double input) {
		String output = "";
		switch(Main.generator_configuration.bytes_format) {
		case "Hexadecimal":
			output = Double.toHexString(input);
			break;
		case "Decimal":
			output = String.valueOf(input);
			break;
		case "Binary":
			output = Long.toBinaryString(Double.doubleToLongBits(input));
			break;
		default:
			System.out.println("Byte formating failed!");
			break;
		}
		return output;
	}
	
	private String bytesFormatInt(int input) {
		String output = "";
		switch(Main.generator_configuration.bytes_format) {
		case "Hexadecimal":
			output = Integer.toHexString(input);
			break;
		case "Decimal":
			output = String.valueOf(input);
			break;
		case "Binary":
			output = Integer.toBinaryString(input);
			break;
		default:
			System.out.println("Byte formating failed!");
			break;
		}
		return output;
	}
	
	private byte[] doubleToByteArray ( final double i ) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeDouble(i);
		dos.flush();
		return bos.toByteArray();
	}
	
	private double convertByteArrayToDouble(byte[] doubleBytes){
		ByteBuffer byteBuffer = ByteBuffer.allocate(Double.BYTES);
		byteBuffer.put(doubleBytes);
		byteBuffer.flip();
		return byteBuffer.getDouble();
	}
	
	private byte[] intToBytes(int my_int) throws IOException {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    ObjectOutput out = new ObjectOutputStream(bos);
	    out.writeInt(my_int);
	    out.close();
	    byte[] int_bytes = bos.toByteArray();
	    bos.close();
	    return int_bytes;
	}
	
	private int bytesToInt(byte[] int_bytes) throws IOException {
	    ByteArrayInputStream bis = new ByteArrayInputStream(int_bytes);
	    ObjectInputStream ois = new ObjectInputStream(bis);
	    int my_int = ois.readInt();
	    ois.close();
	    return my_int;
	}
	
	// TODO: Randomize Time
	private double randomizeTime() {
		double date = (double)(LocalDate.now().getDayOfYear()*LocalDate.now().getYear()/LocalDate.now().getDayOfMonth()*LocalDate.now().getMonthValue());
		double time = (double)(LocalTime.now().getHour()*LocalTime.now().getMinute()*LocalTime.now().getSecond());
		System.out.println(date + "\n" + time);
		return time/date;
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
	
	/*
	 * Write the RNGs into a file
	 */
	private void writeFile() {
		String output = "";
		if(Main.generator_configuration.set_items > 1) {
			for(String s:Main.generator_configuration.OUTPUT)
				output += s + Main.generator_configuration.set_separator;
		}
		else
			output = Main.generator_configuration.OUTPUT.get(0);
		
		try {
            Files.write(Paths.get(Main.generator_configuration.file_path), output.getBytes());
        } catch (IOException e) {
        	System.err.println("WRONG FILE PATH!!!");
            e.printStackTrace();
        }
	}
}
