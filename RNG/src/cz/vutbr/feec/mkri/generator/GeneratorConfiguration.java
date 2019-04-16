package cz.vutbr.feec.mkri.generator;

import java.util.ArrayList;

/**
 * This class is a configuration class.
 * Used for the Main Random Numbers Generator class.
 * 
 * @author xkarge02
 */

public class GeneratorConfiguration {
	
	/*
	 * Output of the generator in case needing more than 1 number
	 */
	public ArrayList<String> OUTPUT = new ArrayList<>();
	public int output_count = 1;
	
	public int combine_count = 1;
	
	/*
	 * Default and only primes output format
	 * Only maximum is used in the only primes output
	 */
	public boolean output_range = false;
	// Configuration of the minimum and maximum
	public int range_min = -100;
	public int range_max = 100;
	
	/*
	 * Hash function configuration
	 */
	public boolean use_hash = false;
	// Options: MD5 | SHA-1 | SHA-256
	public String hash_function = "MD5";
	
	/*
	 * Set output format
	 */
	public boolean output_sets = false;
	// How many items in a set
	public int set_items = 1;
	// Item separator
	public String set_separator = ";";
	
	/*
	 * Configuration seeds
	 */
	public boolean use_mouse = true;
	public boolean use_hw_sensors = false;
	
	public boolean use_cpu_sensors = false;
	public boolean use_gpu_sensors = false;
	public boolean use_disk_sensors = false;
	
	public boolean use_custom_seed = false;
	public int custom_seed = 0;
	
	public boolean use_time_seed = false;
	
	/*
	 * Double output format
	 */
	public boolean output_double = false;
	// How many digits should be after the comma e.g. 2 -> 0,02
	public int digits_after_comma = 0;
	
	/*
	 * Bytes output format
	 */
	public boolean output_bytes = false;
	// Configuration of output length
	public int bytes_length = 16;
	// Formats: Hex | Decimal | Binary
	public String bytes_format = "Hex";
	
	/*
	 * Output to file
	 */
	public boolean output_file = false;
	// Path to a file where the output should be saved
	public String file_path = "output/rng.txt";
	
	/*
	 * Gaussian output format
	 */
	// ???
	//public boolean output_gaussian = false;
}
