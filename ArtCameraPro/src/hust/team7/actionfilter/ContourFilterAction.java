package hust.team7.actionfilter;


import hust.team7.filter.ContourFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class ContourFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private int levelValue;
	private float offsetValue;
	private float scaleValue;
	
	/**
	Constructor ContourFilterAction
	@param bmp, levelValue, offsetValue, scaleValue
	@author 7-A Bui Quang Tan
	*/
	public ContourFilterAction(Bitmap bmp, int levelValue, float offsetValue, float scaleValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.levelValue = levelValue;
		this.offsetValue = offsetValue;
		this.scaleValue = scaleValue;
	}
	
	/**
	Handler Contour Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		ContourFilter filter = new ContourFilter();
		filter.setLevels(levelValue);
		filter.setOffset(offsetValue);
		filter.setScale(scaleValue);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}


