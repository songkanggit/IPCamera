package com.example.dev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


/*?????*/
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button dev = (Button) findViewById(R.id.dev);
		final Intent intentdev = new Intent(this, LocalDevloginActivity.class);
		Button cloud = (Button) findViewById(R.id.cloud);
		final Intent intentcloud = new Intent(this, CloudLoginActivity.class);
		
		
		/*?????��???????*/
        dev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					startActivity(intentdev);
				}
		});

        
        /*????????????*/ 
        cloud.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					startActivity(intentcloud);		
			}	
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
