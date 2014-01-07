package com.example.appcamera;

import android.R.color;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private ImageButton btnButton;
	private HorizontalScrollView honHori;
	private RelativeLayout rLayout;
	private Boolean test = true;
	private TextView mTv1;
	private ImageButton img1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		initView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initView() {
		btnButton = (ImageButton) findViewById(R.id.menuToggle);
		btnButton.setOnClickListener(this);
		honHori = (HorizontalScrollView) findViewById(R.id.scrollView);
		rLayout = (RelativeLayout) findViewById(R.id.layout_btn1);
		rLayout.setOnClickListener(this);
		img1 =(ImageButton) findViewById(R.id.image1);
		img1.setOnClickListener(this);
		mTv1 = (TextView) findViewById(R.id.textview1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.menuToggle:
			if (test)
			// Toast.makeText(getBaseContext(),"pic selection1",Toast.LENGTH_SHORT).show();
			{
				
				
				honHori.setVisibility(View.INVISIBLE);
				test = false;
			} else {
				honHori.setVisibility(View.VISIBLE);
				test = true;
			}
			break;
		case R.id.layout_btn1:
			mTv1.setTextColor(Color.BLUE);

			break;
		case R.id.image1:
			mTv1.setTextColor(Color.GREEN);
			break;
		default:
			break;
		}
	}

}
