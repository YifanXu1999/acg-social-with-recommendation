import React from "react";
import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Heart, MessageCircle, User} from "lucide-react";


export default function Home() {
  return (

    <div>
      <main className="container mx-auto p-4 opacity-80">
        <div className="mb-6">
          <h2 className="text-xl font-semibold text-sky-800">Welcome back, User!</h2>
        </div>
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
          <Card className="bg-white shadow-lg">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium text-sky-600">Total Likes</CardTitle>
              <Heart className="h-4 w-4 text-pink-500"/>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-sky-800">1,234</div>
              <p className="text-xs text-sky-600">+15% from last week</p>
            </CardContent>
          </Card>
          <Card className="bg-white shadow-lg">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium text-sky-600">New Followers</CardTitle>
              <User className="h-4 w-4 text-sky-500"/>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-sky-800">567</div>
              <p className="text-xs text-sky-600">+7% from last week</p>
            </CardContent>
          </Card>
          <Card className="bg-white shadow-lg">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium text-sky-600">Messages</CardTitle>
              <MessageCircle className="h-4 w-4 text-green-500"/>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-sky-800">89</div>
              <p className="text-xs text-sky-600">+3% from last week</p>
            </CardContent>
          </Card>
        </div>
      </main>
    </div>
  );
}
