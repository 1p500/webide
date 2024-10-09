"use client";

import React from 'react';
import CodeEditor from '@/components/codeEditor';
import { FileAddTransparent, FolderAddTransparent } from '@/components/icons/icons';

export default function GuestIdePage() {

  return (
    <div className='flex h-screen'>
      <aside className="w-1/5 bg-gray-800">
        <div className='flex justify-between items-center p-3'>
          {/* <p> Guest Mode</p> 사이드바 */}
          <ul className='flex space-x-4 ml-auto'>
            <li>
              <button>  <FolderAddTransparent className='w-5 h-5'/> </button>
            </li>
            <li>
              <button> <FileAddTransparent className='w-5 h-5'/> </button>
            </li>
          </ul>
        </div>
      </aside>
      <div className='w-4/5 bg-blue h-screen'>
        <CodeEditor /> 
      </div>
      
    </div>
  );
}
