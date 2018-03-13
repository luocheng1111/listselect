package com.demo.library.listsellibrary.utils;

import android.content.Context;
import android.util.TypedValue;

public class DensityUtils {

	/**
	 * dp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, context.getResources().getDisplayMetrics());
	}
	

	/**
	 * sp转px
	 * 
	 * @param context
	 * @param val
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spValue, context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 * 
	 * @param context
	 * @param pxVal
	 * @return
	 */
	public static float px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxValue / scale);
	}

	/**
	 * px转sp
	 * 
	 * @param fontScale
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(Context context, float pxValue) {
		return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
	}

}
