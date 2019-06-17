package com.example.a11708.graduationproject.Utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.content.SharedPreferences.Editor;

import com.example.a11708.graduationproject.AllApplication;


public class SharedPreferencesUtils {

	private static final String Prefer = "bs";

	private static SharedPreferences sharedPreferences;

	private Context context;

	protected SharedPreferencesUtils() {
	}

	private static Editor getEditor(String name) {
		SharedPreferences sp = getSharedPreferences(name);
		return sp == null ? null : sp.edit();
	}


	public synchronized static SharedPreferences getSharedPreferences(String preferenceName) {
		if (AllApplication.getInstance() == null || TextUtils.isEmpty(preferenceName)) {
			return null;
		}
		SharedPreferences sharedPreferences = AllApplication.getInstance().getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		return sharedPreferences;
	}



	// Boolean
	public static void setValue(String key, boolean value) {
		setValue(Prefer, key, value);
	}

	// Float
	public static void setValue(String key, float value) {
		setValue(Prefer, key, value);
	}

	// Integer
	public static void setValue(String key, int value) {
		setValue(Prefer, key, value);
	}

	// Long
	public static void setValue(String key, long value) {
		setValue(Prefer, key, value);
	}

	// String
	public static void setValue(String key, String value) {
		setValue(Prefer, key, value);
	}
	// Get

	// Boolean
	public static boolean getValue(String key, boolean defaultValue) {
		return getValue(Prefer, key, defaultValue);
	}


	// Float
	public static float getValue(String key, float defaultValue) {
		return getValue(Prefer, key, defaultValue);
	}


	// Integer
	public static int getValue(String key, int defaultValue) {
		return getValue(Prefer, key, defaultValue);
	}


	// Long
	public static long getValue(String key, long defaultValue) {
		return getValue(Prefer, key, defaultValue);
	}


	// String
	public static String getValue(String key) {
		return getValue(key, "");
	}

	public static String getValue(String key, String defaultValue) {
		return getValue(Prefer, key, defaultValue);
	}


	public static boolean contains(String key) {
		return contains(Prefer, key);
	}

	/**
	 * @param name  储存sp的xml名字
	 * @param key   sp的key
	 * @param value sp的value
	 */
	// Boolean
	public static void setValue(String name, String key, boolean value) {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		Editor edit = getEditor(name);
		if (edit == null) {
			return;
		}
		edit.putBoolean(key, value);
		handleEdit(edit);

	}


	/**
	 * @param name  储存sp的xml名字
	 * @param key   sp的key
	 * @param value sp的value
	 */
	// Float
	public static void setValue(String name, String key, float value) {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		Editor edit = getEditor(name);
		if (edit == null) {
			return;
		}
		edit.putFloat(key, value);
		handleEdit(edit);
	}


	/**
	 * @param name  储存sp的xml名字
	 * @param key   sp的key
	 * @param value sp的value
	 */
	// Integer
	public static void setValue(String name, String key, int value) {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		Editor edit = getEditor(name);
		if (edit == null) {
			return;
		}
		edit.putInt(key, value);
		handleEdit(edit);
	}


	/**
	 * @param name  储存sp的xml名字
	 * @param key   sp的key
	 * @param value sp的value
	 */
	// Long
	public static void setValue(String name, String key, long value) {
		if (TextUtils.isEmpty(key)) {
			return;
		}
		Editor edit = getEditor(name);
		if (edit == null) {
			return;
		}
		edit.putLong(key, value);
		handleEdit(edit);
	}


	/**
	 * @param name  储存sp的xml名字
	 * @param key   sp的key
	 * @param value sp的value
	 */
	// String
	public static void setValue(String name, String key, String value) {
		if (TextUtils.isEmpty(key) || value == null) {
			return;
		}
		Editor edit = getEditor(name);
		if (edit == null) {
			return;
		}
		edit.putString(key, value.trim());
		handleEdit(edit);
	}


	// Get

	/**
	 * @param name         储存sp的xml名字
	 * @param key          sp的key
	 * @param defaultValue 默认值
	 */
	// Boolean
	public static boolean getValue(String name, String key, boolean defaultValue) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
			return false;
		}
		SharedPreferences sp = getSharedPreferences(name);
		if (sp == null) {
			return false;
		}
		return sp.getBoolean(key, defaultValue);
	}


	/**
	 * @param name         储存sp的xml名字
	 * @param key          sp的key
	 * @param defaultValue 默认值
	 */
	// Float
	public static float getValue(String name, String key, float defaultValue) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
			return 0;
		}
		SharedPreferences sp = getSharedPreferences(name);
		if (sp == null) {
			return 0;
		}
		return sp.getFloat(key, defaultValue);
	}


	/**
	 * @param name         储存sp的xml名字
	 * @param key          sp的key
	 * @param defaultValue 默认值
	 */
	// Integer
	public static int getValue(String name, String key, int defaultValue) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
			return 0;
		}
		SharedPreferences sp = getSharedPreferences(name);
		if (sp == null) {
			return 0;
		}
		return sp.getInt(key, defaultValue);
	}


	/**
	 * @param name         储存sp的xml名字
	 * @param key          sp的key
	 * @param defaultValue 默认值
	 */
	// Long
	public static long getValue(String name, String key, long defaultValue) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
			return 0;
		}
		SharedPreferences sp = getSharedPreferences(name);
		if (sp == null) {
			return 0;
		}
		return sp.getLong(key, defaultValue);
	}


	/**
	 * @param name         储存sp的xml名字
	 * @param key          sp的key
	 * @param defaultValue 默认值
	 */
	// String
	public static String getValue(String name, String key, String defaultValue) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
			return "";
		}
		SharedPreferences sp = getSharedPreferences(name);
		if (sp == null) {
			return defaultValue;
		}
		return sp.getString(key, defaultValue);
	}


	/**
	 * @param name 储存sp的xml名字
	 * @param key  sp的key
	 */
	public static boolean contains(String name, String key) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
			return false;
		}
		SharedPreferences sp = getSharedPreferences(name);
		if (sp == null) {
			return false;
		}
		return sp.contains(key);
	}


	//DELETE
	public static void remove(String key) {
		remove(Prefer, key);
	}

	private static void remove(String name, String key) {
		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(key)) {
			return;
		}
		Editor edit = getEditor(name);
		if (edit == null) {
			return;
		}
		edit.remove(key);
		handleEdit(edit);
	}



	private static void handleEdit(Editor edit) {
		if (edit == null) {
			return;
		}
		//android4.0及以上可以用apply
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			edit.apply();
		} else {
			edit.commit();
		}
	}

}
