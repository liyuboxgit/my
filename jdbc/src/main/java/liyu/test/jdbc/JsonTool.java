package liyu.test.jdbc;

import java.util.ArrayList;

public class JsonTool {

	/**
	 * prefix of ascii string of native character
	 */
	private static String PREFIX = "\\u";

	/**
	 * Native to ascii string. It's same as execut native2ascii.exe.
	 * 
	 * @param str
	 * @return ascii string
	 */
	public static String native2Ascii(String str) {
		char[] chars = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			sb.append(char2Ascii(chars[i]));
		}
		return sb.toString();
	}

	/**
	 * Native character to ascii string.
	 * 
	 * @param c
	 * @return ascii string
	 */
	private static String char2Ascii(char c) {
		if (c > 255) {
			StringBuilder sb = new StringBuilder();
			sb.append(PREFIX);
			int code = (c >> 8);
			String tmp = Integer.toHexString(code);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			code = (c & 0xFF);
			tmp = Integer.toHexString(code);
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
			return sb.toString();
		} else {
			return Character.toString(c);
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String ascii2Native(String unicodeStr) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = unicodeStr.indexOf(PREFIX);
		while (index != -1) {
			sb.append(unicodeStr.substring(begin, index));
			sb.append(ascii2Char(unicodeStr.substring(index, index + 6)));
			begin = index + 6;
			index = unicodeStr.indexOf(PREFIX, begin);
		}
		sb.append(unicodeStr.substring(begin));
		return sb.toString();
	}

	/**
	 * 
	 * @param unicodeStr
	 * @return
	 */
	private static char ascii2Char(String unicodeStr) {
		if (unicodeStr.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!"\\u".equals(unicodeStr.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = unicodeStr.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = unicodeStr.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

	/**
	 * 
	 * @param jsonString
	 * @param fillStringUnit
	 * @return
	 */
	public static String formatJson(String jsonString, String fillStringUnit) {
		if (jsonString == null || jsonString.trim().length() == 0) {
			return null;
		}

		ArrayList<String> eachRowStringList = new ArrayList<String>();
		{
			String jsonTemp = jsonString;
			while (jsonTemp.length() > 0) {
				String eachRowString = getEachRowOfJsonString(jsonTemp);
				eachRowStringList.add(eachRowString.trim());

				jsonTemp = jsonTemp.substring(eachRowString.length());

			}
		}

		int fixedLenth = 0;
		for (int i = 0; i < eachRowStringList.size(); i++) {
			String token = eachRowStringList.get(i);
			int length = token.getBytes().length;
			if (length > fixedLenth && i < eachRowStringList.size() - 1
					&& eachRowStringList.get(i + 1).equals(":")) {
				fixedLenth = length;
			}
		}

		StringBuilder buf = new StringBuilder();
		int count = 0;
		for (int i = 0; i < eachRowStringList.size(); i++) {

			String token = eachRowStringList.get(i);

			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, count, fillStringUnit);
				continue;
			}
			if (token.equals(":")) {
				buf.append(" ").append(token).append(" ");
				continue;
			}
			if (token.equals("{")) {
				String nextToken = eachRowStringList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("}")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}
			if (token.equals("[")) {
				String nextToken = eachRowStringList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("]")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}

			buf.append(token);
			if (i < eachRowStringList.size() - 1
					&& eachRowStringList.get(i + 1).equals(":")) {
				int fillLength = fixedLenth - token.getBytes().length;
				if (fillLength > 0) {
					for (int j = 0; j < fillLength; j++) {
						buf.append(" ");
					}
				}
			}
		}
		return buf.toString();
	}

	/**
	 * 
	 * @param jsonString
	 * @return
	 */
	private static String getEachRowOfJsonString(String jsonString) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (jsonString.length() > 0) {
			String firstString = jsonString.substring(0, 1);
			if (!isInYinHao
					&& (firstString.equals(":") || firstString.equals("{")
							|| firstString.equals("}")
							|| firstString.equals("[")
							|| firstString.equals("]") || firstString
							.equals(","))) {
				if (buf.toString().trim().length() == 0) {
					buf.append(firstString);
				}
				break;
			}
			jsonString = jsonString.substring(1);
			if (firstString.equals("\\")) {
				buf.append(firstString);
				buf.append(jsonString.substring(0, 1));
				jsonString = jsonString.substring(1);
				continue;
			}
			if (firstString.equals("\"")) {
				buf.append(firstString);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}
			}
			buf.append(firstString);
		}
		return buf.toString();
	}

	private static void doFill(StringBuilder buf, int count,
			String fillStringUnit) {
		buf.append("\n");
		for (int i = 0; i < count; i++) {
			buf.append(fillStringUnit);
		}
	}

}