package com.example.mybatisplus;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.Optional;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 15:42
 * @Description:
 * @Modified By:
 */
@Slf4j
public class ComputerIdentifier {
	public static void main(String[] args) {
		String generateLicenseKey = generateLicenseKey();
		System.out.println(generateLicenseKey);

		/*  *//**
				 * 获得jvm的堆内存代码
				 */
		/*
		 * MemoryUsage heapMemoryUsage =
		 * ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		 * System.out.println("jvm.heap.init is " + (heapMemoryUsage.getInit()));
		 * System.out.println("jvm.heap.used is " + (heapMemoryUsage.getUsed()));
		 * System.out.println("jvm.heap.committed is " +
		 * (heapMemoryUsage.getCommitted())); System.out.println("jvm.heap.max is " +
		 * (heapMemoryUsage.getMax()));
		 *//**
			 * 获得jvm的非堆内存代码
			 */
		/*
		 * MemoryUsage nonHeapMemoryUsage =
		 * ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();
		 * System.out.println("jvm.nonheap.init is " + (nonHeapMemoryUsage.getInit()));
		 * System.out.println("jvm.nonheap.used is " + (nonHeapMemoryUsage.getUsed()));
		 * System.out.println("jvm.nonheap.committed is " +
		 * (nonHeapMemoryUsage.getCommitted()));
		 * System.out.println("jvm.nonheap.max is " + (nonHeapMemoryUsage.getMax()));
		 * 
		 * 
		 *//**
			 * 上面的方法只能得到jvm的堆和非堆的整体数据，一般都知道堆和非堆里面都几个不同的区，用来做不同功能， 那么如何得到不同区的数据呢？不多说，上代码
			 *//*
				 * for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
				 * final String kind = pool.getType() == MemoryType.HEAP ? "heap" : "nonheap";
				 * final MemoryUsage usage = pool.getUsage(); System.out.println("kind is " +
				 * kind + ", pool name is " + pool.getName() + ", jvm." + pool.getName() +
				 * ".init is " + usage.getInit()); System.out.println("kind is " + kind +
				 * ", pool name is " + pool.getName() + ", jvm." + pool.getName() + ".used is "
				 * + usage.getUsed()); System.out.println("kind is " + kind + ", pool name is "
				 * + pool.getName() + ", jvm." + pool.getName()+ ".committed is " +
				 * usage.getCommitted()); System.out.println("kind is " + kind +
				 * ", pool name is " + pool.getName() + ", jvm." + pool.getName() + ".max is " +
				 * usage.getMax()); }
				 */
	}

	public static String generateLicenseKey() {

		SystemInfo systemInfo = new SystemInfo();
		OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
		HardwareAbstractionLayer hardware = systemInfo.getHardware();
		CentralProcessor processor = hardware.getProcessor();
		NetworkIF[] networkIFs = hardware.getNetworkIFs();
		for (NetworkIF net : networkIFs) {
			String name = net.getName();
			// String[] iPv4addr = net.getIPv4addr();
			String[] iPv4addr = null;
			boolean present = Optional.ofNullable(iPv4addr).isPresent();
			if (present) {
				System.out.println("ipv4: " + iPv4addr[0]);
			}
			String displayName = net.getDisplayName();
			System.out.println("name: " + name + "; displayName: " + displayName);
		}

		// Manufacturer
		String manufacturer = operatingSystem.getManufacturer();

		// Get the System/CPU Serial Number, if available.
		String systemSerialNumber = processor.getSystemSerialNumber();

		// Identifier,
		String identifier = processor.getIdentifier();

		// Get the number of logical CPUs available for processing.
		int logicalProcessorCount = processor.getLogicalProcessorCount();
		long[] cpuLoadTicks = processor.getSystemCpuLoadTicks();
		long user = cpuLoadTicks[CentralProcessor.TickType.USER.getIndex()];
		long nice = cpuLoadTicks[CentralProcessor.TickType.NICE.getIndex()];
		long sys = cpuLoadTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
		long idle = cpuLoadTicks[CentralProcessor.TickType.IDLE.getIndex()];
		long iowait = cpuLoadTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
		long irq = cpuLoadTicks[CentralProcessor.TickType.IRQ.getIndex()];
		long softirq = cpuLoadTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];

		return String.format(">>>manufacturer:[%s],systemSerialNumber:[%s],identifier:[%s],count:[%s]", manufacturer,
				systemSerialNumber, identifier, logicalProcessorCount);

	}

}
