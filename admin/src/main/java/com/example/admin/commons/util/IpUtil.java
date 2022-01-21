package com.example.admin.commons.util;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-11 11:16
 * @Description:
 * @Modified By:
 */
public class IpUtil {

	private static final String CMD_1 = "ifconfig ";

	private static final String CMD_2 = "cmd /c ipconfig /all";

	public static void main(String[] args) {
		// getLocalHost();

		// 获取默认网关
		String defaultGateway = getLocalMachineInfo("Default Gateway . . . . . . . . . :");
		System.out.println("机器名：" + defaultGateway);
		// 获取DNS服务器
		String DNSServers = getLocalMachineInfo("DNS Servers . . . . . . . . . . . :");
		System.out.println("机器名：" + DNSServers);

		getInfo();

	}

	/**
	 * 获取当前服务IP地址
	 */
	public static void getLocalHost() {
		try {
			InetAddress ip4 = Inet4Address.getLocalHost();
			System.out.println(ip4.getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getLocalMachineInfo(String str) {
		String line = "";
		int n;
		try {
			Process ps = Runtime.getRuntime().exec(CMD_1);
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			while (null != (line = br.readLine())) {
				if (line.indexOf(str) != -1) {
					n = line.indexOf(":");
					line = line.substring(n + 2);
					break;
				}
			}
			ps.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	public static void getInfo() {
		try {
			Process ps = Runtime.getRuntime().exec(CMD_1);
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
