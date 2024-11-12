import { z } from "zod"; // Add new import

export const UserLoginSchema = z
  .object({
    email: z.string().email(),
    password: z
      .string()
      .min(4, { message: "Password is too short" })
      .max(20, { message: "Password is too long" }),
    }
  );