import express from "express";
import {
  getChat,
  //   chat,
  getConversations,
  newConversation,
  startChat,
} from "../controllers/chat.controller.js";
import authValidation from "../middlewares/authValidation.middleware.js";
import multer from "multer";

const upload = multer();

const router = express.Router();

router.post(
  "/new-conversation",
  upload.none(),
  authValidation,
  newConversation
);
router.post(
  "/get-conversations",
  upload.none(),
  authValidation,
  getConversations
);
router.post("/start-chat", upload.none(), authValidation, startChat);
router.post("/get-chats", upload.none(), authValidation, getChat);

export default router;
