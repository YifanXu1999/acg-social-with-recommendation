"use client"

import React  from 'react'
import "@/app/components/label/dashboardLabel"

interface DropdownItem {
  id: string
  label: string
}

interface DropdownListProps {
  items: DropdownItem[],
  itemStlye?: string
}

export default function DropDownList({ items = [], itemStlye = "" }: DropdownListProps) {
  return (

      <div >
        {items.map((item) => (
          <a key={item.id}
             href="#"
             className={`block text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900 ${itemStlye}`}
          >
            {item.label}
          </a>
        ))}
      </div>

  )
}