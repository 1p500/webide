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
    <div>
      <h2>Code Editor</h2>

      {/* 언어 선택 버튼 */}
      <div>
        <button onClick={() => setLanguage("javascript")} className="m-2 p-2 bg-blue-500 text-white">
          JavaScript
        </button>
        <button onClick={() => setLanguage("python")} className="m-2 p-2 bg-green-500 text-white">
          Python
        </button>
        <button onClick={() => setLanguage("html")} className="m-2 p-2 bg-red-500 text-white">
          HTML
        </button>
      </div>

      <ReactCodeMirror
        value={code}
        height="200px"
        theme={oneDark}
        extensions={[getLanguageExtension()]} // 언어에 따라 확장을 동적으로 변경
        onChange={(value) => onChange(value)}
      />
    </div>
  );
}
