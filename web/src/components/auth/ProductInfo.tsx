import { Button } from "@/components/ui/button";
import { CheckCircle } from "lucide-react";
import { motion } from "framer-motion";

export function ProductInfo() {
  const features = [
    "Seamless integration with your workflow",
    "Advanced analytics and reporting",
    "24/7 customer support",
    "Customizable to fit your needs",
  ];

  return (
    <div className="h-full flex flex-col justify-center">
      <motion.h2
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5, delay: 0.2 }}
        className="text-4xl font-bold mb-6"
      >
        Welcome to Our Product
      </motion.h2>
      <motion.p
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5, delay: 0.4 }}
        className="text-xl mb-8"
      >
        Discover the amazing features that await you:
      </motion.p>
      <ul className="space-y-4 mb-8">
        {features.map((feature, index) => (
          <motion.li
            key={index}
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.5, delay: 0.6 + index * 0.1 }}
            className="flex items-center"
          >
            <CheckCircle className="mr-2 h-6 w-6 text-green-400" />
            <span>{feature}</span>
          </motion.li>
        ))}
      </ul>
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5, delay: 1 }}
      >
        <Button
          variant="secondary"
          size="lg"
          className="bg-white text-indigo-600 hover:bg-indigo-100 transition duration-300 ease-in-out transform hover:-translate-y-1 hover:shadow-lg"
        >
          Learn More
        </Button>
      </motion.div>
    </div>
  );
}
