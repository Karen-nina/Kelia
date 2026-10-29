import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";

const apiKey = process.env.REACT_APP_FIREBASE_API_KEY;
const appId = process.env.REACT_APP_FIREBASE_APP_ID;

const firebaseConfig = {
  apiKey: apiKey,
  authDomain: "kelia-f4919.firebaseapp.com",
  projectId: "kelia-f4919",
  storageBucket: "kelia-f4919.firebasestorage.app",
  messagingSenderId: "354767314013",
  appId: appId
};

const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);