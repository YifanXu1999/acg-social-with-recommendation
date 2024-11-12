"use client";
import { Bell, MessageCircle, Search, User } from "lucide-react"
import Image from "next/image"
import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import  AcgSocialIcon from "@/app/assets/acg-social-icon.png"
import  Icon from "@/app/assets/dashicon.png"

import React from "react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel, DropdownMenuSeparator,
  DropdownMenuTrigger
} from "@/components/ui/dropdown-menu";
import Link from "next/link";
export default function DashboardHeader() {
  const [ifAuthenticated, setAuthenticated] = useState(false)
  return (
    <div className="static w-full opacity-70 h-12">
      <header className="bg-gradient-to-r border rounded-sm from-sky-200 to-sky-50 py-1 shadow-md backdrop-blur">
        <div className=" flex flex-row items-center justify-between">
          <div className="flex items-center w-3/4">
            <div className=" h-10">
              <Image src={Icon} alt="icon" width={120}
               />
            </div>

            <nav className=" flex ">
              <Button variant="ghost" className="text-purple-900 hover:bg-purple-400/30">
                Home
              </Button>
              <Button variant="ghost" className="text-purple-900 hover:bg-purple-400/30">
                Interest Groups
              </Button>
            </nav>
          </div>
          <div className="w-full flex items-center justify-between">
            <div className="relative flex items-center">
              <Image
                src={AcgSocialIcon}
                alt="ACG Social Logo"
                width={24}
                height={24}
                className="absolute left-2 rounded-full"
              />
              <Input
                className="w-full pl-10 sm:w-64 bg-white/30 text-purple-900 placeholder-purple-700/70 border-purple-200"
                placeholder="Search..."/>
              <Button variant="ghost" size="icon"
                      className="absolute right-0 bg-blue-200 text-purple-900 hover:bg-purple-400/50">
                <Search className="h-4 w-4"/>
              </Button>
            </div>

            <div className="flex items-center space-x-4">
              <Button variant="ghost" size="icon" className="text-purple-900 hover:bg-purple-400/30">
                <Bell className="h-5 w-5"/>
              </Button>
               <div className="pr-3">
                 <UserDropdown ifAuthenticated={ifAuthenticated} />
               </div>


            </div>
          </div>

        </div>
      </header>

    </div>
  )
}

const UserDropdown = ({ ifAuthenticated }: { ifAuthenticated: boolean }) => {
  const [ifOpenUserDropDown, setOpenUserDropDown] = useState(false)

  const AuthenticatedDropdownItems = [
    { id: '1', label: 'Profile', href: '/user/profile' },
    { id: '2', label: 'Logout', href: '/user/logout' },
  ]
  const UnAuthenticateddropdownItems = [
    { id: '1', label: 'Login', href: '/user/login' },
  ]

  const dropdownItems = ifAuthenticated ? AuthenticatedDropdownItems : UnAuthenticateddropdownItems
  const dropDownLabel = ifAuthenticated ? 'My Account' : 'System Setting'
  return (
    <div onMouseEnter={()=>setOpenUserDropDown(true)} onMouseLeave={()=>setOpenUserDropDown(false)}>
      <DropdownMenu open={ifOpenUserDropDown}>
        <DropdownMenuTrigger>
          <div className=" p-1.5  rounded-sm ring-transparent text-purple-900 hover:bg-purple-400/30 ">
            <User className="h-5 w-5"/>
          </div>
        </DropdownMenuTrigger>
        <DropdownMenuContent className="min-w-52  bg-purple-100">
          <DropdownMenuLabel> {dropDownLabel} </DropdownMenuLabel>
          <DropdownMenuSeparator />
          {
            dropdownItems.map((item) => {
              return (
                <Link href={item.href} key={item.id}>
                  <DropdownMenuItem >
                      {item.label}
                  </DropdownMenuItem>
                </Link>
              )
            })
          }
        </DropdownMenuContent>
      </DropdownMenu>

    </div>




  )
}