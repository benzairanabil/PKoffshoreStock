package dev.procheck.offshore.stock.utils;

import java.util.Base64;

public class CommonFunc {

	public static String getBase64Encode(String data) {
		return (Base64.getEncoder().encodeToString(data.getBytes()));
	}

	public static String getBase64Decode(String data) {
		byte[] decodedBytes = Base64.getDecoder().decode(data);
		return (new String(decodedBytes));
	}
}
