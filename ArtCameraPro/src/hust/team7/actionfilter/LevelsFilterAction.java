package hust.team7.actionfilter;


import hust.team7.filter.GainFilter;
import hust.team7.filter.LevelsFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;

public class LevelsFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private float lowlevels;
	private float highlevels;
	private float lowOutput;
	private float highOutput;

	/**
	Constructor LevelsFilterAction
	@param bmp
	@author 7-A Bui Quang Tan
	*/
	public LevelsFilterAction(Bitmap bmp, float lowlevels, float highlevels, float lowOutput, float highOutput) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.lowlevels = lowlevels;
		this.highlevels = highlevels;
		this.lowOutput = lowOutput;
		this.highOutput = highOutput;
	}

	/**
	Handler Levels Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		LevelsFilter filter = new LevelsFilter();
		filter.setLowLevel(lowlevels);
		filter.setHighLevel(highlevels);
		filter.setLowOutputLevel(lowOutput);
		filter.setHighOutputLevel(highOutput);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}

