import React from 'react';
import { Save, Copy, Share2 } from 'lucide-react';

interface EditorHeaderProps {
  onSave: () => void;
  onCopy: () => void;
  onShare: () => void;
  isSaved: boolean;
}

export default function EditorHeader({ onSave, onCopy, onShare, isSaved }: EditorHeaderProps) {
  return (
    <div className="flex items-center justify-between p-4 bg-gradient-to-r from-purple-600 to-blue-500">
      <h1 className="text-2xl font-bold text-white">Quill Editor</h1>
      <div className="flex gap-3">
        <button
          onClick={onSave}
          className="flex items-center gap-2 px-4 py-2 bg-white text-purple-600 rounded-md hover:bg-purple-50 transition-colors"
        >
          <Save size={18} />
          {isSaved ? 'Saved!' : 'Save'}
        </button>
        <button
          onClick={onCopy}
          className="flex items-center gap-2 px-4 py-2 bg-white text-purple-600 rounded-md hover:bg-purple-50 transition-colors"
        >
          <Copy size={18} />
          Copy
        </button>
        <button
          onClick={onShare}
          className="flex items-center gap-2 px-4 py-2 bg-white text-purple-600 rounded-md hover:bg-purple-50 transition-colors"
        >
          <Share2 size={18} />
          Share
        </button>
      </div>
    </div>
  );
}