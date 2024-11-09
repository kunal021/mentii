import express from "express";
import {
  //   chat,
  getConversations,
  newConversation,
  startChat,
} from "../controllers/chat.controller.js";
import authValidation from "../middlewares/authValidation.middleware.js";

const router = express.Router();

router.post("/new-conversation", authValidation, newConversation);
router.get("/get-conversations", authValidation, getConversations);
router.post("/start-chat", authValidation, startChat);
// router.post("/", authValidation, chat);

export default router;
