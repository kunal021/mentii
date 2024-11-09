import express from "express";
import authRoute from "./auth.route.js";
import chatRoute from "./chat.route.js";

const router = express.Router();

router.use("/auth", authRoute);
router.use("/chat", chatRoute);

export default router;
