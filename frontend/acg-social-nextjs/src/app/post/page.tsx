"use client";
import React from 'react'
import Editor from '@/components/textEditor/Editor'
export default function page() {
    return (
        <div className="min-h-screen bg-gray-50" >
        <div className="py-8">
          <Editor />
        </div>
      </div>
    )
}
