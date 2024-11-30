"use client"
import { Dialog } from '@radix-ui/react-dialog'
import {
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {Button} from "@/components/ui/button";
import {Dispatch, SetStateAction, useEffect, useState} from "react";
import { useRouter } from 'next/navigation';

import {Input} from "@/components/ui/input";
import {Label} from "@/components/ui/label";
import axios from "axios";
import Link from "next/link";





export default function Page() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const router = useRouter();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    try {
      const response = await axios.post("/api/user/login", { email, password });
      if (response.status === 200) {
        router.push("/dashboard");
      }
    } catch (error) {
      console.error("Login failed", error);
    }
  };



  const currentPath = '' ; // Get the current path

  const clientId = "your_github_client_id";
  const redirectUri = `http://127.0.0.1:11025/oauth2/authorization/github`; 

  return (
    <div className="login-page">
      <form onSubmit={handleSubmit}>
        <Label htmlFor="email">Email</Label>
        <Input
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <Label htmlFor="password">Password</Label>
        <Input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <Button type="submit">Login</Button>
      </form>

      <Link href={redirectUri}>Login with GitHub</Link>
    </div>
  );
}
