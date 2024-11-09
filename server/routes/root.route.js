import express from "express";
import authRoute from "./auth.route.js";
import chatRoute from "./chat.route.js";
import subscriptionRoute from "./subscription.route.js";

const router = express.Router();

router.use("/auth", authRoute);
router.use("/chat", chatRoute);
router.use("/subscription", subscriptionRoute);

export default router;
