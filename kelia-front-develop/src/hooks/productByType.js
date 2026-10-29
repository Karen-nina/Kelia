import axios from "axios";
import { useEffect, useState } from "react";

export const useGetProductsByType = (type) => {
  const [data, setData] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const baseUrl = process.env.REACT_APP_BACKEND_URL;

  const fetch = async () => {
    try {
      setLoading(true);

      const response = await axios.get(`${baseUrl}/productANMAT?type=${type}`);

      setData(response.data);
    } catch (err) {
      setError(err.message || "Unknown error");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetch();
  }, [type]);

  const agregar = (producto) => {
    //data.push(producto);
    //setData(data);
  };

  return { data, agregar, error, loading };
};
