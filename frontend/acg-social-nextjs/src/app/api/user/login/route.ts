import {NextResponse} from "next/server";
import {UserLoginSchema} from "@/app/api/types";




export async function POST(request: Request) {
  let body = await request.json();
  // Use Zod to validate the received data against the UserSchema
  body = UserLoginSchema.safeParse(body);
  if(! body.success) {
    const requestError = Object.fromEntries(
      body.error.issues.map((issue: { path: string[]; message: string }) => [issue.path[0], issue.message]) || []
    );
    return NextResponse.json({ errors: requestError });
  }

  const r = await fetch('http://localhost:8080/user/authenticate/login', {
    method: 'POST', // Specify the HTTP method
    headers: {
      'Content-Type': 'application/json' // Set the content type
    },
    body: JSON.stringify(body.data) // Convert data to JSON string
  }).then(res => res.json());


  if(! r.success) {
    console.log(NextResponse.json({ errors: r.message }))
    return NextResponse.json({ errors: r.message }, { status: 401 });
  }
  const  response =  NextResponse.json({
    code: 0,
    message: "success",
    result: r
  })


  response.cookies.set("token", r.data, {
    path: "/",
    httpOnly: true,
    maxAge: 60 * 60 * 24 * 30,
    secure: true,
    sameSite: "strict",
    expires: new Date(Date.now() + 1000 * 60 * 60 * 24 * 30),
  });

  return response;

}