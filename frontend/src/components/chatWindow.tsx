"use client";

import React, { useEffect, useState } from 'react';

// WebSocket 서버 URL
const WS_URL = "ws://localhost:8080/chat";

export default function Chat() {
  const [messages, setMessages] = useState<string[]>([]); // 메시지 목록
  const [inputMessage, setInputMessage] = useState<string>(''); // 입력 메시지
  const [ws, setWs] = useState<WebSocket | null>(null); // WebSocket 인스턴스

  useEffect(() => {
    // WebSocket 연결
    const socket = new WebSocket(WS_URL);
    setWs(socket);

    socket.onopen = () => {
      console.log('WebSocket connected');
    };

    socket.onmessage = (event) => {
      // 서버에서 새로운 메시지를 받을 때마다 상태를 업데이트
      setMessages((prevMessages) => [...prevMessages, event.data]);
    };

    socket.onclose = () => {
      console.log('WebSocket disconnected');
    };

    return () => {
      socket.close();
    };
  }, []);

  const sendMessage = () => {
    if (ws && inputMessage) {
      // WebSocket을 통해 메시지를 서버로 전송
      ws.send(inputMessage);
      setInputMessage(''); // 메시지를 전송한 후 입력창을 비움
    }
  };

  return (
    <div className="chat-container">
      <div className="chat-messages">
        {messages.map((msg, index) => (
          <div key={index} className="chat-message">
            {msg}
          </div>
        ))}
      </div>
      <div className="chat-input">
        <input
          type="text"
          value={inputMessage}
          onChange={(e) => setInputMessage(e.target.value)}
          placeholder="Type your message..."
        />
        <button onClick={sendMessage}>Send</button>
      </div>
    </div>
  );
}
