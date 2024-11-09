import mongoose from "mongoose";

const chatSchema = new mongoose.Schema(
  {
    conversationId: {
      type: String,
      required: true,
    },
    message: {
      type: String,
      required: true,
    },
    sender: {
      type: String,
      required: true,
    },
    contentType: {
      type: String,
    },
    imageUrl: {
      type: String,
    },
  },
  {
    timestamps: true,
  }
);

const Chat = mongoose.model("Chat", chatSchema);

export default Chat;
