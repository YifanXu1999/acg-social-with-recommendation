import { Bell, Heart, MessageCircle, Search, User } from "lucide-react"
import Image from "next/image"

import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import  AcgSocialIcon from "@/app/assets/acg-social-icon.png"
export default function Dashboard() {
  return (
    <div className="fixed w-full">
      <header className="bg-gradient-to-r border rounded-sm from-purple-400 to-pink-300 py-1 shadow-md backdrop-blur">
        <div className=" flex flex-row items-center justify-between">
          <div className="flex items-center w-3/4">
            <h1 className="text-xl font-bold text-purple-900 pl-1"> ACG Social</h1>
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
                className="w-full pl-10 sm:w-64 bg-white/30 text-purple-900 placeholder-purple-700/70 border-purple-400"
                placeholder="Search..."/>
              <Button variant="ghost" size="icon"
                      className="absolute right-0 bg-purple-400/30 text-purple-900 hover:bg-purple-400/50">
                <Search className="h-4 w-4"/>
              </Button>
            </div>
            <div className="flex items-center space-x-4">
              <Button variant="ghost" size="icon" className="text-purple-900 hover:bg-purple-400/30">
                <Bell className="h-5 w-5"/>
              </Button>
              <Button variant="ghost" size="icon" className="text-purple-900 hover:bg-purple-400/30">
                <MessageCircle className="h-5 w-5"/>
              </Button>
              <Button variant="ghost" size="icon" className="text-purple-900 hover:bg-purple-400/30">
                <User className="h-5 w-5"/>
              </Button>
            </div>
          </div>

        </div>
      </header>

    </div>
  )
}