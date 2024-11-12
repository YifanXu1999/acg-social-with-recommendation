import {NextResponse} from "next/server";
import {UserLoginSchema} from "@/app/api/types";




export async function POST(request: Request) {
  var body = await request.json();

  // Use Zod to validate the received data against the UserSchema
  body = UserLoginSchema.safeParse(body);
  if(! body.success) {
    const requestError = Object.fromEntries(
      body.error?.issues?.map((issue) => [issue.path[0], issue.message]) || []
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

  const  response =  NextResponse.json({
    code: 0,
    message: "success",
    result: r
  })

  console.log(r)

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