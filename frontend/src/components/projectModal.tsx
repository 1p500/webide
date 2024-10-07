"use cliend";

import React from 'react';

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  children: React.ReactNode;
}

export default function ProjectModal({isOpen, onClose, children}:ModalProps){
  if (!isOpen) return null;

  return(
    <div className='fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50'>
      <div className='bg-white p-6 rounded-md shadow-sm max-w-lg w-full'>
        <div className='flex justify-between items-center'>
          <h2 className='text-black'>My Projects</h2>
          <button onClick={onClose} className='text-black text-xl font-bold'>x</button>

        </div>
        <div className='mt-4'>{children}</div>
      </div>
    </div>
  )
}