"use client"
import React, { useRef } from 'react';
import dynamic from 'next/dynamic';
const ReactQuill = dynamic(() => import("react-quill-new"), { ssr: false });
import 'react-quill-new/dist/quill.snow.css';
import EditorHeader from './EditorHeader';

import { modules } from './EditorToolbar';

export default function Editor() {
  const [content, setContent] = React.useState('');
  const [isSaved, setIsSaved] = React.useState(false);
  const quillRef = useRef<ReactQuill>(null);

  const handleSave = () => {
    localStorage.setItem('editor-content', content);
    setIsSaved(true);
    setTimeout(() => setIsSaved(false), 2000);
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(content);
  };

  const handleShare = () => {
    const url = window.location.href;
    navigator.clipboard.writeText(url);
  };

  React.useEffect(() => {
    const savedContent = localStorage.getItem('editor-content');
    if (savedContent) {
      setContent(savedContent);
    }
  }, []);

  return (
    <div className="w-full max-w-6xl mx-auto p-6 h-full">
      <div className="bg-white rounded-lg shadow-xl overflow-hidden ">
        <EditorHeader
          onSave={handleSave}
          onCopy={handleCopy}
          onShare={handleShare}
          isSaved={isSaved}
        />
        
        <div className="editor-container">
          <ReactQuill
            ref={quillRef}
            theme="snow"
            value={content}
            onChange={setContent}
            modules={modules}
          />
        </div>
      </div>

      {/* <Preview content={content} /> */}
    </div>
  );
}