package com.sdk;

import java.util.ArrayList;

import android.view.Surface;

public class NetDEVSDK {
	static {
        System.loadLibrary("Curl");
        System.loadLibrary("MP4");
        System.loadLibrary("mXML");
        System.loadLibrary("RM_Module");
        System.loadLibrary("NDRender");
        System.loadLibrary("dspvideomjpeg");
        System.loadLibrary("NDPlayer");
        System.loadLibrary("Discovery");
        System.loadLibrary("NetDEVSDK");
        System.loadLibrary("NetDEVSDK_JNI");
    }
    private OnNotifyListener mNotifyListener;
 	public static int glpUserID;       /*�û���¼ID*/
 	public static int glpcloudID;      /*���˻���¼ID*/
 	public static NETDEV_FINDDATA_S []m_astVodFile = new NETDEV_FINDDATA_S[10];
 	
 	/**
 	 * @enum tagNETDEVPlayControl
 	 * @brief �طſ������� ö�ٶ��� Playback control commands Enumeration definition
 	 * @attention �� None
 	 */
 	public class NETDEV_VOD_PLAY_CTRL_E{
 		public static final int NETDEV_PLAY_CTRL_PLAY           = 0;           /* ��ʼ����  Play */
 		public static final int NETDEV_PLAY_CTRL_PAUSE          = 1;           /* ��ͣ����  Pause */
 		public static final int NETDEV_PLAY_CTRL_RESUME         = 2;          /* �ָ�����  Resume */
 		public static final int NETDEV_PLAY_CTRL_GETPLAYTIME    = 3;           /* ��ȡ���Ž���  Obtain playing time */
 		public static final int NETDEV_PLAY_CTRL_SETPLAYTIME    = 4;           /* ���ò��Ž���  Configure playing time */
 		public static final int NETDEV_PLAY_CTRL_GETPLAYSPEED   = 5;          /* ��ȡ�����ٶ�  Obtain playing speed */
 		public static final int NETDEV_PLAY_CTRL_SETPLAYSPEED   = 6;        /* ���ò����ٶ�  Configure playing speed */
 	}
 	
 	/**
 	 * @enum tagNETDEVVodPlayStatus
 	 * @brief �طš�����״̬ ö�ٶ��� Playback and download status Enumeration definition
 	 * @attention �� None
 	 */
 	public class NETDEV_VOD_PLAY_STATUS_E
 	{
 	/** ����״̬  Play status */
 		public static final int NETDEV_PLAY_STATUS_16_BACKWARD        = 0;        /* 16���ٺ��˲���  Backward at 16x speed */
 		public static final int NETDEV_PLAY_STATUS_8_BACKWARD         = 1;       /* 8���ٺ��˲���  Backward at 8x speed */
 		public static final int NETDEV_PLAY_STATUS_4_BACKWARD         = 2;       /* 4���ٺ��˲���  Backward at 4x speed */
 	    public static final int NETDEV_PLAY_STATUS_2_BACKWARD         = 3;        /* 2���ٺ��˲���  Backward at 2x speed */
 		public static final int NETDEV_PLAY_STATUS_1_BACKWARD         = 4;        /* �����ٶȺ��˲���  Backward at normal speed */
 	    public static final int NETDEV_PLAY_STATUS_HALF_BACKWARD      = 5;        /* 1/2���ٺ��˲���  Backward at 1/2 speed */
 	    public static final int NETDEV_PLAY_STATUS_QUARTER_BACKWARD   = 6;        /* 1/4���ٺ��˲���  Backward at 1/4 speed */
 	    public static final int NETDEV_PLAY_STATUS_QUARTER_FORWARD    = 7;       /* 1/4���ٲ���  Play at 1/4 speed */
 	    public static final int NETDEV_PLAY_STATUS_HALF_FORWARD       = 8;        /* 1/2���ٲ���  Play at 1/2 speed */
 	    public static final int NETDEV_PLAY_STATUS_1_FORWARD          = 9;        /* �����ٶ�ǰ������  Forward at normal speed */
 	    public static final int NETDEV_PLAY_STATUS_2_FORWARD          = 10;       /* 2����ǰ������  Forward at 2x speed */
 	    public static final int NETDEV_PLAY_STATUS_4_FORWARD          = 11;       /* 4����ǰ������  Forward at 4x speed */
 	    public static final int NETDEV_PLAY_STATUS_8_FORWARD          = 12;       /* 8����ǰ������  Forward at 8x speed */
 	    public static final int NETDEV_PLAY_STATUS_16_FORWARD         = 13;      /* 16����ǰ������  Forward at 16x speed */
        public static final int NETDEV_PLAY_STATUS_INVALID            = 14;
 	}
 	
 	
 	/**
 	* SDK ��ʼ��  SDK initialization
 	* @return 1��ʾ�ɹ�,������ʾʧ�� 1 means success, and any other value means failure.
 	* @note �̲߳���ȫ Thread not safe
 	*/
 	public static native int NETDEV_Init();
 	
