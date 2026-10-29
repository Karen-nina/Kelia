import React from "react";
import { Accordion } from "react-bootstrap";
import "./FAQs.css";

const faqItems = [
  {
    question: "¿Qué es la celiaquía?",
    answer: "La enfermedad celíaca es una enfermedad crónica, que se desencadena por la ingestión de proteínas presentes en trigo, avena, cebada y centeno, habitualmente llamadas gluten, afectando el intestino delgado de personas que están genéticamente predispuestas. \n\n La enfermedad puede aparecer en cualquier momento de la vida, desde que se incorpora el gluten a la alimentación, es más frecuente en mujeres que en hombres. \n\n Cuando una persona celíaca consume gluten, se produce un daño principalmente en la mucosa del intestino disminuyendo la capacidad de absorber nutrientes.",
  },
  {
    question: "¿Cómo sé si un producto es apto para celíacos?",
    answer: "Un producto es apto para celíacos si en el envase tiene el logo “Sin TACC”, si está inscripto en el listado oficial de alimentos libres de gluten del organismo de salud correspondiente (como ANMAT en Argentina) y si no contiene ingredientes derivados de trigo, avena, cebada o centeno ni ha estado en contacto con ellos. Siempre es importante leer la etiqueta completa y, ante cualquier duda, evitar su consumo",
  },
  {
    question: "¿Qué cuidados debo tener si cocino para alguien celíaco?",
    answer: "Al cocinar para una persona celíaca, es fundamental lavarse bien las manos antes de comenzar, utilizar utensilios y superficies limpias que no hayan estado en contacto con gluten, y cocinar los alimentos sin gluten por separado o en primer lugar. No se debe usar aceite o agua que haya sido utilizado para cocinar alimentos con gluten, y los productos deben almacenarse de forma separada. Además, es imprescindible verificar que todos los ingredientes sean aptos para celíacos.",
  },
  {
    question: "¿Cuáles son sus síntomas?",
    answer: "Los síntomas de la celiaquía varían entre personas, pero suelen incluir dolor abdominal, hinchazón, gases, diarrea crónica o constipación, fatiga, pérdida de peso, anemia, problemas en la piel como dermatitis herpetiforme y cambios en el estado de ánimo como irritabilidad o ansiedad. En los niños, puede haber retraso en el crecimiento o baja talla. En algunos casos, la celiaquía puede no presentar síntomas visibles, aunque igual haya daño en el intestino.",
  },
  {
    question: "¿Se cura la celiaquía?",
    answer: "La celiaquía no tiene cura, pero se controla completamente llevando una dieta libre de gluten durante toda la vida. Al evitar el gluten de forma estricta, el intestino se regenera y los síntomas desaparecen.",
  },
  {
    question: "¿Qué sucede si una persona celíaca consume gluten?",
    answer: "Incluso pequeñas cantidades de gluten pueden causar daños en el intestino delgado de una persona celíaca, además de síntomas gastrointestinales y malestar general.",
  },
  {
    question: "¿Los medicamentos pueden tener gluten?",
    answer: "Sí, algunos medicamentos pueden contener gluten como excipiente. Siempre es importante consultar con un médico o farmacéutico antes de tomar un medicamento.",
  },
  {
    question: "¿Una persona celíaca puede comer en restaurantes?",
    answer: "Sí, pero debe asegurarse de que el restaurante ofrezca opciones seguras sin gluten y que el personal entienda cómo evitar la contaminación cruzada.",
  },
  {
    question: "¿Es lo mismo ser celíaco que tener sensibilidad al gluten?",
    answer: "No. La celiaquía es una enfermedad autoinmune, mientras que la sensibilidad al gluten no celíaca no produce daño intestinal, aunque puede generar síntomas similares.",
  },
  {
    question: "¿Los productos sin gluten son más saludables?",
    answer: "No necesariamente. Algunos productos sin gluten pueden ser altos en grasas, azúcares o aditivos. Lo ideal es mantener una alimentación equilibrada, más allá de evitar el gluten.",
  },
  {
    question: "¿Se puede tener celiaquía sin síntomas?",
    answer: "Sí, muchas personas tienen celiaquía asintomática, es decir, no presentan síntomas visibles pero igual sufren daños intestinales al consumir gluten.",
  },
  {
    question: "¿La celiaquía es hereditaria?",
    answer: "Sí, tiene un componente genético. Si un familiar directo tiene celiaquía, es recomendable realizar estudios para detectar la enfermedad, aunque no haya síntomas.",
  },
  {
    question: "¿Cómo se diagnostica la celiaquía?",
    answer: "El diagnóstico se realiza mediante análisis de sangre específicos y suele confirmarse con una biopsia intestinal. Es importante no suspender el consumo de gluten antes de los estudios.",
  }
];

const FAQs = () => {

  return (
    <div className="container text-center my-5">
      <h2 className="mb-4 fw-bold">Preguntas frecuentes</h2>
      <Accordion className="mx-auto" style={{ maxWidth: "700px" }} defaultActiveKey="0">
        {faqItems.map((item, idx) => (
          <Accordion.Item eventKey={idx.toString()} key={idx}>
            <Accordion.Header className="accordion-header">{item.question}</Accordion.Header>
            <Accordion.Body className="answer-text" style={{ whiteSpace: 'pre-line' }}>
              {item.answer}
            </Accordion.Body>
          </Accordion.Item>
  ))}
      </Accordion>
    </div>
  );
};

export default FAQs;