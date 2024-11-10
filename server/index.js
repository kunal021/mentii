import dotenv from "dotenv";
dotenv.config();
import express from "express";
import cors from "cors";
import bodyParser from "body-parser";
import http from "http";
import { Server } from "socket.io";
import cookieParser from "cookie-parser";
import connectDB from "./config/DBConnect.js";
import rootRoute from "./routes/root.route.js";

const PORT = process.env.PORT || 5000;

const app = express();
// const server = http.createServer(app);
// const io = new Server(server, {
//   cors: {
//     origin: "*",
//     methods: ["GET", "POST"],
//   },
// });

app.use(cors());
app.use(cookieParser());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use("/api/v1", rootRoute);

// io.on("connection", (socket) => {
//   console.log(`[INFO] New client connected with ID: ${socket.id}`);

//   // Set custom ID for the client
//   socket.on("setmyId", (data) => {
//     socket.customId = data.customId;
//     console.log(`[INFO] Client ${socket.id} set custom ID: ${data.customId}`);
//   });

//   // Admin-client setup
//   socket.on("setAdminClient", () => {
//     socket.isAdmin = true;
//     console.log(`[INFO] Admin client connected (ID: ${socket.id})`);
//   });

//   // Admin sends a message to a specific client
//   socket.on("sendMessageToClient", (data) => {
//     const recipient = Array.from(io.sockets.sockets.values()).find(
//       (s) => s.customId === data.clientId
//     );
//     if (recipient) {
//       recipient.emit("message", `Message from Admin: ${data.message}`);
//       console.log(
//         `[INFO] Message sent to Client ${data.clientId}: ${data.message}`
//       );
//     }
//   });

//   // Admin broadcasts a message to all clients
//   socket.on("broadcastMessage", (data) => {
//     io.emit("message", `Broadcast from Admin: ${data.message}`);
//     console.log(`[INFO] Broadcast message to all clients: ${data.message}`);
//   });

//   // Handle disconnection
//   socket.on("disconnect", () => {
//     console.log(`[INFO] Client ${socket.id} has disconnected`);
//   });
// });

// Start the server after successful database connection
connectDB()
  .then(() => {
    console.log("Connected to Database");
    app.listen(PORT, () => {
      console.log(
        `[INFO] WebSocket server running on port ${PORT}. Waiting for connections...`
      );
    });
  })
  .catch((err) => {
    console.log(err);
  });
