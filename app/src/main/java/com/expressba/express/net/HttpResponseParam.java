package com.expressba.express.net;


//HTTP应答返回参数

public class HttpResponseParam{
	RETURN_STATUS statusCode;	//状态码
	String responseClassName;	//当有多种返回的对象可能时,用这个名字来区分
	String responseString;		//响应的实体JSON字符串
	//String responseMessage;		//响应的消息字符串
	public HttpResponseParam(){
		statusCode = RETURN_STATUS.Ok;
		responseClassName = "";
	}

	public enum RETURN_STATUS{//--chao--这是枚举类型，如果不初始化，其中的每个项的值按照，0，1，2，3，4，5，6，7，8，9依次，比如Ok的值是0
		Ok,
		Saved,
		RequestException,
		ResponseException,
		ServerException,
		ObjectNotFoundException,
		NetworkException,
		Unknown
	}
}
