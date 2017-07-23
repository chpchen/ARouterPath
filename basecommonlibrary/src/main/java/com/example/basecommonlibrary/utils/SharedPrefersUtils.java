/**   
 * Copyright © 2015 公司名. All rights reserved.
 * 
 * @Title: SharedPrefersUtils.java 
 * @Prject: CheYiPai_AndroidUpdate
 * @Package: com.cyp.p.utils 
 * @Description: TODO
 * @author: Administrator   
 * @date: 2015-6-24 下午6:20:16 
 * @version: V1.0   
 */
package com.example.basecommonlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName: SharedPrefersUtils
 * @Description: TODO
 * @author: SHAOS
 * @date: 2015-6-24 下午6:20:16
 */
public class SharedPrefersUtils {

	/**
	 * 保存在手机里面的文件名
	 */
	public static final String FILE_NAME = "share_data";
	public static final String CONFIG = "config";
	public static final String CLIENT_ID = "ClientID";

	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 *
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}



	/**
	 * 移除某个key值已经对应的值
	 *
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 *
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 *
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 *
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 *
	 * @author zhy
	 *
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 *
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 *
		 * @param editor
		 */
		public static void apply(Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

	public final static String SHARED = "sharedpreference";

	public static void putValue(Context context, String key, int value) {
		Editor sp = context.getApplicationContext().getSharedPreferences(SHARED, Context.MODE_PRIVATE)
				.edit();
		sp.putInt(key, value);
		sp.commit();
	}

	public static void putValue(Context context, String key, boolean value) {
		Editor sp = context.getApplicationContext().getSharedPreferences(SHARED, Context.MODE_PRIVATE)
				.edit();
		sp.putBoolean(key, value);
		sp.commit();
	}

	public static void putValue(Context context, String key, String value) {
		Editor sp = context.getApplicationContext().getSharedPreferences(SHARED, Context.MODE_PRIVATE)
				.edit();
		sp.putString(key, value);
		sp.commit();
	}

	public static int getValue(Context context, String key, int defValue) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SHARED,
				Context.MODE_PRIVATE);
		int value = sp.getInt(key, defValue);
		return value;
	}

	public static boolean getValue(Context context, String key, boolean defValue) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SHARED,
				Context.MODE_PRIVATE);
		boolean value = sp.getBoolean(key, defValue);
		return value;
	}

	public static String getValue(Context context, String key, String defValue) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SHARED,
				Context.MODE_PRIVATE);
		String value = sp.getString(key, defValue);
		return value;
	}

	public static void clearSharedValue(Context context) {
		SharedPreferences sp = context.getApplicationContext().getSharedPreferences(SHARED,
				Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}

	public static void clearSharedOtherValue(Context context, String keyPos,
                                             int defValue, String[] conditionList) {
		Editor sp = context.getSharedPreferences(SHARED, Context.MODE_PRIVATE)
				.edit();
		for (int i = 0; i < conditionList.length; i++) {
			if (!conditionList[i].equals(keyPos)) {
				sp.putInt(conditionList[i], defValue);
				sp.commit();
			}
		}
	}
}
