package hust.team7.actionfilter;


import hust.team7.filter.PointillizeFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;
import android.graphics.Color;

public class PointillizeFilterAction {

	private Bitmap bmp;
	private int width;
	private int height;
	private int[] colors;
	private int sizeValue;
	private float randomnessValue;
	private float fuzzinessValue;
	
	/**
	Constructor PointillizeFilterAction
	@param bmp, sizeValue, randomnessValue, fuzzinessValue
	@author 7-A Bui Quang Tan
	*/
	public PointillizeFilterAction(Bitmap bmp, int sizeValue, float randomnessValue, float fuzzinessValue) {
		this.bmp = bmp;
		this.width = bmp.getWidth();
		this.height = bmp.getHeight();
		this.sizeValue = sizeValue;
		this.randomnessValue = randomnessValue;
		this.fuzzinessValue = fuzzinessValue;
	}
	
	/**
	Handler Pointillize Effect
	@param no param
	@author 7-A Bui Quang Tan
	*/
	public Bitmap action() {
		colors = AndroidUtils.bitmapToIntArray(this.bmp);
		PointillizeFilter filter = new PointillizeFilter();
		filter.setEdgeColor(Color.BLACK);
		filter.setScale(sizeValue);
		filter.setRandomness(randomnessValue);
		filter.setAmount(0);
		filter.setFuzziness(fuzzinessValue);
		filter.setGridType(PointillizeFilter.HEXAGONAL);
		colors = filter.filter(colors, width, height);
		bmp = Bitmap.createBitmap(colors, 0, width, width, height,
				Bitmap.Config.ARGB_8888);
		return bmp;

	}

}



