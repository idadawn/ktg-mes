package com.ktg.mes.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mes websocket服务端  为组态、大屏、提供数据推送
 */
@ServerEndpoint("/websocket/mes")
@Slf4j
@Component
public class MesWebSocket {
	private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();
	public final static String WEBSOCKET_HEARTBEAT = "heartbeat-iot";
	/**
	 * 客户端成功连接时调用
	 *
	 * @param session 存储的session
	 */
	@OnOpen
	public void openSession(Session session) {
		log.warn("Mes WebSocket：{} 已连接", session.getId());
		sessionMap.put(session.getId(),session);
	}

	/**
	 * 服务端接收到消息
	 *
	 * @param session 会话，每个访问对象都会有一个单独的会话
	 * @param message 服务端接收到的消息  格式：  deviceCode:nodeCode:pointCode1,pointCode2,pointCode3
	 */
	@OnMessage(maxMessageSize = 10485760)
	public void onMessage(Session session, String message) {
		log.warn("Mes WebSocket：接收到 {} 的消息 {}", session.getId(), message);
		if(WEBSOCKET_HEARTBEAT.equals(message)){
			sendMessage(session,"OK");
		}
	}

	/**
	 * 客户端断开连接时调用
	 *
	 * @param session 存储的session
	 */
	@OnClose
	public void onClose(Session session) {
		log.warn("Mes WebSocket：{} 断开连接", session.getId());
		removeSessionData(session);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		removeSessionData(session);
	}

	private void removeSessionData(Session session) {
		String sessionId = session.getId();
		sessionMap.remove(sessionId);
	}

	/**
	 * 向指定Session(用户)发送message
	 *
	 * @param session 用session进行判断
	 * @param message 发送的消息
	 */
	private static void sendMessage(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
			log.warn("Mes sendData {}", message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给对应的测点session客户端集合发送测点实时数据
	 *
	 * @param message    发送消息
	 * @param sessionMap 获取sessionIds发送实时数据
	 */
	private static synchronized void sendMessageAll(String message, Map<String, Session> sessionMap) {
		for (String key : sessionMap.keySet()) {
			log.info("sessionId: {}", key);
			sendMessage(sessionMap.get(key), message);
		}
	}

	/**
	 * 检查是否有客户端监听此设备的实时数据
	 *
	 * @param message   发送消息内容
	 */
	public static void pushData(String message) {
		if (ObjectUtils.isNotEmpty(sessionMap)) {
			sendMessageAll(message, sessionMap);
		}
	}
}
