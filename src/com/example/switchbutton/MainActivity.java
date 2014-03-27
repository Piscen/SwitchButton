package com.example.switchbutton;

import com.example.switchbutton.SetttingSwitchCustom.OnSwitchChangeListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SetttingSwitchCustom btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn  = (SetttingSwitchCustom) findViewById(R.id.btn);
		btn.setOnSwitchChangeListener(new OnSwitchChangeListener() {
			
			@Override
			public void onSwitchChange(boolean c) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), ""+c, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
