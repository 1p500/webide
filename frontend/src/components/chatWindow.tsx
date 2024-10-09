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
    <div className="h-screen flex flex-col">
      {/* 채팅 메시지 영역 */}
      <div className="flex-grow p-4 bg-gray-100 overflow-y-auto">
        {messages.map((msg, index) => (
          <div key={index} className="chat-message p-2 bg-white rounded-lg shadow mb-2">
            {msg}
          </div>
        ))}
      </div>

      {/* 채팅 입력 영역 */}
      <div className="w-full p-4 bg-gray-800 flex items-center">
        <input
          type="text"
          className="w-3/4 flex-grow p-2 rounded-lg text-black"
          value={inputMessage}
          onChange={(e) => setInputMessage(e.target.value)}
          placeholder="Type your message..."
        />
        <button 
          className="w-1/4 ml-4 px-4 py-2 bg-black text-white rounded-lg hover:bg-gray-600"
          onClick={sendMessage}
        >
          Send
        </button>
      </div>
    </div>

  );
}
