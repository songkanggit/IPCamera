package ipcamera.zealens.com.ipcamera;

/*            ʵʱ��Ƶ��������
 * 1.��ʼ����Ƶ��ʾ���ڣ�
 * 2.ʹ��NetDEVSDK.NETDEV_QueryVideoChlDetailList��ȡ��Ƶͨ���б��Ա�����NetDEVSDK.NETDEV_RealPlay��������ʵʱ���š�
 * 3.ΪNetDEVSDK.NETDEV_RealPlay���int lpUerID�Լ�NETDEV_PREVIEWINFO_S oPreviewInfo��ֵ��
 * 4.lpUerIDΪlocalDevloginActivity �� cloudDevloginActivity �л�ȡ���û���¼ID lpUserID��
 * 5.NETDEV_PREVIEWINFO_S oPreviewInfo��dwChannelID Ϊ NetDEVSDK.NETDEV_QueryVideoChlDetailList��ȡ��Ƶͨ���б���mChannel��
 * 6����oPreviewInfo.dwLinkMode �� oPreviewInfo.dwStreamIndex��������ֵ�󼴿ɵ���NetDEVSDK.NETDEV_RealPlay����ʵ�����ţ�
 * */


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sdk.NETDEV_AUDIO_SAMPLE_PARAM_S;
import com.sdk.NETDEV_FILECOND_S;
import com.sdk.NETDEV_FINDDATA_S;
import com.sdk.NETDEV_PLAYBACKCOND_S;
import com.sdk.NETDEV_PREVIEWINFO_S;
import com.sdk.NETDEV_VIDEO_CHL_DETAIL_INFO_S;
import com.sdk.NetDEVSDK;
import com.sdk.NetDEVSDK.OnNotifyListener;
import com.sdk.NETDEV_PLAYBACKCONTROL_S;
import com.sdk.NETDEV_AUDIO_SAMPLE_PARAM_S.NETDEV_AUDIO_SAMPLE_FORMAT_E;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.AudioTrack.OnPlaybackPositionUpdateListener;
import android.media.MediaRecorder;
/*ʵʱ���Ž���*/
public class LiveActivity extends Activity {

	public static int lpPlayID;     //��Ƶ��ID
	public PlayView mplayer;//GLsurface
	private int mVoiceComHandle;
	private AudioTrack mAudioPlayer;
	private AudioRecord mAudioRecorder;
	private boolean m_keep_running;
	protected byte []     m_in_bytes ;
	private int buf_size;
	private NetDEVSDK mNetDEVSDK = new NetDEVSDK();
	//public SurfaceView mViewLive;
	//public SurfaceHolder mViewHolder;
	private ArrayAdapter<String> ChannelAdapter;
	private int mChannel;

    public class inputTalkThread extends Thread {
    	@Override
        public void run() {
        	inputTalk();
        } 
    	}  
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//mplayer = new PlayView(getApplication());
		//setContentView(mplayer);
		setContentView(R.layout.activity_my);
		/*��ʼ����Ƶ��ʾ����*/
		
		mplayer = (PlayView)findViewById(R.id.myView1);
		//mplayer = new PlayView(getApplication());
		//mViewLive = (SurfaceView)findViewById(R.id.LiveView1);
		//mViewHolder = mViewLive.getHolder();
	    //mViewHolder.setFormat(PixelFormat.TRANSLUCENT);  
		lpPlayID = 0;

