package hust.team7.artcamerapro;

import hust.team7.actionfilter.BlurFilterAction;
import hust.team7.actionfilter.GaussianFilterAction;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
		bmpImage = BitmapFactory.decodeFile(file_name);
		// to do check null
		if(bmpImage != null){
			ivEditImage.setImageBitmap(bmpImage);
		}
		
		// Bitmap bmp = ((BitmapDrawable)imgView.getBackground()).getBitmap();
	}

	@Override
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
				svListEffect.setVisibility(View.VISIBLE);
				checkSelect = false;
			}

			else {
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
