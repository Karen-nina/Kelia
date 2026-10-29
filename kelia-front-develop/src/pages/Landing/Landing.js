import React, { useEffect } from 'react';
import './Landing.css';
import { Link } from 'react-router-dom';
import AOS from 'aos';
import 'aos/dist/aos.css';

const Landing = () => {
  useEffect(() => {
    AOS.init({ duration: 1000, once: true });
  }, []);

  return (
    <>

      {/* Welcome Section */}
      <section className="hero-section text-white d-flex align-items-center" style={{
  background: "linear-gradient(to right, rgba(0, 0, 0, 0.55), rgba(0, 0, 0, 0.1)), url('/img/inicio.jpg')",
  backgroundSize: "cover",
  backgroundPosition: "center",
  minHeight: "80vh"
}}>
        <div className="container">
          <div className="row">
            <div className="col-md-7">
              <h1 className="display-4 fw-bold">Sin gluten, sin miedo, <br />sin excusas</h1>
              <p className="lead">Descubrí todo lo que la comunidad celíaca necesita en un solo lugar.</p>
              <Link to="/login" className="btn btn-cta mt-3">
                Comenzar ahora <i className="bi bi-arrow-right"></i>
              </Link>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="container text-center" data-aos="fade-up">
        <h2 className="mb-4">Explorá nuestras funciones</h2>
        <div className="row g-4">
          <div className="col-md-6" data-aos="fade-up">
            <div className="feature-card h-100">
              <img src="/img/foro.jpg" alt="Foro Comunitario" className="feature-img" />
              <h4>Foro comunitario</h4>
              <p>Publicá dudas y compartí consejos con otros celíacos en nuestra comunidad.</p>
            </div>
          </div>
          <div className="col-md-6" data-aos="fade-up">
            <div className="feature-card h-100">
              <img src="/img/restaurantes.jpg" alt="Mapa de Restaurantes" className="feature-img" />
              <h4>Mapa de establecimientos</h4>
              <p>Localizá y calificá establecimientos libres de gluten cerca tuyo.</p>
            </div>
          </div>
          <div className="col-md-6" data-aos="fade-up">
            <div className="feature-card h-100">
              <img src="/img/recetas.jpg" alt="Recetas sin Gluten" className="feature-img" />
              <h4>Recetas sin gluten</h4>
              <p>Descubrí y valorá recetas elaboradas para celíacos.</p>
            </div>
          </div>
          <div className="col-md-6" data-aos="fade-up">
            <div className="feature-card h-100">
              <img src="/img/productos.jpg" alt="Productos Certificados" className="feature-img" />
              <h4>Productos certificados</h4>
              <p>Accedé al catálogo oficial de productos aprobados por ANMAT.</p>
            </div>
          </div>
        </div>
      </section>

      {/* Beneficios y Contacto */}
      <section className="container my-5" data-aos="fade-up">
        <div className="row align-items-center">
          {/* Beneficios */}
          <div className="col-md-6 mb-4 mb-md-0" data-aos="fade-up">
            <h3 className="mb-4">Beneficios</h3>
            <ul className="list-unstyled">
              <li className="d-flex align-items-start mb-4" data-aos="fade-up" data-aos-delay="100">
                <img src="/img/icono-1.png" alt="Comer fuera sin ansiedad" width="40" className="me-3" />
                <div>
                  <h5 className="mb-1">Menos ansiedad al momento de comer fuera de casa</h5>
                  <p className="mb-0">Encontrá lugares certificados o recomendados por otros celíacos, sin miedo a la contaminación cruzada.</p>
                </div>
              </li>
              <li className="d-flex align-items-start mb-4" data-aos="fade-up" data-aos-delay="200">
                <img src="/img/icono-2.png" alt="Ahorro de tiempo" width="40" className="me-3" />
                <div>
                  <h5 className="mb-1">Ahorrá tiempo y estrés</h5>
                  <p className="mb-0">Evitá buscar en múltiples fuentes: todo lo que necesitás para vivir sin gluten está en una sola app.</p>
                </div>
              </li>
              <li className="d-flex align-items-start mb-4" data-aos="fade-up" data-aos-delay="300">
                <img src="/img/icono-3.png" alt="Inspiración culinaria" width="40" className="me-3" />
                <div>
                  <h5 className="mb-1">Inspirate sin poner en riesgo tu salud</h5>
                  <p className="mb-0">Recibí recetas seguras, evaluadas por la comunidad, y descubrí qué cocinar sin tener que improvisar.</p>
                </div>
              </li>
              <li className="d-flex align-items-start" data-aos="fade-up" data-aos-delay="400">
                <img src="/img/icono-4.png" alt="Comunidad celíaca" width="40" className="me-3" />
                <div>
                  <h5 className="mb-1">Sentite parte de una comunidad que entiende tus desafíos</h5>
                  <p className="mb-0">Compartí experiencias, hacé preguntas, ayudá a otros y descubrí que no estás solo/a.</p>
                </div>
              </li>
            </ul>
          </div>
          {/* Contacto */}
          <div className="col-md-6" data-aos="fade-up">
            <div className="p-4 rounded" style={{ backgroundColor: "#ccd5ae" }}>
              <h4 className="mb-4">Contactanos</h4>
              <form>
                <div className="mb-3" data-aos="fade-up" data-aos-delay="100">
                  <label htmlFor="name" className="form-label">Nombre</label>
                  <input type="text" className="form-control" id="name" placeholder="Tu nombre" />
                </div>
                <div className="mb-3" data-aos="fade-up" data-aos-delay="200">
                  <label htmlFor="email" className="form-label">Email</label>
                  <input type="email" className="form-control" id="email" placeholder="nombre@email.com" />
                </div>
                <div className="mb-3" data-aos="fade-up" data-aos-delay="300">
                  <label htmlFor="message" className="form-label">Mensaje</label>
                  <textarea className="form-control" id="message" rows="4" placeholder="Escribí tu mensaje..."></textarea>
                </div>
                <div className="d-flex justify-content-end" data-aos="fade-up" data-aos-delay="400">
                  <button type="submit" className="btn btn-send px-4">Enviar mensaje</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </section>

      {/* Call to Action Section */}
      <section className="cta-section text-center text-black py-5">
        <div className="container">
          <h2 className="mb-4">¿Listo para unirte a nuestra comunidad?</h2>
          <p className="lead mb-4">Descubrí un mundo sin gluten lleno de posibilidades y apoyo.</p>
          <Link to="/login" className="btn btn-cta mt-3">
            Comenzar ahora <i className="bi bi-arrow-right"></i>
          </Link>
        </div>
      </section>
    </>
  );
};

export default Landing;