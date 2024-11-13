'use client';

import { useState } from 'react';
import dynamic from 'next/dynamic';
import 'react-quill/dist/quill.snow.css';
import { modules, formats } from './toolbar-options';
import { Card } from '@/components/ui/card';

const ReactQuill = dynamic(() => import('react-quill'), {
  ssr: false,
  loading: () => (
    <div className="h-[400px] w-full animate-pulse rounded-lg bg-gray-100"></div>
  ),
});

export default function QuillEditor() {
  const [content, setContent] = useState('');

  return (
    <Card className="w-full overflow-hidden bg-white p-0">
      <div className="min-h-[500px] w-full">
        <ReactQuill
          theme="snow"
          value={content}
          onChange={setContent}
          modules={modules}
          formats={formats}
          className="h-[400px]"
          placeholder="Start writing something amazing..."
        />
      </div>
    </Card>
  );
}