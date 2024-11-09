import dotenv from "dotenv";
dotenv.config();
import { GoogleGenerativeAI } from "@google/generative-ai";
import Chat from "../schemas/chat.schema.js";
import Conversation from "../schemas/conversation.schema.js";
import User from "../schemas/user.schema.js";
// console.log(process.env.GEMINI_API_KEY);

const client = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);
const model = client.getGenerativeModel({ model: "gemini-1.5-flash" });

// export const chat = async (req, res) => {
//   const { message, conversationId, userId } = req.body;

//   try {
//     if (!userId) {
//       return res.status(401).json({ error: "Unauthorized" });
//     }
//     if (!message) {
//       return res.status(400).json({ error: "All fields are required" });
//     }

//     let conversation;
//     if (conversationId) {
//       conversation = await Conversation.findOne({
//         _id: conversationId,
//       }).populate({
//         path: "messages",
//         select: "sender",
//       });
//       if (!conversation) {
//         return res.status(404).json({ error: "Conversation not found" });
//       }
//     } else {
//       conversation = await Conversation.create({});
//     }
//     console.log(conversation);

//     const newMessage = await Chat.create({
//       conversationId: conversation._id,
//       message,
//       sender: userId,
//     });

//     conversation.messages.push(newMessage._id);
//     conversation.lastMessage = newMessage._id;

//     await conversation.save({
//       validateBeforeSave: false,
//     });

//     const history = conversation.messages.map((chat) => ({
//       role: chat.sender === "bot" ? "model" : "user",
//       parts: [{ text: chat.message }],
//     }));

//     // const history = conversation.messages.map((chat) => ({
//     //   role: chat.sender !== "bot" ? "user" : "model",
//     //   parts: [{ text: chat.message }],
//     // }));

//     console.log(JSON.stringify(history, null, 2));

//     const chat = model.startChat({
//       history: history,
//       generationConfig: { temperature: 0.6, maxOutputTokens: 500 },
//     });

//     const promptText = `You are a helpful mental health support assistant. Please provide a comforting response to the following user message: "${message}"`;

//     const result = await chat.sendMessage(promptText);
//     // console.log(result.response.text());

//     return res.status(200).json({ result: result.response.text() });
//   } catch (error) {
//     return res.status(500).json({ error: error.message });
//   }
// };

export const newConversation = async (req, res) => {
  const { message, userType } = req.body;
  try {
    if (!userType) {
      return res.status(401).json({ error: "Unauthorized" });
    }

    if (!message) {
      return res.status(400).json({ error: "All fields are required" });
    }

    const newConversation = await Conversation.create({});
    const newMessage = await Chat.create({
      conversationId: newConversation._id,
      message,
      sender: userType,
    });

    newConversation.messages.push(newMessage._id);
    newConversation.lastMessage = newMessage._id;

    await User.findByIdAndUpdate(
      req.user._id,
      {
        $push: { conversations: newConversation._id },
      },
      { new: true }
    );

    await newConversation.save({
      validateBeforeSave: false,
    });

    const chat = model.startChat({
      generationConfig: { temperature: 0.6, maxOutputTokens: 500 },
    });

    const promptText = `You are a helpful mental health support assistant. Please provide a comforting response to the following user message: "${message}"`;

    const result = await chat.sendMessage(promptText);

    const chatModel = await Chat.findById(newMessage._id);

    if (!chatModel) {
      return res.status(404).json({ error: "Chat not found" });
    }

    chatModel.result = result.response.text();

    await chatModel.save({
      validateBeforeSave: false,
    });

    return res.status(200).json({ result: result.response.text() });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const getConversations = async (req, res) => {
  const { userId } = req.body;
  try {
    if (!userId) {
      return res.status(401).json({ error: "Unauthorized" });
    }
    const user = await User.findById(userId).populate({
      path: "conversations",
      populate: {
        path: "messages",
      },
    });
    return res.status(200).json({ conversations: user.conversations });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const startChat = async (req, res) => {
  const { conversationId, message, userId } = req.body;
  try {
    if (!conversationId) {
      return res.status(401).json({ error: "Unauthorized" });
    }

    if (!message) {
      return res.status(400).json({ error: "All fields are required" });
    }

    const newMessage = await Chat.create({
      conversationId,
      message,
      sender: userId,
    });

    const conversation = await Conversation.findById(conversationId).populate({
      path: "messages",
      select: "sender message",
    });

    conversation.messages.push(newMessage._id);
    conversation.lastMessage = newMessage._id;

    await conversation.save({
      validateBeforeSave: false,
    });

    // Ensure each message part contains 'text' data as required by the API
    const history = conversation.messages.map((chat) => ({
      role: chat.sender === "bot" ? "model" : "user",
      parts: [{ data: { text: chat.message } }],
    }));

    const chat = model.startChat({
      history,
      generationConfig: { temperature: 0.6, maxOutputTokens: 500 },
    });

    const promptText = `You are a helpful mental health support assistant. Please provide a comforting response to the following user message: "${message}"`;

    // Call sendMessage with the correctly structured request object
    const result = await chat.sendMessage({
      prompt: promptText,
      history,
    });

    return res.status(200).json({ result: result.response.text() });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
