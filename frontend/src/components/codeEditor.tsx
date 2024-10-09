// components/codeEditor.tsx
"use client";

import React, { useState } from "react";
import ReactCodeMirror from '@uiw/react-codemirror';
import { javascript } from "@codemirror/lang-javascript";
import { python } from "@codemirror/lang-python";
import { html } from "@codemirror/lang-html";
import { oneDark } from "@codemirror/theme-one-dark";

export default function CodeEditor() {
  const [code, setCode] = useState("// 여기에 코드를 작성하세요");
  const [language, setLanguage] = useState("javascript");

  const onChange = (value: string) => {
    setCode(value);
    console.log("코드 변경됨:", value);
  };

  // 언어에 따라 CodeMirror 확장을 선택하는 함수
  const getLanguageExtension = () => {
    switch (language) {
      case "javascript":
        return javascript();
      case "python":
        return python();
      case "html":
        return html();
      default:
        return javascript();
    }
  };

  return (
    <div className='h-screen flex flex-col'>
      {/* 상단 영역: 언어 선택 및 제목 */}
      <div className='flex items-center justify-between p-2 bg-gray-800 text-white'>
        <h2>Code Editor</h2>
        <div className='flex space-x-4'>
          <button onClick={() => setLanguage("javascript")} className="px-4 py-2 bg-blue-500 text-white">
            JavaScript
          </button>
          <button onClick={() => setLanguage("python")} className="px-4 py-2 bg-green-500 text-white">
            Python
          </button>
          <button onClick={() => setLanguage("html")} className="px-4 py-2 bg-red-500 text-white">
            HTML
          </button>
      </div>
    </div>

  {/* 코드 에디터 영역 */}
  <div className='flex-grow'>
    <ReactCodeMirror
      value={code}
      height='100%'    
      theme={oneDark}
      extensions={[getLanguageExtension()]}  // 동적 언어 확장
      onChange={(value) => onChange(value)}
    />
  </div>
</div>

  );
}