		List<NETDEV_VIDEO_CHL_DETAIL_INFO_S> list = new ArrayList<NETDEV_VIDEO_CHL_DETAIL_INFO_S>();
		list = NetDEVSDK.NETDEV_QueryVideoChlDetailList(NetDEVSDK.glpUserID, 64);	/*��Ƶͨ���б��ȡ*/
		Spinner channellist = (Spinner)findViewById(R.id.Channel);
		ChannelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,new ArrayList<String>());
		channellist.setAdapter(ChannelAdapter);
		for(int i = 0; i < list.size(); i++)
		{
			NETDEV_VIDEO_CHL_DETAIL_INFO_S tmp = list.get(i);
			System.out.println(i +" Status --" + tmp.bPtzSupported);
			ChannelAdapter.add("Channel - " +tmp.dwChannelID + ": Status -" + ((tmp.enStatus != 0) ? "Online" : "Offline" ));
		}
		
		/*��Ƶͨ������*/
		channellist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String item =arg0.getItemAtPosition(arg2).toString();
				String temp = "Channel - ";
				mChannel = Integer.parseInt(item.substring(temp.length(), item.indexOf(':')));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
				
			}
		});
		
		/*����ʵʩ����*/
		Button startButton = (Button) findViewById(R.id.startplay);
		startButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//NetDEVSDK.NETDEV_SetRenderSurface(mViewHolder.getSurface()); //��Ƶ������ʾ��������
				
		    	NETDEV_PREVIEWINFO_S oPreviewInfo = new NETDEV_PREVIEWINFO_S();
		    	oPreviewInfo.dwChannelID = mChannel;  // ��ֵ���ڽ����޸�
		    	oPreviewInfo.dwLinkMode = 1;   // 1,/* TCP */             2,/* UDP */
		    	oPreviewInfo.dwStreamIndex = 0;  // 0,/* ����  Main stream */       1,/* ����  Sub stream */       2,/* ������  Third stream */
		    	if(0 != lpPlayID)
		    	{
		    		NetDEVSDK.NETDEV_StopRealPlay(lpPlayID);
		    	}
		    	mplayer.isCanDrawFrame = true;
				lpPlayID = NetDEVSDK.NETDEV_RealPlay(NetDEVSDK.glpUserID, oPreviewInfo);
				 
			}
			
		});
		
		Button stopButton = (Button)findViewById(R.id.stopplay);
		
		/*�ر�ʵʱ����*/
		stopButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mplayer.isCanDrawFrame = false;
				int iRet = NetDEVSDK.NETDEV_StopRealPlay(lpPlayID);
				lpPlayID = 0;
				System.out.println("----------------------" + iRet);
			}
		});
		
		/* ��ʱ�����¼���ļ� */
		Button findButton = (Button)findViewById(R.id.find);
		findButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				NETDEV_FILECOND_S oFileCond = new NETDEV_FILECOND_S();
				oFileCond.dwChannelID = mChannel;
				oFileCond.tBeginTime = (System.currentTimeMillis()/1000)-(24*3600);//��ѯ��ȥ24Сʱ��¼��
				oFileCond.tEndTime = System.currentTimeMillis()/1000;
				int dwFileHandle = NetDEVSDK.NETDEV_FindFile(NetDEVSDK.glpUserID, oFileCond);
				if(dwFileHandle == 0)
				{
					System.out.println("find file failed");	
				}
				else
				{
					System.out.println("find file success" );
				
				NETDEV_FINDDATA_S stVodFile = new NETDEV_FINDDATA_S();
				for(int i=0;i<10;i++)
				{
					int iRet = NetDEVSDK.NETDEV_FindNextFile(dwFileHandle, stVodFile);
					if(0 == iRet)
					{
						System.out.println("find nextfile failed");
						break;
					}
					else
					{
						NetDEVSDK.m_astVodFile[i] = new NETDEV_FINDDATA_S();
		                NetDEVSDK.m_astVodFile[i].szFileName = stVodFile.szFileName;
		                NetDEVSDK.m_astVodFile[i].tBeginTime = stVodFile.tBeginTime;
		                NetDEVSDK.m_astVodFile[i].tEndTime = stVodFile.tEndTime;
		                NetDEVSDK.m_astVodFile[i].byFileType = stVodFile.byFileType;
		                String strBeginLocalTime, strEndLocalTime;
		                Date nowTime = new Date(stVodFile.tBeginTime*1000);
		                SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		                strBeginLocalTime = sdFormatter.format(nowTime);
		                Date nowTime1 = new Date(stVodFile.tEndTime*1000);
		                SimpleDateFormat sdFormatter1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		                strEndLocalTime = sdFormatter1.format(nowTime1);
		                System.out.println(strBeginLocalTime +"---"+strEndLocalTime); 
		                
					}	
				}
				}
				
				int iRet =NetDEVSDK.NETDEV_FindClose(dwFileHandle);
				if(iRet == 1)
				{
						System.out.println("find Close success");	
				}
				else
				{
						System.out.println("find Close failed");
				}		
			}
		});
		
		
		/*��ʱ�䲥��¼�� */
		Button playbackButton = (Button)findViewById(R.id.palyback);
		playbackButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//NetDEVSDK.NETDEV_SetRenderSurface(mViewHolder.getSurface()); //��Ƶ������ʾ��������
		  
			    NETDEV_PLAYBACKCOND_S stPlayBackByTimeInfo = new NETDEV_PLAYBACKCOND_S();

			    /* Assume to play the first recording found */
			    stPlayBackByTimeInfo.tBeginTime = NetDEVSDK.m_astVodFile[0].tBeginTime;
			    stPlayBackByTimeInfo.tEndTime = NetDEVSDK.m_astVodFile[0].tEndTime;
			    stPlayBackByTimeInfo.dwChannelID = mChannel;
			    stPlayBackByTimeInfo.dwLinkMode = 1;//NETDEV_TRANSPROTOCAL_RTPTCP
			    mplayer.isCanDrawFrame = true;
			    lpPlayID = NetDEVSDK.NETDEV_PlayBackByTime(NetDEVSDK.glpUserID, stPlayBackByTimeInfo);
			    if(0 == lpPlayID)
			    {
			    	System.out.println("Play the video failed.");
			    }
			}
		});
		
		/*ֹͣ����¼��*/
		Button stopplaybackButton = (Button)findViewById(R.id.stopplayback);
		stopplaybackButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				mplayer.isCanDrawFrame = false;
			    int iRet = NetDEVSDK.NETDEV_StopPlayBack(lpPlayID);
			    if (0 != iRet)
			    {
			    	System.out.println("Stop play failed.");
			    }

			    lpPlayID = 0;
			}
		});
		
		
		/*��ͣ����¼��*/
		Button pauseplaybackButton = (Button)findViewById(R.id.pause);
		pauseplaybackButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
			   if((lpPlayID != 0 )&&(NetDEVSDK.glpUserID != 0))
			   {
				   NETDEV_PLAYBACKCONTROL_S PlayBackControlenSpeed = new NETDEV_PLAYBACKCONTROL_S();
				   int iRet = NetDEVSDK.NETDEV_PlayBackControl(lpPlayID,NetDEVSDK.NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_PAUSE, PlayBackControlenSpeed);
				   
				   if(1 != iRet)
				   {
					   System.out.println("pause play failed.");
				   }
			   }
			}
		});
		
		
		/*����������ͣ¼��*/
		Button resumeplaybackButton = (Button)findViewById(R.id.resume);
		resumeplaybackButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
			   if((lpPlayID != 0 )&&(NetDEVSDK.glpUserID != 0))
			   {
				   NETDEV_PLAYBACKCONTROL_S PlayBackControlenSpeed = new NETDEV_PLAYBACKCONTROL_S();
				   int iRet = NetDEVSDK.NETDEV_PlayBackControl(lpPlayID,NetDEVSDK.NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_RESUME, PlayBackControlenSpeed);
				   
				   if(1 != iRet)
				   {
					   System.out.println("pause play failed.");
				   }
			   }
			}
		});
		
		
		/*���*/
		Button forwordplaybackButton = (Button)findViewById(R.id.forword);
		forwordplaybackButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				NETDEV_PLAYBACKCONTROL_S PlayBackControlenSpeed = new NETDEV_PLAYBACKCONTROL_S();
				PlayBackControlenSpeed.enSpeed = 0;
			   int iRet = NetDEVSDK.NETDEV_PlayBackControl(lpPlayID,NetDEVSDK.NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_GETPLAYSPEED, PlayBackControlenSpeed);
			   
			   if(0 == iRet)
			   {
				   System.out.println("Get play speed failed.");
			   }
			   PlayBackControlenSpeed.enSpeed = PlayBackControlenSpeed.enSpeed + 1 <= NetDEVSDK.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_16_FORWARD ? (PlayBackControlenSpeed.enSpeed + 1) : PlayBackControlenSpeed.enSpeed;
			   
			   while((PlayBackControlenSpeed.enSpeed <= NetDEVSDK.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_HALF_FORWARD) && (PlayBackControlenSpeed.enSpeed >=NetDEVSDK.NETDEV_VOD_PLAY_STATUS_E. NETDEV_PLAY_STATUS_HALF_BACKWARD))
			    {
				   PlayBackControlenSpeed.enSpeed++;
			    }
			   
			   iRet = NetDEVSDK.NETDEV_PlayBackControl(lpPlayID,NetDEVSDK.NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_SETPLAYSPEED, PlayBackControlenSpeed);
			   if(0 == iRet)
			   {
				   System.out.println("Set play speed failed.");
			   }
			}
		});
		
		
		/*����*/
		Button backwordplaybackButton = (Button)findViewById(R.id.backword);
		backwordplaybackButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
			NETDEV_PLAYBACKCONTROL_S PlayBackControlenSpeed = new NETDEV_PLAYBACKCONTROL_S();
			PlayBackControlenSpeed.enSpeed = 0;
			   int iRet = NetDEVSDK.NETDEV_PlayBackControl(lpPlayID,NetDEVSDK.NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_GETPLAYSPEED, PlayBackControlenSpeed);
			   
			   if(0 == iRet)
			   {
				   System.out.println("Get play speed failed.");
			   }
			   PlayBackControlenSpeed.enSpeed = PlayBackControlenSpeed.enSpeed - 1 >= NetDEVSDK.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_16_BACKWARD ? (PlayBackControlenSpeed.enSpeed - 1) : PlayBackControlenSpeed.enSpeed;
			   
			   while((PlayBackControlenSpeed.enSpeed <= NetDEVSDK.NETDEV_VOD_PLAY_STATUS_E.NETDEV_PLAY_STATUS_HALF_FORWARD) && (PlayBackControlenSpeed.enSpeed >=NetDEVSDK.NETDEV_VOD_PLAY_STATUS_E. NETDEV_PLAY_STATUS_HALF_BACKWARD))
			    {
				   PlayBackControlenSpeed.enSpeed--;
			    }
			   
			   iRet = NetDEVSDK.NETDEV_PlayBackControl(lpPlayID,NetDEVSDK.NETDEV_VOD_PLAY_CTRL_E.NETDEV_PLAY_CTRL_SETPLAYSPEED, PlayBackControlenSpeed);
			   if(0 == iRet)
			   {
				   System.out.println("Set play speed failed.");
			   }
			}
		});
		
		
		/*�����Խ���*/
		Button TalkonButton = (Button)findViewById(R.id.Button01);
		TalkonButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {

				//���������Խ�����
				mVoiceComHandle = mNetDEVSDK.NETDEV_StartInputVoiceSrv(NetDEVSDK.glpUserID, mChannel);
				if(0 == mVoiceComHandle)
				{
					 System.out.println("StartInputVoiceSrv failed.");
				} 
	
				//��������
				{
					outputTalk();
				}
				    
			    //��������
			    {
			        m_keep_running = true;
			        inputTalkThread inputTalkThread = new inputTalkThread();  
			        inputTalkThread.start();  
 
			    } 

			}

		});
		

		
		//�ر������Խ�
		Button TalkoffButton = (Button)findViewById(R.id.Button02);
		TalkoffButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) {
				m_keep_running = false;
				int iRet = NetDEVSDK.NETDEV_StopInputVoiceSrv(mVoiceComHandle);
			   if(0 == iRet)
			   {
				   System.out.println("StopInputVoiceSrv failed.");
			   }
			}
		});
		
	}
	
	//��������
	public void outputTalk()
	{
		
	    mNetDEVSDK.setNotifyListener(new OnNotifyListener() {
		   @Override
            public void nativeNotifyDecodeAudioData(byte[] voiceData, int u32WaveFormat, int length, int type) 
            {	

        		try {
	        			if (mAudioPlayer == null)
	        			{
	        				mAudioPlayer = new AudioTrack(AudioManager.STREAM_MUSIC, 8000,
	        						AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
	        						length, AudioTrack.MODE_STREAM);
	        			}
	        			mAudioPlayer.write(voiceData, 0, length);
	        			mAudioPlayer.play();

        			} catch (Exception e) 
        			{
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        			} 
            } 

		});
	}
	
	//��������
	public void inputTalk()
	{
        buf_size = AudioRecord.getMinBufferSize(8000,AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT);
		  
       mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC,8000,AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT,
    buf_size) ;
       m_in_bytes = new byte [buf_size]; 
      try
      {
    	  NETDEV_AUDIO_SAMPLE_PARAM_S oVoiceParam = new NETDEV_AUDIO_SAMPLE_PARAM_S();
    	  oVoiceParam.dwChannels = mChannel;
    	  oVoiceParam.dwSampleRate = 8000;
    	  oVoiceParam.enSampleFormat = NETDEV_AUDIO_SAMPLE_FORMAT_E.NETDEV_AUDIO_SAMPLE_FMT_S16;
    	  
          mAudioRecorder.startRecording() ;
             while(m_keep_running)
             {
            	 mAudioRecorder.read(m_in_bytes, 0, buf_size) ;
                 NetDEVSDK.NETDEV_InputVoiceData(mVoiceComHandle,m_in_bytes,buf_size,oVoiceParam);
             }
     
             mAudioRecorder.stop() ;
             mAudioRecorder = null ;
             m_in_bytes = null ;

        
      }
      catch(Exception e)
      {
       e.printStackTrace();
      }
		
	}
	

	/*�رս���ʱ�ر�ʵʱ����*/
	@Override
	protected void onDestroy() {
		super.onDestroy();
		int iRet = NetDEVSDK.NETDEV_StopRealPlay(lpPlayID);
		lpPlayID = 0;
		System.out.println("----------------------" + iRet);
	}
}
