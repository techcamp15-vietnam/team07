package hust.team7.actionfilter;

import hust.team7.filter.PointillizeFilter;
import hust.team7.filter.util.AndroidUtils;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

public class PointillizeFilterAction {
	private Bitmap bmp;
	private int width;
	private int height;
	private float sizeValue = 7f;
	private float randomnessValue = 0.25f;
	private float fuzzinessValue = 0.05f;
	private int[] colors;

	// private ImageView ivEditImage;

	public PointillizeFilterAction(Bitmap bmp, ImageView ivEditImage) {
		this.bmp = bmp;
		this.width = ivEditImage.getDrawable().getIntrinsicWidth();
		this.height = ivEditImage.getDrawable().getIntrinsicHeight();
		colors = AndroidUtils.drawableToIntArray(ivEditImage.getDrawable());
	}

	public Bitmap action() {
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

		/*
		 * new Thread(new Runnable() {
		 * 
		 * @Override public void run() { PointillizeFilter filter = new
		 * PointillizeFilter(); filter.setEdgeColor(Color.BLACK);
		 * filter.setScale(sizeValue); filter.setRandomness(randomnessValue);
		 * filter.setAmount(0); filter.setFuzziness(fuzzinessValue);
		 * filter.setGridType(PointillizeFilter.HEXAGONAL); colors =
		 * filter.filter(colors, width, height); bmp =
		 * Bitmap.createBitmap(colors, 0, width, width, height,
		 * Bitmap.Config.ARGB_8888); ivEditImage.setImageBitmap(bmp); }
		 * }).start();
		 */

	}

}
