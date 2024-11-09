import dotenv from "dotenv";
dotenv.config();
import express from "express";
import cors from "cors";
import bodyParser from "body-parser";
import cookieParser from "cookie-parser";
import { Socket, Server } from "socket.io";
import { createServer } from "http";
import connectDB from "./config/DBConnect.js";
import rootRoute from "./routes/root.route.js";

const port = process.env.PORT || 5000;

const app = express();
const server = createServer(app);

app.use(cors());
app.use(cookieParser());

app.use(bodyParser.json());

app.use("/api/v1", rootRoute);

connectDB()
  .then(() => {
    console.log("Connected to Database");
    app.listen(port, () => {
      console.log(`Server running on port ${port}`);
    });
  })
  .catch((err) => {
    console.log(err);
  });
