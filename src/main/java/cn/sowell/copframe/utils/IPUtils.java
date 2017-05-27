package cn.sowell.copframe.utils;

import sun.net.util.IPAddressUtil;
/**
 * 
 * <p>Title: IPUtils</p>
 * <p>Description: </p><p>
 * 如果sun.net.util.IPAddressUtil的包提示不存在，那么需要配置eclipse的buildPath，添加一个accessible的条目sun/**
 * </p>
 * @author Copperfield Zhang
 * @date 2017年5月27日 上午11:21:15
 */
public class IPUtils {
	/**
	 * 判断一个ip地址是否是内网地址
	 * @param text
	 * @return
	 */
	public static boolean isInternalIp(String text) {
		if(IPAddressUtil.isIPv4LiteralAddress(text)){
			byte[] addr = IPAddressUtil.textToNumericFormatV4(text);
			final byte b0 = addr[0];
			final byte b1 = addr[1];
			// 10.x.x.x/8
			final byte SECTION_1 = 0x0A;
			// 172.16.x.x/12
			final byte SECTION_2 = (byte) 0xAC;
			final byte SECTION_3 = (byte) 0x10;
			final byte SECTION_4 = (byte) 0x1F;
			// 192.168.x.x/16
			final byte SECTION_5 = (byte) 0xC0;
			final byte SECTION_6 = (byte) 0xA8;
			switch (b0) {
			case SECTION_1:
				return true;
			case SECTION_2:
				if (b1 >= SECTION_3 && b1 <= SECTION_4) {
					return true;
				}
			case SECTION_5:
				switch (b1) {
				case SECTION_6:
					return true;
				}
			default:
				return false;
			}
		}
		return false;
	}
}
