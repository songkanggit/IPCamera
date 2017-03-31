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
 	public static int glpUserID;       /*用户登录ID*/
 	public static int glpcloudID;      /*云账户登录ID*/
 	public static NETDEV_FINDDATA_S []m_astVodFile = new NETDEV_FINDDATA_S[10];
 	
 	/**
 	 * @enum tagNETDEVPlayControl
 	 * @brief 回放控制命令 枚举定义 Playback control commands Enumeration definition
 	 * @attention 无 None
 	 */
 	public class NETDEV_VOD_PLAY_CTRL_E{
 		public static final int NETDEV_PLAY_CTRL_PLAY           = 0;           /* 开始播放  Play */
 		public static final int NETDEV_PLAY_CTRL_PAUSE          = 1;           /* 暂停播放  Pause */
 		public static final int NETDEV_PLAY_CTRL_RESUME         = 2;          /* 恢复播放  Resume */
 		public static final int NETDEV_PLAY_CTRL_GETPLAYTIME    = 3;           /* 获取播放进度  Obtain playing time */
 		public static final int NETDEV_PLAY_CTRL_SETPLAYTIME    = 4;           /* 设置播放进度  Configure playing time */
 		public static final int NETDEV_PLAY_CTRL_GETPLAYSPEED   = 5;          /* 获取播放速度  Obtain playing speed */
 		public static final int NETDEV_PLAY_CTRL_SETPLAYSPEED   = 6;        /* 设置播放速度  Configure playing speed */
 	}
 	
 	/**
 	 * @enum tagNETDEVVodPlayStatus
 	 * @brief 回放、下载状态 枚举定义 Playback and download status Enumeration definition
 	 * @attention 无 None
 	 */
 	public class NETDEV_VOD_PLAY_STATUS_E
 	{
 	/** 播放状态  Play status */
 		public static final int NETDEV_PLAY_STATUS_16_BACKWARD        = 0;        /* 16倍速后退播放  Backward at 16x speed */
 		public static final int NETDEV_PLAY_STATUS_8_BACKWARD         = 1;       /* 8倍速后退播放  Backward at 8x speed */
 		public static final int NETDEV_PLAY_STATUS_4_BACKWARD         = 2;       /* 4倍速后退播放  Backward at 4x speed */
 	    public static final int NETDEV_PLAY_STATUS_2_BACKWARD         = 3;        /* 2倍速后退播放  Backward at 2x speed */
 		public static final int NETDEV_PLAY_STATUS_1_BACKWARD         = 4;        /* 正常速度后退播放  Backward at normal speed */
 	    public static final int NETDEV_PLAY_STATUS_HALF_BACKWARD      = 5;        /* 1/2倍速后退播放  Backward at 1/2 speed */
 	    public static final int NETDEV_PLAY_STATUS_QUARTER_BACKWARD   = 6;        /* 1/4倍速后退播放  Backward at 1/4 speed */
 	    public static final int NETDEV_PLAY_STATUS_QUARTER_FORWARD    = 7;       /* 1/4倍速播放  Play at 1/4 speed */
 	    public static final int NETDEV_PLAY_STATUS_HALF_FORWARD       = 8;        /* 1/2倍速播放  Play at 1/2 speed */
 	    public static final int NETDEV_PLAY_STATUS_1_FORWARD          = 9;        /* 正常速度前进播放  Forward at normal speed */
 	    public static final int NETDEV_PLAY_STATUS_2_FORWARD          = 10;       /* 2倍速前进播放  Forward at 2x speed */
 	    public static final int NETDEV_PLAY_STATUS_4_FORWARD          = 11;       /* 4倍速前进播放  Forward at 4x speed */
 	    public static final int NETDEV_PLAY_STATUS_8_FORWARD          = 12;       /* 8倍速前进播放  Forward at 8x speed */
 	    public static final int NETDEV_PLAY_STATUS_16_FORWARD         = 13;      /* 16倍速前进播放  Forward at 16x speed */
        public static final int NETDEV_PLAY_STATUS_INVALID            = 14;
 	}
 	
 	
 	/**
 	* SDK 初始化  SDK initialization
 	* @return 1表示成功,其他表示失败 1 means success, and any other value means failure.
 	* @note 线程不安全 Thread not safe
 	*/
 	public static native int NETDEV_Init();
 	
 	/**
 	* SDK 注销  SDK uninitialization
 	* @return 1表示成功,其他表示失败 1 means success, and any other value means failure.
 	* @note 线程不安全 Thread not safe
 	*/
 	public static native int NETDEV_UInit();
 	
 	/**
 	* 用户登录  User login
 	* @param [IN]  DevIP         设备IP Device IP
 	* @param [IN]  DevPort         设备服务器端口 Device server port
 	* @param [IN]  UserName      用户名 Username
 	* @param [IN]  Password      密码 Password
 	* @param [OUT] oDeviceInfo       设备信息 device information 
 	* @return 返回的用户登录ID,返回 0 表示失败,其他值表示返回的用户ID值。 Returned user login ID. 0 indicates failure, and other values indicate the user ID.
 	* @note
 	*/
	public static native int NETDEV_Login(String DevIP, int DevPort, String Username, String Password,NETDEV_DEVICE_INFO_S oDeviceInfo);
	
	/**
	* 用户注销  User logout
	* @param [IN] lpUserID    用户登录ID User login ID
	* @return 1 表示成功,其他表示失败 1 means success, and any other value means failure.
	* @note
	*/
    public static native int NETDEV_Logout(int lpUerID);
    
    /**
    * 查询通道能力集  Query channel capabilities
    * @param [IN]    lpUserID           用户登录ID User login ID
    * @param [INOUT] pdwChlCount        通道数 Number of channels
    * @return ArrayList    通道能力集列表 List of channel capabilities.
    * @note
    */
    public static native ArrayList<NETDEV_VIDEO_CHL_DETAIL_INFO_S> NETDEV_QueryVideoChlDetailList(int lpUserID, int dwChlCount);
    
    /**
    * 获取错误码  Get error codes
    * @return 错误码 Error codes
    */
    public static native int NETDEV_GetLastError();
    
    /**
    * 设置视频图像显示比例  Modify image display ratio
    */
    public static native void NETDEV_SetRenderSurface(Surface view);
    
    /**
    * 启动实时预览  Start live preview
    * @param [IN]  lpUserID             用户登录ID User login ID
    * @param [IN]  oPreviewInfo       预览参数  see enumeration
    * @return 返回的用户登录ID,返回 0 表示失败,其他值表示返回的用户ID值。 Returned user login ID. 0 indicates failure, and other values indicate the user ID.
    * @note
    */
    public  static native int NETDEV_RealPlay(int lpUerID,NETDEV_PREVIEWINFO_S oPreviewInfo);
    
    /**
    * 停止实时预览  Stop live preview
    * @param [IN]  lpPlayID     预览ID Preview ID
    * @return 1表示成功,其他表示失败 1 means success, and any other value means failure.
    * @note 对应关闭NETDEV_RealPlay开启的实况 Stop the live view started by NETDEV_RealPlay
    */
    public  static native int NETDEV_StopRealPlay(int lpPlayID);
    
    /**
    * 用户登录云端账户 User login to cloud account
    * @param [IN]  CloudUrl       云端服务器URL  Cloud server URL 
    * @param [IN]  CloudUser          云端账户名 Cloud account name
    * @param [IN]  Cloudpassword          云端账户密码  Cloud account password 
    * @return 返回的用户登陆ID,0表示失败，其他值表示返回的用户ID值  Returned user ID. 0 means failure, any other value is a user ID.
    * @note 
    */
    public static native int NETDEV_LoginCloud(String CloudUrl,String CloudUser,String Cloudpassword);
    
    /**
    * 云端设备动态密码登录   Cloud device login with dynamic password
    * @param [IN]  lpUserID             云端账户登录ID Cloud account login ID
    * @param [IN]  CloudDevInfo           云端设备登录信息  Cloud device login info
    * @param [OUT] cloudDeviceInfo           设备信息 device info
    * @return 返回的用户登陆ID,0表示失败，其他值表示返回的用户ID值  Returned user ID. 0 means failure, any other value is a user ID.
    * @note 
        1、pCloudInfo 中szDevicePassword字段不需填写。The szDevicePassword field in pCloudInfo needs not to be filled in.
    */
    public static native int NETDEV_LoginByDynamic(int lpCloudID,NETDEV_CLOUD_DEV_LOGIN_S CloudDevInfo,NETDEV_DEVICE_INFO_S cloudDeviceInfo);
    
    /**
    * 云端设备登录 Cloud device login
    * @param [IN]  lpUserID             云端账户登录ID    Cloud account login ID 
    * @param [IN]  CloudDevInfo           云端设备登录信息  Cloud device login info 
    * @param [OUT] cloudDeviceInfo           设备信息  device info 
    * @return 返回的用户登陆ID,0表示失败，其他值表示返回的用户ID值  Returned user ID. 0 means failure, any other value is a user ID.
    * @note 
        1、pCloudInfo 中szDevicePassword字段需填写。The szDevicePassword field in pCloudInfo must be filled in.
    */
    public static native int NETDEV_LoginCloudDev(int lpCloudID,NETDEV_CLOUD_DEV_LOGIN_S CloudDevInfo,NETDEV_DEVICE_INFO_S cloudDeviceInfo);
    
    /**
    * 查询云端账户设备列表   Query device list under a cloud account
    * @param [IN]  lpUserID            用户登录ID User login ID
    * @return 查询业务号,返回0表示失败，其他值作为NETDEV_FindNextCloudDevInfo、NETDEV_FindCloseDevList等函数的参数。
    Service ID. 0 means failure, any other value will be used as parameter of functions including NETDEV_FindNextCloudDevInfo and NETDEV_FindCloseDevList.
    * @note  
    */
    public static native int NETDEV_FindCloudDevList(int lpCloudID);
    
    /**
    * 逐个获取查找到的设备信息   Obtain info about detected devices one by one
    * @param [IN]  lpFindHandle         查找句柄   Search handle
    * @param [OUT] pstDevInfo           保存设备信息的指针   Pointer to saved device info
    * @return 1表示成功，其他表示失败 1 means success, and any other value means failure.
    * @note 返回失败说明查询结束 A returned failure indicates the end of search.
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

