package cz.vutbr.feec.mkri.generator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.*;
import com.profesorfalken.jsensors.model.sensors.*;

/**
 * This Class gathers the Users Computer Component information and returns them a number.
 * The returning random number is generated using an MD5 hash.
 * 
 * @author Yehor Safonov; id: 185942
 * @author David Karger; id: 186526
 * 
 * Author of the jSensors library:
 * @author @profesorfalken
 * {https://github.com/profesorfalken/jSensors}
 */

/*
 * Component.name = Returns serial name of the component 
 *                  Examples: "Intel Core I7-9700K", "NVIDIA GeForce GCX 1080" and "WDC WDS500G2B0B"
 *                  (last one is a Western Digital 500GB M.2 SSD disk)
 * 
 * Processor sensors (cpu.sensors.*):
 *  - temperatures [List<Temperature>] = Temperatures of each core
 * 		- name -> Example: "Temp CPU Core #1"
 *  - loads [List<Load>] = Load/Usage of each core and CPUs memory load
 *  	- name -> Example: "Load CPU Core #1" + "Load Memory"
 *  - fans [List<Fan>] = Fan speed (possibly only working on Notebook; on Desktop - no data)
 * 
 * Graphics Card sensors (gpu.sensors.*):
 *  - temperatures [List<Temperature>] = Temperature of GPU core
 *  - loads [List<Load>] = Load of the GPUs components
 *  	- Components: Core, Controller, Video Engine, Memory
 *  	- name -> Example: "Load GPU Core"
 *  - fans [List<Fan>] = Fan speed of the GPU
 *  	- name -> Example: "Fan GPU"
 * 
 * Disk sensors (disk.sensors.*):
 *  - temperatures [List<Temperature>] = Temperature of Disk
 *  - loads [List<Load>] = ??? (Probably used disk space)
 *  - fans [List<Fan>] = ??? (Disk has no fans...)
 * 
 * Motherboard sensors (mobo.sensors.*): (have not tryed out yet)
 *  - temperatures
 *  - loads
 *  - fans
 */

/*
 * Getting information from components slows the generation MASSIVLY.
 * So its gathered in another thread.
 */

public class ComponentGenerator extends Thread {
	
	private MessageDigest md;
	
	private Components components;
	private List<Cpu> cpus;
	private List<Gpu> gpus;
	private List<Disk> disks;
	
	public ComponentGenerator() {
		try { md = MessageDigest.getInstance("MD5"); }
		catch(Exception e) { e.printStackTrace(); }
	}
	
	@Override
	public void run() {
		try {
			// while(true) {
				System.err.println("Reloading data from HW sensors.");
				// this.reloadData();
				//Thread.sleep(60000);
				Thread.sleep(100);
			// }
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private void reloadData() {
		// Retrieves new data from Hardware sensors.
		components = JSensors.get.components();
		
		cpus  = components.cpus;  //CPU(s)
		gpus  = components.gpus;  //GPU(s)
		disks = components.disks; //HDD(s)/SSD(s)
	}
	
	public int getRandom(int input) {
		String hash_input = Double.toString(input * (this.component_CPU() + this.component_GPU() + this.component_Disk()));
		try { return (new BigInteger( this.md.digest( hash_input.getBytes() ) ) ).intValue(); }
		catch(Exception e) { e.printStackTrace(); }
		return 0;
	}
	
	private double component_CPU() {
		
		double sum = 1;
		
		if(cpus != null) {
			for (Cpu cpu : cpus) {
				if (cpu.sensors != null) {
					
					List<Temperature> temps = cpu.sensors.temperatures;
					for (Temperature temp : temps) {
						if(temp.value!=0)
							sum += temp.value * sum * (double)temp.hashCode();
					}
					
					List<Load> loads = cpu.sensors.loads;
					for (Load load : loads) {
						if(load.value!=0)
							sum += load.value * sum * (double)load.hashCode();
					}
					
					List<Fan> fans = cpu.sensors.fans;
					for (Fan fan : fans) {
						if(fan.value!=0)
							sum += fan.value * sum * (double)fan.hashCode();
					}
				}
			}
		}
		
		return sum;
	}
	
	private double component_GPU() {
		
		double sum = 1;
		
		if(gpus != null) {
			for (final Gpu gpu : gpus) {
				if (gpu.sensors != null) {
					
					List<Temperature> temps = gpu.sensors.temperatures;
					for (final Temperature temp : temps) {
						if(temp.value!=0)
							sum += temp.value * sum * (double)temp.hashCode();
					}
					
					List<Load> loads = gpu.sensors.loads;
					for (Load load : loads) {
						if(load.value!=0)
							sum += load.value * sum * (double)load.hashCode();
					}
					
					List<Fan> fans = gpu.sensors.fans;
					for (final Fan fan : fans) {
						if(fan.value!=0)
							sum += fan.value * sum * (double)fan.hashCode();
					}
				}
			}
		}
		
		return sum;
	}
	
	private double component_Disk() {
		
		double sum = 1;
		
		if(disks != null) {
			for (final Disk disk : disks) {
				
				List<Temperature> temps = disk.sensors.temperatures;
				for (final Temperature temp : temps) {
					if(temp.value!=0)
						sum += temp.value * sum * (double)temp.hashCode();
				}
				
				List<Load> loads = disk.sensors.loads;
				for (Load load : loads) {
					if(load.value!=0)
						sum += load.value * sum * (double)load.hashCode();
				}
				
				List<Fan> fans = disk.sensors.fans;
				for (final Fan fan : fans) {
					if(fan.value!=0)
						sum += fan.value * sum * (double)fan.hashCode();
				}
	        }
		}
		
		return sum;
	}
	
	/*
	 * Code example from the Author @profesorfalken:
	 * 
	 * if(cpus != null) {
	 		for (Cpu cpu : cpus) {
	            System.out.println("Found CPU component: " + cpu.name);
	            if (cpu.sensors != null) {
	              System.out.println("Sensors: ");
	  
	              //Print temperatures
	              List<Temperature> temps = cpu.sensors.temperatures;
	              for (Temperature temp : temps) {
	                  System.out.println(temp.name + ": " + temp.value + " C");
	              }
	  
	              //Print fan speed
	              List<Fan> fans = cpu.sensors.fans;
	              for (Fan fan : fans) {
	                  System.out.println(fan.name + ": " + fan.value + " RPM");
	              }
	            }
	        }
		}
	 *	if(gpus != null) {
			for (final Gpu gpu : gpus) {
	            System.out.println("Found GPU component: " + gpu.name);
	            if (gpu.sensors != null) {
	              System.out.println("Sensors: ");
	  
	              //Print temperatures
	              List<Temperature> temps = gpu.sensors.temperatures;
	              for (final Temperature temp : temps) {
	                  System.out.println(temp.name + ": " + temp.value + " C");
	              }
	  
	              //Print fan speed
	              List<Fan> fans = gpu.sensors.fans;
	              for (final Fan fan : fans) {
	                  System.out.println(fan.name + ": " + fan.value + " RPM");
	              }
	            }
	        }
		}
	 *	if(disks != null) {
			for (final Disk disk : disks) {
	            System.out.println("Found Disk component: " + disk.name);
	            if (disk.sensors != null) {
	              System.out.println("Sensors: ");
	  
	              //Print temperatures
	              List<Temperature> temps = disk.sensors.temperatures;
	              for (final Temperature temp : temps) {
	                  System.out.println(temp.name + ": " + temp.value + " C");
	              }
	              
	            }
	        }
		}
	 */
}
