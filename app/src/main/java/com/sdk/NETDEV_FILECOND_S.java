package com.sdk;

public class NETDEV_FILECOND_S {
	public String    szFileName;                 /* ¼���ļ���  Recording file name */
	public int   dwChannelID;                    /* ͨ����  Channel number */
	public int   dwFileType;                     /* ¼��洢����,�μ�ö��#NETDEV_PLAN_STORE_TYPE_E  Recording storage type, see enumeration #NETDEV_PLAN_STORE_TYPE_E */
	public long   tBeginTime;                     /* ��ʼʱ��  Start time */
	public long   tEndTime;                       /* ����ʱ��  End time */
}
