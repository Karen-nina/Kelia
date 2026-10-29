jest.mock("firebase/app", () => ({
  initializeApp: jest.fn(),
}));

jest.mock("firebase/auth", () => ({
  getAuth: jest.fn(),
}));

describe("Firebase Initialization", () => {
  beforeEach(() => {
    jest.clearAllMocks();

    process.env.REACT_APP_FIREBASE_API_KEY = "fake-api-key";
    process.env.REACT_APP_FIREBASE_APP_ID = "fake-app-id";
  });

  test("InitializesFirebaseWithCorrectConfig", () => {
    const { initializeApp } = require("firebase/app");

    require("./firebase");

    expect(initializeApp).toHaveBeenCalledWith({
      apiKey: "fake-api-key",
      authDomain: "kelia-f4919.firebaseapp.com",
      projectId: "kelia-f4919",
      storageBucket: "kelia-f4919.firebasestorage.app",
      messagingSenderId: "354767314013",
      appId: "fake-app-id",
    });
  });
});