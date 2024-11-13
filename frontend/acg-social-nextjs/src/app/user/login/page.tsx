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




export default function Page() {
  const  [isLoginPage, setIsLoginPage] = useState(true)
  useEffect(() => {
    setIsLoginPage(true);
  }, []);


  
  return isLoginPage ? <LoginModal  setIsLoginPage={setIsLoginPage}/> : <SignupModal setIsLoginPage={setIsLoginPage}/>
  // return <SignupModal setIsLoginPage={setIsLoginPage}/>

}



const LoginModal = ({setIsLoginPage}: {setIsLoginPage: Dispatch<SetStateAction<boolean>>}) => {
  const router = useRouter()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const handleLogin = async  (e: React.FormEvent) => {
      e.preventDefault()
      console.log('Login attempted with:', { email, password })
      const  response = await  axios.post('/api/user/login', { email, password }).then(r=>r.data).catch((e) => {
        setError(e.response.data.errors)
      })
      if(response == undefined) {
        return;
      }
      router.back()
  }

  return (
    <Dialog defaultOpen={true} onOpenChange={() => router.back()}>
      <DialogContent className="sm:max-w-[450px]" >
        <DialogHeader>
          <DialogTitle>Login          </DialogTitle>
          <DialogDescription>
            Enter your credentials to access your account.
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleLogin} className="space-y-4 ">
          <div className="space-y">
            <Label htmlFor="email">
              Email        

            </Label>
            <Input
              id="email"
              type="email"
              placeholder="your@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="password">Password</Label>
            <Input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <span className="text-red-500 text-sm">{error}</span>}
  
          <Button type="submit" className="w-full">Log in</Button>
            <div className="text-sm text-gray-500 cursor-pointer" onClick={() => setIsLoginPage(false)}>
              Dont have an account? 
              <span className='font-bold pl-3'>Register</span>
            </div>
        </form>

      </DialogContent>
    </Dialog>

  )
}

const SignupModal =  ({setIsLoginPage}: {setIsLoginPage: Dispatch<SetStateAction<boolean>>}) => {
 
  const router = useRouter()
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [rePassword, setRePassword] = useState('')
  const [error, setError] = useState('')

  
  const handleSignup = async (e: React.FormEvent) => {
      e.preventDefault()
      console.log('Passwords:', { password, rePassword })
      if (password !== rePassword) {
          setError('Passwords do not match')
          return;
      }
      console.log('Signup attempted with:', { email, password })
      const response = await axios.post('/api/user/signup', { email, password }).then(r=>r.data).catch((e) => {
        setError(e.response.data.errors)
      })
      if(response == undefined) {
        return;
      }

      router.back()



  }

  
  return (
        <Dialog defaultOpen={true} onOpenChange={()=> {router.back() }}>
          <DialogContent className="sm:max-w-[450px]" >
            <DialogHeader>
              <DialogTitle>Sign Up</DialogTitle>
              <DialogDescription>
                Create an account to get started.
              </DialogDescription>
            </DialogHeader>
            <form onSubmit={handleSignup} className="space-y-4 ">
              <div className="space-y">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="your@email.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="password">Password</Label>
                <Input
                  id="password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="rePassword">Re-enter Password</Label>
                <Input
                  id="rePassword"
                  type="password"
                  value={rePassword}
                  onChange={(e) => setRePassword(e.target.value)}
                  required
                />
              </div>
              {error && <span className="text-red-500 text-sm">{error}</span>}
              <Button type="submit" className="w-full">Sign up</Button>
              <div className="text-sm text-gray-500 cursor-pointer" onClick={() => setIsLoginPage(false)}>
                Dont have an account?
                <span className='font-bold pl-3'>Register</span>
              </div>
            </form>
          </DialogContent>
        </Dialog>


  )
}