import { Login } from "@/components/auth/Login";
import { Signup } from "@/components/auth/Signup";
import { ProductInfo } from "@/components/auth/ProductInfo";
import { motion } from "framer-motion";
import { useLocation } from "react-router-dom";

export default function Auth() {
  const location = useLocation();

  const isLogin = location.pathname === "/auth/login";
  const isSignup = location.pathname === "/auth/signup";
  return (
    <div className="flex min-h-screen bg-gray-50 overflow-hidden">
      {/* Left column - Auth form */}
      <motion.div
        initial={{ opacity: 0, x: -20 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 0.5 }}
        className="w-full md:w-1/2 p-8 flex items-center justify-center"
      >
        <div className="w-full max-w-md">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -20 }}
            transition={{ duration: 0.3 }}
          >
            {isLogin && <Login />}
            {isSignup && <Signup />}
          </motion.div>
        </div>
      </motion.div>

      {/* Right column - Product information */}
      <motion.div
        initial={{ opacity: 0, x: 20 }}
        animate={{ opacity: 1, x: 0 }}
        transition={{ duration: 0.5 }}
        className="hidden md:block w-1/2 bg-gradient-to-br from-purple-600 via-blue-500 to-indigo-700 p-12 text-white relative overflow-hidden"
      >
        <div className="absolute inset-0 bg-grid-white/[0.2] bg-[size:20px_20px]" />
        <div className="absolute inset-0 bg-gradient-to-t from-purple-600/50 to-transparent" />
        <div className="relative z-10">
          <ProductInfo />
        </div>
      </motion.div>
    </div>
  );
}
