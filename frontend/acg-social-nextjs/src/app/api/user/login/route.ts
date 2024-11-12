import {NextResponse} from "next/server";
import {UserLoginSchema} from "@/app/api/types";
import axios from "axios";



export async function POST(request: Request) {
  const body = await request.json();

  // Use Zod to validate the received data against the UserSchema
  const result = UserLoginSchema.safeParse(body);
  if(! result.success) {
    const requestError = Object.fromEntries(
      result.error?.issues?.map((issue) => [issue.path[0], issue.message]) || []
    );
    return NextResponse.json({ errors: requestError });
  }

console.log(result)
  const response = await axios.post('http://localhost:8080/user/authenticate/login', result.data);
  console.log(response.data)
  return NextResponse.json({
    code: 0,
    message: "success",
    result: response.data
  })

}