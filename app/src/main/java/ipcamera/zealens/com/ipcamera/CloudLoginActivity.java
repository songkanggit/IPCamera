package ipcamera.zealens.com.ipcamera;
/*            ���豸��¼����
 * 1.��ȡNetDEVSDK.NETDEV_LoginCloud����ֵ��ΪglpcloudID�����˻���¼ID��
 * 2.��glpcloudIDΪ��λ�ȡ�豸��¼ID��glpUserID����ȡʱ����oCloudDevInfo.szDevicePassword���޵��ò�ͬ�ĺ�����NETDEV_LoginByDynamic��NETDEV_LoginCloudDev��
 * 3.��glpUserID��Ϊ0��������ת��LiveActivityʵʱ���Ž��棻
 * */


import com.sdk.NetDEVSDK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/*�ƶ��˺ŵ�¼*/
public class CloudLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud);
		NetDEVSDK.NETDEV_Init();
		Button cloudloginButton = (Button) findViewById(R.id.cloudlogin);
		final Intent intent = new Intent(this, CloudDevloginActivity.class);
		
		final EditText cloudusername = (EditText) findViewById(R.id.cloudname);
		final EditText cloudpassword = (EditText) findViewById(R.id.cloudpassword);
		final EditText URL = (EditText) findViewById(R.id.URL);
	    
		/*�ƶ��˻���¼�� ����¼�ɹ��������ƶ��豸��¼����*/
		cloudloginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String CloudUserName = cloudusername.getText().toString();
				String CloudPassword = cloudpassword.getText().toString();
				String CloudURL =URL.getText().toString();
				
				NetDEVSDK.glpcloudID = NetDEVSDK.NETDEV_LoginCloud(CloudURL,CloudUserName,CloudPassword);
				if(0 != NetDEVSDK.glpcloudID)
				{
					startActivity(intent);
				}
							
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cloud, menu);
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