 	/**
 	* SDK ע��  SDK uninitialization
 	* @return 1��ʾ�ɹ�,������ʾʧ�� 1 means success, and any other value means failure.
 	* @note �̲߳���ȫ Thread not safe
 	*/
 	public static native int NETDEV_UInit();
 	
 	/**
 	* �û���¼  User login
 	* @param [IN]  DevIP         �豸IP Device IP
 	* @param [IN]  DevPort         �豸�������˿� Device server port
 	* @param [IN]  UserName      �û��� Username
 	* @param [IN]  Password      ���� Password
 	* @param [OUT] oDeviceInfo       �豸��Ϣ device information 
 	* @return ���ص��û���¼ID,���� 0 ��ʾʧ��,����ֵ��ʾ���ص��û�IDֵ�� Returned user login ID. 0 indicates failure, and other values indicate the user ID.
 	* @note
 	*/
	public static native int NETDEV_Login(String DevIP, int DevPort, String Username, String Password,NETDEV_DEVICE_INFO_S oDeviceInfo);
	
	/**
	* �û�ע��  User logout
	* @param [IN] lpUserID    �û���¼ID User login ID
	* @return 1 ��ʾ�ɹ�,������ʾʧ�� 1 means success, and any other value means failure.
	* @note
	*/
    public static native int NETDEV_Logout(int lpUerID);
    
    /**
    * ��ѯͨ��������  Query channel capabilities
    * @param [IN]    lpUserID           �û���¼ID User login ID
    * @param [INOUT] pdwChlCount        ͨ���� Number of channels
    * @return ArrayList    ͨ���������б� List of channel capabilities.
    * @note
    */
    public static native ArrayList<NETDEV_VIDEO_CHL_DETAIL_INFO_S> NETDEV_QueryVideoChlDetailList(int lpUserID, int dwChlCount);
    
    /**
    * ��ȡ������  Get error codes
    * @return ������ Error codes
    */
    public static native int NETDEV_GetLastError();
    
    /**
    * ������Ƶͼ����ʾ����  Modify image display ratio
    */
    public static native void NETDEV_SetRenderSurface(Surface view);
    
    /**
    * ����ʵʱԤ��  Start live preview
    * @param [IN]  lpUserID             �û���¼ID User login ID
    * @param [IN]  oPreviewInfo       Ԥ������  see enumeration
    * @return ���ص��û���¼ID,���� 0 ��ʾʧ��,����ֵ��ʾ���ص��û�IDֵ�� Returned user login ID. 0 indicates failure, and other values indicate the user ID.
    * @note
    */
    public  static native int NETDEV_RealPlay(int lpUerID,NETDEV_PREVIEWINFO_S oPreviewInfo);
    
    /**
    * ֹͣʵʱԤ��  Stop live preview
    * @param [IN]  lpPlayID     Ԥ��ID Preview ID
    * @return 1��ʾ�ɹ�,������ʾʧ�� 1 means success, and any other value means failure.
    * @note ��Ӧ�ر�NETDEV_RealPlay������ʵ�� Stop the live view started by NETDEV_RealPlay
    */
    public  static native int NETDEV_StopRealPlay(int lpPlayID);
    
    /**
    * �û���¼�ƶ��˻� User login to cloud account
    * @param [IN]  CloudUrl       �ƶ˷�����URL  Cloud server URL 
    * @param [IN]  CloudUser          �ƶ��˻��� Cloud account name
    * @param [IN]  Cloudpassword          �ƶ��˻�����  Cloud account password 
    * @return ���ص��û���½ID,0��ʾʧ�ܣ�����ֵ��ʾ���ص��û�IDֵ  Returned user ID. 0 means failure, any other value is a user ID.
    * @note 
    */
    public static native int NETDEV_LoginCloud(String CloudUrl,String CloudUser,String Cloudpassword);
    
