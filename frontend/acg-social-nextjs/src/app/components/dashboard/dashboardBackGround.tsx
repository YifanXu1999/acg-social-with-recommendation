import React from 'react'
import Image from "next/image";
import Background from "@/app/assets/dashboard-background.png"
export default function DashboardBackGround() {

  return (
    <div className='h-full'>
      <div className="fixed inset-0 -z-10">
        <Image src={Background} fill  style={{objectFit: 'cover'}} alt="background"></Image>
        <div className="absolute inset-0 bg-gradient-to-b from-gray-300"> </div>
      </div>
    </div>
  );

}
