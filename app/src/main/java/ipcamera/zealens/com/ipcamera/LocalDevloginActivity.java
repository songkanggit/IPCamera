package ipcamera.zealens.com.ipcamera;
/*              �����豸��¼����
 * 1.��ȡNetDEVSDK.NETDEV_Login��������ֵ��ΪglpUserID���û���¼ID
 * 2.��glpUserID��Ϊ0��������ת��LiveActivityʵʱ���Ž��棻
 * */


import com.sdk.NETDEV_DEVICE_INFO_S;
import com.sdk.NetDEVSDK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/*�豸��¼����*/
public class LocalDevloginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		NetDEVSDK.NETDEV_Init();
		Button loginButton = (Button) findViewById(R.id.login);
		final Intent intent = new Intent(this, LiveActivity.class);
		
		final EditText username = (EditText) findViewById(R.id.editText1);
		final EditText password = (EditText) findViewById(R.id.editText2);
		final EditText ip = (EditText) findViewById(R.id.editText3);
	    /*��¼�豸  ����¼�ɹ�������ʵʱ����*/
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String UserName = username.getText().toString();
				String Password = password.getText().toString();
				String DevIP =ip.getText().toString();
				NETDEV_DEVICE_INFO_S oDeviceInfo = new NETDEV_DEVICE_INFO_S();
				NetDEVSDK.glpUserID = NetDEVSDK.NETDEV_Login(DevIP,0, UserName, Password, oDeviceInfo);
				if(0 != NetDEVSDK.glpUserID)
				{
					startActivity(intent);
				}
							
			}
			
		});
		
		/*ע���豸*/
		Button logoutButton = (Button)findViewById(R.id.logout);
		logoutButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				
				NetDEVSDK.NETDEV_Logout(NetDEVSDK.glpUserID);
				NetDEVSDK.glpUserID = 0;
				
			}
		});
	}
	
	/*�رս���ʱ��SDKע��*/
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		NetDEVSDK.NETDEV_UInit();
	}
}
