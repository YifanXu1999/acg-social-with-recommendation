'use client';

import { useEditor, EditorContent } from '@tiptap/react';
import StarterKit from '@tiptap/starter-kit';
import { Color } from '@tiptap/extension-color';
import TextStyle from '@tiptap/extension-text-style';
import Highlight from '@tiptap/extension-highlight';
import Link from '@tiptap/extension-link';
import BubbleMenu from '@tiptap/extension-bubble-menu';
import { EditorBubbleMenu } from './bubble-menu';

export default function TiptapEditor() {
  const editor = useEditor({
    extensions: [
      StarterKit,
      Color,
      TextStyle,
      Highlight,
      Link.configure({
        openOnClick: false,
        HTMLAttributes: {
          class: 'text-blue-500 underline',
        },
      }),
      BubbleMenu,
    ],
    content: '<p>Start writing something beautiful...</p>',
    editorProps: {
      attributes: {
        class:
          'prose prose-sm sm:prose lg:prose-lg xl:prose-2xl mx-auto focus:outline-none',
      },
    },
  });

  return (
    <div className="relative min-h-[500px] w-full max-w-screen-lg border-gray-200 bg-white sm:mb-[calc(20vh)] sm:rounded-lg sm:border sm:shadow-lg">
      {editor && <EditorBubbleMenu editor={editor} />}
      <div className="p-4">
        <EditorContent editor={editor} />
      </div>
    </div>
  );
}