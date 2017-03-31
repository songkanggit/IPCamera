package ipcamera.zealens.com.ipcamera;


import com.sdk.NETDEV_CLOUD_DEV_INFO_S;
import com.sdk.NETDEV_CLOUD_DEV_LOGIN_S;
import com.sdk.NETDEV_DEVICE_INFO_S;
import com.sdk.NetDEVSDK;













import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/*�ƶ��豸��¼����*/
public class CloudDevloginActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devlogin);
		NetDEVSDK.NETDEV_Init();
		Button devloginButton = (Button) findViewById(R.id.devlogin);
		final Intent intent = new Intent(this, LiveActivity.class);
		Button getdevlistButton = (Button) findViewById(R.id.getdevlist);
		
		
		final EditText Devname = (EditText) findViewById(R.id.Devname);
		final EditText Devpassword = (EditText) findViewById(R.id.Devpassword);
		
	    /*
	     * �ƶ��豸��¼
	     * �����豸��¼�����Ƿ���ڵ��ã�
	     * �����ڵ���NETDEV_LoginByDynamic
	     * ���ڵ���NETDEV_LoginCloudDev
	     * �ɹ�������ʵʱ����
	     * 
	     * Dev nameһ����дget Dev list �л�ȡ����User name
	     * */
		devloginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String DevName = Devname.getText().toString();
				String DevPassword = Devpassword.getText().toString();
				NETDEV_CLOUD_DEV_LOGIN_S oCloudDevInfo	= new NETDEV_CLOUD_DEV_LOGIN_S();
				NETDEV_DEVICE_INFO_S oDeviceInfo = new NETDEV_DEVICE_INFO_S();
				
				oCloudDevInfo.szDeviceName = DevName;
				oCloudDevInfo.szDevicePassword = DevPassword;
				if(oCloudDevInfo.szDevicePassword.isEmpty())
				{
					NetDEVSDK.glpUserID = NetDEVSDK.NETDEV_LoginByDynamic(NetDEVSDK.glpcloudID, oCloudDevInfo, oDeviceInfo);
				}
				else
				{
					NetDEVSDK.glpUserID = NetDEVSDK.NETDEV_LoginCloudDev(NetDEVSDK.glpcloudID,oCloudDevInfo,oDeviceInfo);
				}
				
				
				if(0 != NetDEVSDK.glpUserID)
				{
					startActivity(intent);
				}
							
			}
			
		});
		
		
		/*��ȡ�豸�б�
		 * ����ȡ�����豸�б������LOG CAT��info���пɹ���ѯ
		 * */
		getdevlistButton.setOnClickListener(new OnClickListener() {
			String tag = "FindCloudDevList";
			
			@Override
			public void onClick(View v) {
				int dwFileHandle = 0;
				String strMeg = null;
				String strOut = null;
				
				dwFileHandle = NetDEVSDK.NETDEV_FindCloudDevList(NetDEVSDK.glpcloudID);
				if(0 == dwFileHandle)
				{
					Log.i(tag,"Query failed."); 
				}
				else
				{
					NETDEV_CLOUD_DEV_INFO_S stclouddeviceinfo = new NETDEV_CLOUD_DEV_INFO_S();
					for(int i = 0;i < 10;i++)
					{
						int iRet = NetDEVSDK.NETDEV_FindNextCloudDevInfo(dwFileHandle,stclouddeviceinfo);
						if(0 == iRet)
						{
							break;
						}
						else
						{
							strMeg = "IP:" + stclouddeviceinfo.szIPAddr + "  ";
							strOut += strMeg;
							strMeg = "User Name:" + stclouddeviceinfo.szDevUserName + "  ";
							strOut += strMeg;
							strMeg = "Serial Num:" + stclouddeviceinfo.szDevSerialNum + "  ";
							strOut += strMeg;
							strMeg = "Dev Name:" + stclouddeviceinfo.szDevName + "  ";
							strOut += strMeg;
							strMeg = "Dev Model:" + stclouddeviceinfo.szDevModel + "  ";
							strOut += strMeg;
							strMeg = "Dev Port:" + String.valueOf(stclouddeviceinfo.dwDevPort) + "  \n";
							strOut += strMeg;
							
						}
						Log.i(tag,strOut); 
					}
				}
							
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.devlogin, menu);
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