    /**
    * �ƶ��豸��̬�����¼   Cloud device login with dynamic password
    * @param [IN]  lpUserID             �ƶ��˻���¼ID Cloud account login ID
    * @param [IN]  CloudDevInfo           �ƶ��豸��¼��Ϣ  Cloud device login info
    * @param [OUT] cloudDeviceInfo           �豸��Ϣ device info
    * @return ���ص��û���½ID,0��ʾʧ�ܣ�����ֵ��ʾ���ص��û�IDֵ  Returned user ID. 0 means failure, any other value is a user ID.
    * @note 
        1��pCloudInfo ��szDevicePassword�ֶβ�����д��The szDevicePassword field in pCloudInfo needs not to be filled in.
    */
    public static native int NETDEV_LoginByDynamic(int lpCloudID,NETDEV_CLOUD_DEV_LOGIN_S CloudDevInfo,NETDEV_DEVICE_INFO_S cloudDeviceInfo);
    
    /**
    * �ƶ��豸��¼ Cloud device login
    * @param [IN]  lpUserID             �ƶ��˻���¼ID    Cloud account login ID 
    * @param [IN]  CloudDevInfo           �ƶ��豸��¼��Ϣ  Cloud device login info 
    * @param [OUT] cloudDeviceInfo           �豸��Ϣ  device info 
    * @return ���ص��û���½ID,0��ʾʧ�ܣ�����ֵ��ʾ���ص��û�IDֵ  Returned user ID. 0 means failure, any other value is a user ID.
    * @note 
        1��pCloudInfo ��szDevicePassword�ֶ�����д��The szDevicePassword field in pCloudInfo must be filled in.
    */
    public static native int NETDEV_LoginCloudDev(int lpCloudID,NETDEV_CLOUD_DEV_LOGIN_S CloudDevInfo,NETDEV_DEVICE_INFO_S cloudDeviceInfo);
    
    /**
    * ��ѯ�ƶ��˻��豸�б�   Query device list under a cloud account
    * @param [IN]  lpUserID            �û���¼ID User login ID
    * @return ��ѯҵ���,����0��ʾʧ�ܣ�����ֵ��ΪNETDEV_FindNextCloudDevInfo��NETDEV_FindCloseDevList�Ⱥ����Ĳ�����
    Service ID. 0 means failure, any other value will be used as parameter of functions including NETDEV_FindNextCloudDevInfo and NETDEV_FindCloseDevList.
    * @note  
    */
    public static native int NETDEV_FindCloudDevList(int lpCloudID);
    
    /**
    * �����ȡ���ҵ����豸��Ϣ   Obtain info about detected devices one by one
    * @param [IN]  lpFindHandle         ���Ҿ��   Search handle
    * @param [OUT] pstDevInfo           �����豸��Ϣ��ָ��   Pointer to saved device info
    * @return 1��ʾ�ɹ���������ʾʧ�� 1 means success, and any other value means failure.
    * @note ����ʧ��˵����ѯ���� A returned failure indicates the end of search.
    */
    public static native int NETDEV_FindNextCloudDevInfo(int lpFindID,NETDEV_CLOUD_DEV_INFO_S clouddeviceinfo);
    
    
    public static native int NETDEV_FindFile(int lpUserID,NETDEV_FILECOND_S pstFindCond);
    public static native int NETDEV_FindNextFile(int lpFindHandle,NETDEV_FINDDATA_S pstFindData);
    public static native int NETDEV_FindClose(int lpFindHandle);
    public static native int NETDEV_PlayBackByTime(int lpUserID,NETDEV_PLAYBACKCOND_S pstPlayBackInfo);
    public static native int NETDEV_StopPlayBack(int lpPlayHandle);
    public static native int NETDEV_PlayBackControl(int lpPlayHandle,int dwControlCode,NETDEV_PLAYBACKCONTROL_S lpBuffer);
    
    public static native int initialize();
    public static native int rendererRender();
    public static native int setRendererViewport(int w, int h);
    public static native int initializeRenderer();
    
    public native int NETDEV_StartInputVoiceSrv(int lpUserID,int dwChannelID);
    public static native int NETDEV_StopInputVoiceSrv(int lpVoiceComHandle);
    public static native int NETDEV_InputVoiceData(int lpVoiceComHandle,byte[] lpDataBuf,int dwDataLen,NETDEV_AUDIO_SAMPLE_PARAM_S pstVoiceParam);
   
    public interface OnNotifyListener { 
    	public void nativeNotifyDecodeAudioData(byte[] voiceData, int u32WaveFormat, int length, int type);
    }
    public void setNotifyListener(OnNotifyListener notifyListener) {
        mNotifyListener = notifyListener;
    }

    public void nativeNotifyDecodeAudioData(byte[] voiceData,int u32WaveFormat, int length, int type) {
    		if (mNotifyListener != null) {
    				mNotifyListener.nativeNotifyDecodeAudioData(voiceData, u32WaveFormat, length, type);
    		}
    	}
}

