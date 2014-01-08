	package hust.team7.artcamerapro;

import hust.team7.actionfilter.BlurFilterAction;
import hust.team7.actionfilter.GaussianFilterAction;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class EditActivity extends Activity implements OnClickListener {

	private ImageButton btPointilize;
	private ImageButton btGaussian;
	private ImageButton btBlur;
	private ImageButton btMoreConfig;
	private ImageButton btSelectEffect;
	private HorizontalScrollView svListEffect;
	private ImageView ivEditImage;
	private Bitmap bmpImage, bmpResult;
	private int check;
	private String file_name;
	private Boolean checkSelect = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		initView();
	}
	
	public  Bitmap rotateBitmap(String fileName) {

		Bitmap bitmap = null;
		Bitmap rotatedBitmap = null;
		Uri uri = Uri.fromFile(new File(fileName));

		try {
			bitmap = BitmapFactory.decodeFile(fileName);
			if (bitmap == null) {
				return null;
			}

			ExifInterface ex = new ExifInterface(fileName);
			int orientation = ex.getAttributeInt(ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_UNDEFINED);

			if (orientation == ExifInterface.ORIENTATION_UNDEFINED) {
				Cursor cursor = getContentResolver()
						.query(uri,
								new String[] { MediaStore.Images.ImageColumns.ORIENTATION },
								null, null, null);

				try {
					if (cursor.moveToFirst()) {
						int deg = cursor
								.getInt(cursor
										.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION));
						if (deg == 90) {
							orientation = ExifInterface.ORIENTATION_ROTATE_90;
						} else if (deg == 180) {
							orientation = ExifInterface.ORIENTATION_ROTATE_180;
						} else if (deg == 270) {
							orientation = ExifInterface.ORIENTATION_ROTATE_270;
						}
					}

					cursor.close();
				} catch (Exception e) {

				}
			}

			int degree = 0;
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree += 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree += 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree += 90;
				break;
			case ExifInterface.ORIENTATION_TRANSPOSE:
				degree += 45;
				break;
			case ExifInterface.ORIENTATION_UNDEFINED:
				degree += 360;
				break;
			default:
				break;
			}

			if (degree > 0) {
				Matrix matrix = new Matrix();
				matrix.postRotate(degree);
				rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			} else {
				rotatedBitmap = bitmap;
			}

		} catch (Exception e) {
			// handle the exception(s)
		}

		return rotatedBitmap;
	}

	private void initView() {
		btPointilize = (ImageButton) findViewById(R.id.btPointilize);
		btPointilize.setOnClickListener(this);
		btGaussian = (ImageButton) findViewById(R.id.btGaussian);
		btGaussian.setOnClickListener(this);
		btBlur = (ImageButton) findViewById(R.id.btBlur);
		btBlur.setOnClickListener(this);
		btSelectEffect = (ImageButton) findViewById(R.id.btSelectEffect);
		btSelectEffect.setOnClickListener(this);
		svListEffect = (HorizontalScrollView) findViewById(R.id.svListEffect);
		ivEditImage = (ImageView)findViewById(R.id.ivImageEdit);

		Intent i = getIntent();
		file_name = i.getStringExtra("image_name");
		//bmpImage = BitmapFactory.decodeFile(file_name);
		bmpImage = rotateBitmap(file_name);
//		File tempPhoto = new File(file_name);
//		try {
//			if (!tempPhoto.exists()) {
//				tempPhoto.createNewFile();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		// to do check null
		if(bmpImage != null){
			ivEditImage.setImageBitmap(bmpImage);
			//ivEditImage.setImageDrawable(bmp);
		}
		
		// Bitmap bmp = ((BitmapDrawable)imgView.getBackground()).getBitmap();
	}

	@Override
	/**
	Onlick check
	@param view 
	@author 7-A Nguyen Hai Duong
	*/
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btPointilize:
			check = 1;

			break;

		case R.id.btGaussian:
			check = 2;
			btMoreConfig.setVisibility(View.VISIBLE);
			if (bmpImage != null) {
				GaussianFilterAction gaussAction = new GaussianFilterAction(
						bmpImage);
				bmpResult = gaussAction.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.btBlur:
			check = 3;
			btMoreConfig.setVisibility(View.VISIBLE);
			if (bmpImage != null) {
				BlurFilterAction blurAction = new BlurFilterAction(bmpImage);
				bmpResult = blurAction.action();
				ivEditImage.setImageBitmap(bmpResult);
			} else {
				Toast.makeText(this, "Null bmpImage cmnr", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btSelectEffect:
			if (checkSelect) {
				Animation bottomUp = AnimationUtils.loadAnimation(getBaseContext(), R.anim.bottom_up);
				svListEffect.setAnimation(bottomUp);
				svListEffect.setVisibility(View.VISIBLE);
				checkSelect = false;
			}

			else {
				Animation bottomDown = AnimationUtils.loadAnimation(getBaseContext(), R.anim.bottom_down);
				svListEffect.setAnimation(bottomDown);
				
				svListEffect.setVisibility(View.INVISIBLE);
				checkSelect = true;
			}
			/*
			 * case R.id.btMoreConfig: switch (check) { case 1: break; case 2:
			 * break; case 3: break; }
			 */
		}
	}
}