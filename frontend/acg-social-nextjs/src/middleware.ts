import { NextRequest } from "next/server";
import { NextResponse } from "next/server";
export function middleware(request: NextRequest) {
  console.log(request.nextUrl.pathname, "ε=ε=ε=ε=┏( ￣▽￣)┛");

  if(request.nextUrl.pathname != "/user/login") {
    const token = request.cookies.get("token");
    if(!token) {
      console.log("No token", "ε=ε=ε=ε=┏( ￣▽￣)┛");
      return NextResponse.redirect(new URL("/user/login", request.url));
    } else {
      console.log("Token", token, "ε=ε=ε=ε=┏( ￣▽￣)┛");
    }
  } 

}


export const config = {
  matcher: ["/user/:path*"],

};
