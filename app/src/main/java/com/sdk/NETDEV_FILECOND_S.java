package com.sdk;

public class NETDEV_FILECOND_S {
	public String    szFileName;                 /* 录像文件名  Recording file name */
	public int   dwChannelID;                    /* 通道号  Channel number */
	public int   dwFileType;                     /* 录像存储类型,参见枚举#NETDEV_PLAN_STORE_TYPE_E  Recording storage type, see enumeration #NETDEV_PLAN_STORE_TYPE_E */
	public long   tBeginTime;                     /* 起始时间  Start time */
	public long   tEndTime;                       /* 结束时间  End time */
}
