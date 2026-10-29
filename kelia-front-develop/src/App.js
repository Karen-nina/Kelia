import React from "react";
import "./App.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar/Navbar";
import Footer from "./components/Footer/Footer";
import ProtectedLayout from "./components/ProtectedLayout/ProtectedLayout";

import Landing from "./pages/Landing/Landing";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import Inicio from "./pages/Inicio/Inicio";
import Recetas from "./pages/Recetas/Recetas";
import NuevaReceta from "./pages/Recetas/NuevaReceta";
import DetalleReceta from "./pages/DetalleReceta/DetalleReceta";
import Establecimientos from "./pages/Establecimientos/Establecimientos";
import Productos from "./pages/Productos/Productos";
import FAQs from "./pages/FAQs/FAQs";

const App = () => {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route element={<ProtectedLayout />}>
          <Route path="/inicio" element={<Inicio />} />
          <Route path="/recetas" element={<Recetas />} />
          <Route path="/recetas/nuevareceta" element={<NuevaReceta />} />
          <Route path="/receta/:id" element={<DetalleReceta />} />
          <Route path="/establecimientos" element={<Establecimientos />} />
          <Route path="/establecimiento/:id" element={<Establecimientos />} />
          <Route path="/productos" element={<Productos />} />
          <Route path="/faq" element={<FAQs />} />
        </Route>
      </Routes>
      <Footer />
    </>
  );
};

export default App;