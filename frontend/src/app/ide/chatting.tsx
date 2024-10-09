"use client";
import React, { useRef, useState } from "react";
// import {
//   GeneralChattingDiv,
//   ChattingMessages,
//   ChattingMessage,
//   ChattingName,
//   ChattingContent,
//   ChattingInputForm,
//   ChattingInput,
//   ChattingSendButton,
// } from './Chatting.styles';
import { useGeneralChatStore } from "@/store/useChattingStore";
import { Client } from "@stomp/stompjs";
import { create } from "zustand";

interface ChattingType {
  messageType: "ENTER" | "EXIT" | "TALK";
  userNickname: string;
  content: string;
}

interface GeneralChatState {
  messages: ChattingType[];
  addMessage: (message: ChattingType) => void;
}

const useGeneralChatStore = create<GeneralChatState>((set) => ({
  messages: [],
  addMessage: (message: ChattingType) =>
    set((state) => ({
      messages: [...state.messages, message as ChattingType],
    })),
}));

interface GeneralChattingProps {
  clientRef: React.RefObject<Client>;
}

const getCurrentProjectId = () => {
  if (typeof window !== "undefined") {
    const path = window.location.pathname;
    const pathSegments = path.split("/");
    const projectId = pathSegments[2];
    return projectId;
  }
  return "";
};

const GeneralChatting: React.FC<GeneralChattingProps> = ({ clientRef }) => {
  const scrollRef = useRef<HTMLDivElement | null>(null);
  const client = clientRef.current;
  const [sendContent, setSendContent] = useState("");
  const messages = useGeneralChatStore((state) => state.messages);
  const messagesEndRef = useRef<HTMLDivElement | null>(null);

  const handleContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setSendContent(e.target.value);
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (sendContent === "") {
      alert("채팅을 입력해주세요");
    } else if (sendContent.length > 200) {
      alert("채팅은 200자 이내로 입력해주세요");
    } else {
      const sendMessage = { content: sendContent };

      if (client) {
        client.publish({
          destination: `/app/project/${getCurrentProjectId()}/chat-create`,
          body: JSON.stringify(sendMessage),
        });
        setSendContent("");
      }
    }
  };
  return (
    <GeneralChattingDiv>
      <ChattingMessages ref={scrollRef}>
        {messages.map((message, index) => {
          const { messageType, userNickname, content, currentUsers } = message;
          console.log(currentUsers);
          return (
            <ChattingMessage key={index}>
              {messageType === "TALK" ? (
                <>
                  <ChattingName>{userNickname}</ChattingName>
                  <ChattingContent>{content}</ChattingContent>
                </>
              ) : (
                <ChattingContent>{content}</ChattingContent>
              )}
            </ChattingMessage>
          );
        })}
        <div ref={messagesEndRef} />
      </ChattingMessages>

      <ChattingInputForm onSubmit={handleSubmit}>
        <ChattingInput
          placeholder="채팅을 입력하세요"
          value={sendContent}
          onChange={handleContent}
        />
        <ChattingSendButton type="submit">전송</ChattingSendButton>
      </ChattingInputForm>
    </GeneralChattingDiv>
  );
};

export default GeneralChatting;
