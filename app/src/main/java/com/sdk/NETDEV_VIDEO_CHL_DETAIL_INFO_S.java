package com.sdk;

    /*视频通道详细信息*/
public class NETDEV_VIDEO_CHL_DETAIL_INFO_S {
	public int dwChannelID;                      /* 通道ID  Channel ID */      
	public int bPtzSupported;                    /* 是否支持云台 Whether ptz is supported */
	public int enStatus;                         /* 通道状态  Channel status */
	public int dwStreamNum;                      /* 流个数  Number of streams */
}
