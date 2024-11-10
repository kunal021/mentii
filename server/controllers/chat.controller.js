import dotenv from "dotenv";
dotenv.config();
import { GoogleGenerativeAI } from "@google/generative-ai";
import Chat from "../schemas/chat.schema.js";
import Conversation from "../schemas/conversation.schema.js";
import User from "../schemas/user.schema.js";
import nlp from "compromise";

const client = new GoogleGenerativeAI(process.env.GEMINI_API_KEY);
const model = client.getGenerativeModel({ model: "gemini-1.5-flash" });

export const newConversation = async (req, res) => {
  const { message, userType, userId, name } = req.body;
  try {
    if (!userType) {
      return res.status(401).json({ error: "Unauthorized" });
    }

    if (!message) {
      return res.status(400).json({ error: "All fields are required" });
    }

    const doc = nlp(message);
    const keywords = doc.topics().out("array");

    const newConversation = await Conversation.create({});

    if (!newConversation) {
      return res
        .status(500)
        .json({ error: "Failed to create a new conversation." });
    }

    const newMessage = await Chat.create({
      conversationId: newConversation._id,
      message,
      sender: userType,
    });

    if (!newMessage) {
      return res.status(500).json({ error: "Failed to create a new message." });
    }

    newConversation.messages.push(newMessage._id);
    newConversation.lastMessage = newMessage._id;

    if (!userId) {
      return res.status(401).json({ error: "User not authenticated." });
    }

    await User.findByIdAndUpdate(
      userId,
      {
        $push: {
          conversations: newConversation._id,
          keyWords: { $each: keywords },
        },
      },
      { new: true }
    );

    await newConversation.save({
      validateBeforeSave: false,
    });

    const chat = model.startChat({
      generationConfig: { temperature: 0.6, maxOutputTokens: 500 },
    });

    const promptText = `You are a helpful mental health support assistant. Please provide a comforting response to the following user message: "${message}". The message should start with user's name ${name}`;

    const result = await chat.sendMessage(promptText);

    // const title = await chat.sendMessage(
    //   `Please provide oly one short and crisp title for the conversation: "${message}"`
    // );

    const botResponse = await Chat.create({
      conversationId: newConversation._id,
      message: result.response.text(),
      sender: "bot",
    });

    newConversation.messages.push(botResponse._id);
    newConversation.lastMessage = botResponse._id;
    await newConversation.save({
      validateBeforeSave: false,
    });

    return res.status(200).json({
      ...botResponse._doc,
      // title: title.response.text(),
    });
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

    // Create user message
    const newMessage = await Chat.create({
      conversationId,
      message,
      sender: userId,
    });

    const conversation = await Conversation.findById(conversationId);

    conversation.messages.push(newMessage._id);
    conversation.lastMessage = newMessage._id;

    await conversation.save({
      validateBeforeSave: false,
    });

    // Format history correctly for the chat model
    const history = conversation.messages.map((chat) => ({
      role: chat.sender === "bot" ? "assistant" : "user",
      content: chat.message,
    }));

    // Add the current message to history
    history.push({
      role: "user",
      content: message,
    });

    try {
      // Start chat session
      const chat = await model
        .startChat
        //   {
        //   history,
        //   generationConfig: { temperature: 0.6, maxOutputTokens: 500 },
        // }
        ();

      const doc = nlp(message);
      const keywords = doc.topics().out("array");

      await User.findByIdAndUpdate(
        userId,
        {
          $push: { keyWords: { $each: keywords } },
        },
        { new: true }
      );

      // Send message with proper structure
      const result = await chat.sendMessage(message, {
        context: `You are a helpful mental health support assistant. Please provide comforting and supportive responses.`,
        history,
      });

      // Get response text
      const responseText = result.response.text();

      // Save bot response to database
      const botResponse = await Chat.create({
        conversationId,
        message: responseText,
        sender: "bot",
      });

      // Update conversation with bot response
      conversation.messages.push(botResponse._id);
      conversation.lastMessage = botResponse._id;
      await conversation.save({
        validateBeforeSave: false,
      });

      return res.status(200).json({ ...botResponse._doc });
    } catch (chatError) {
      console.error("Chat API Error:", chatError);
      return res.status(500).json({
        error: "Failed to generate response",
        details: chatError.message,
      });
    }
  } catch (error) {
    console.error("Server Error:", error);
    return res.status(500).json({ error: error.message });
  }
};

export const getChat = async (req, res) => {
  const { conversationId } = req.body;
  try {
    const chat = await Chat.find({ conversationId }).sort({ createdAt: -1 });

    if (!chat) {
      return res.status(404).json({ error: "Chat not found" });
    }

    return res.status(200).json({ chat });
  } catch {
    return res.status(500).json({ error: error.message });
  }
};

export const getChatById = async (req, res) => {
  const { chatId } = req.body;
  try {
    const chat = await Chat.findById(chatId);

    if (!chat) {
      return res.status(404).json({ error: "Chat not found" });
    }

    return res.status(200).json({ chat });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

export const getConversationsById = async (req, res) => {
  const { conversationId } = req.body;
  try {
    const conversation = await Conversation.findById(conversationId).populate({
      path: "messages",
      populate: {
        path: "resultId",
      },
    });

    if (!conversation) {
      return res.status(404).json({ error: "Conversation not found" });
    }

    return res.status(200).json({ conversation });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
