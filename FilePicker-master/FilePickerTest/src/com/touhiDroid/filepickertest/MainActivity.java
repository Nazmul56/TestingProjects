package com.touhiDroid.filepickertest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.touhiDroid.filepicker.FilePickerActivity;

public class MainActivity extends Activity {

	private final int FILE_CHOOSER = 10001;
	private static final String tag = "MainActivity";

	private TextView tv1;
	private ImageView iv1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tv1 = (TextView) findViewById(R.id.tv_file_type);
		iv1 = (ImageView) findViewById(R.id.iv_1);

		Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, FILE_CHOOSER);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(tag, "Got result: " + (resultCode == RESULT_OK));
		if (resultCode == RESULT_OK && requestCode == FILE_CHOOSER) {
			File f = new File(data.getStringExtra(FilePickerActivity.FILE_PATH));
			byte[] baFile = new byte[(int) f.length()];
			BufferedInputStream buf;
			try {
				buf = new BufferedInputStream(new FileInputStream(f));
				buf.read(baFile, 0, baFile.length);
				buf.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			int fileFormatKey = data.getIntExtra(
					FilePickerActivity.FILE_FORMAT_KEY, 0);
			if (fileFormatKey == FilePickerActivity.PDF_FILE)
				tv1.setText("A pdf file is chosen.");
			else if (fileFormatKey == FilePickerActivity.PASSBOOK_FILE)
				tv1.setText("A passbook file is chosen");
			else if (fileFormatKey == FilePickerActivity.IMAGE_FILE) {
				handleImageFile(baFile);
			}
		}
	}

	private void handleImageFile(byte[] baFile) {
		tv1.setText("Image file is chosen.");
		Bitmap bmp = BitmapFactory.decodeByteArray(baFile, 0, baFile.length);
		// final float densityMultiplier =
		// getResources().getDisplayMetrics().density;
		//
		// int h = (int) (100 * densityMultiplier);
		// int w = (int) (h * bmp.getWidth() / ((double) bmp.getHeight()));
		//
		// bmp = Bitmap.createScaledBitmap(bmp, w, h, true);

		if (bmp != null)
			iv1.setImageBitmap(bmp);
	}

}
