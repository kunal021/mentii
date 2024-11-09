import { Button } from "@/components/ui/button";
import { CheckCircle } from "lucide-react";
import { motion } from "framer-motion";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";

export function ProductInfo() {
  const features = [
    "Secure patient data management",
    "Real-time chat with patients",
    "Appointment scheduling and reminders",
    "Customizable medical records",
  ];

  return (
    <Card className="w-full max-w-2xl mx-auto">
      <CardHeader>
        <CardTitle>
          <motion.h2
            initial={{ opacity: 0, y: -10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3, delay: 0.1 }}
            className="text-3xl font-bold"
          >
            Welcome to MedChat Dashboard
          </motion.h2>
        </CardTitle>
      </CardHeader>
      <CardContent>
        <motion.p
          initial={{ opacity: 0, y: -10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3, delay: 0.2 }}
          className="text-lg mb-6 text-muted-foreground"
        >
          Discover the powerful features designed for healthcare professionals:
        </motion.p>
        <ul className="space-y-3 mb-6">
          {features.map((feature, index) => (
            <motion.li
              key={index}
              initial={{ opacity: 0, x: -10 }}
              animate={{ opacity: 1, x: 0 }}
              transition={{ duration: 0.3, delay: 0.3 + index * 0.1 }}
              className="flex items-center"
            >
              <CheckCircle className="mr-2 h-5 w-5 text-primary" />
              <span className="text-foreground">{feature}</span>
            </motion.li>
          ))}
        </ul>
        <motion.div
          initial={{ opacity: 0, y: 10 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3, delay: 0.7 }}
        >
          <Button
            variant="default"
            size="lg"
            className="w-full sm:w-auto transition duration-300 ease-in-out transform hover:-translate-y-1 hover:shadow-md"
          >
            Explore Dashboard
          </Button>
        </motion.div>
      </CardContent>
    </Card>
  );
}
